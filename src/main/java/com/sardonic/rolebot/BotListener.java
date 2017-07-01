package com.sardonic.rolebot;

import com.sardonic.rolebot.exceptions.BotException;
import com.sardonic.rolebot.identity.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Core bot class which handles all events, and reading and writing the Role-Channel file.
 * Created by Micky Lindsay  on 6/29/2017.
 */
class BotListener extends ListenerAdapter {

    private String path;
    private Guild server;
    private GuildController controller;
    private RoleList roleList;
    private ChannelList channelList;
    private Map<Long, Role> roles;
    private Map<Long, TextChannel> channels;

    BotListener(JDA jda, String path) throws BotException {
        this.path = path;

        if (jda.getGuilds().size() == 1) {
            this.server = jda.getGuilds().get(0);
        } else {
            throw new BotException("Bot is active in more than one server.");
        }
        this.controller = new GuildController(server);
        this.roleList = new RoleList();
        this.channelList = new ChannelList();
        this.roles = new HashMap<>();
        this.channels = new HashMap<>();

        File f = new File(path);
        try {
            Scanner scan = new Scanner(f);
            while (scan.hasNext()) {
                RoleIdentity role = new RoleIdentity(scan.next(), scan.nextLong());
                roleList.add(role);
                ChannelIdentity channel = new ChannelIdentity(scan.next(), scan.nextLong());
                channelList.add(channel);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw new BotException(e);
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        if (!message.getContent().startsWith("!")) {
            return;
        }
        String content = message.getContent();

        MessageBuilder output = new MessageBuilder();
        if (content.startsWith("!channels")) {
            for (ChannelIdentity chanIden : channelList) {
                TextChannel chan = getChannel(chanIden);
                if (chan != null) {
                    output.append(chan);
                    output.append("\n");
                } else {
                    String s = chanIden.getId() + " : is not valid channel.";
                    output.append(s);
                }
            }
        } else if (content.startsWith("!inchannel")) {
            if (message.getMentionedChannels().size() < 1) {
                output.append("Please mention a channel.");
            } else {
                long mentionedId = message.getMentionedChannels().get(0).getIdLong();
                Role role = getRole(roleList.get(channelList.indexOf(mentionedId)));
                List<Member> members = server.getMembersWithRoles(role);
                for (Member member : members) {
                    output.append(member.getEffectiveName());
                }
            }
        } else if (content.startsWith("!gib")) {
            Role gibbedRole = null;
            if (message.getMentionedChannels().size() < 1) {
                RoleIdentity identity = roleList.find(content.split(" ")[1].trim());
                if (identity != null) {
                    gibbedRole = getRole(identity);
                }
            } else {
                Channel mentionedChannel = message.getMentionedChannels().get(0);
                gibbedRole = getAttachedRole(mentionedChannel);
            }

            if (gibbedRole != null) {
                if (!message.getMember().getRoles().contains(gibbedRole)) {
                    controller.addRolesToMember(message.getMember(), gibbedRole).queue();
                    output.append("Done.");
                } else {
                    output.append("You already have that role.");
                }
            }

        } else if (content.startsWith("!take")) {
            if (message.getMentionedChannels().size() < 1) {
                output.append("Please mention a channel.");
            } else {
                Channel mentionedChannel = message.getMentionedChannels().get(0);
                Role role = getAttachedRole(mentionedChannel);
                if (role != null) {
                    if (message.getMember().getRoles().contains(role)) {
                        controller.removeRolesFromMember(message.getMember(), role).queue();
                        output.append("Done.");
                    } else {
                        output.append("You don't have that role.");
                    }
                } else {
                    output.append("Channel is not currently being handled.");
                }
            }
        } else if (message.getMember().getPermissions().contains(Permission.MANAGE_CHANNEL)) {
            if (content.startsWith("!handle")) {
                if (message.getMentionedChannels().size() < 1) {
                    output.append("Please mention a channel.");
                } else {
                    Channel channel = message.getMentionedChannels().get(0);
                    Role role;
                    if (!channelList.contains(channel.getIdLong())) {
                        //role = controller.createRole().setName(channel.getName()).complete();

                    }
                }
            } else if (content.startsWith("!create")) {
                String[] split = content.split(" ", 2);
                if (split.length < 2) {
                    output.append("Please include a channel name.");
                } else {
                    String name = split[1].replaceAll(" ", "-");
                    try {
                        Role role = controller.createRole().setName(name).setMentionable(true).complete();
                        ChannelAction action = controller.createTextChannel(name);
                        action.addPermissionOverride(server.getPublicRole(), 0, Permission.MESSAGE_READ.getRawValue());
                        action.addPermissionOverride(role, Permission.MESSAGE_READ.getRawValue(), 0);
                        Channel channel = action.complete();

                        roleList.add(new RoleIdentity(name, role.getIdLong()));
                        channelList.add(new ChannelIdentity(name, channel.getIdLong()));
                        updateFile();
                    } catch (PermissionException e) {
                        output.append("I do not have permission to create channels in this server.");
                    } catch (Exception e) {
                        output.append("Failed to create channel");
                    }
                }
            } else if(content.startsWith("!delete")){
                if (message.getMentionedChannels().size() < 1) {
                    output.append("Please mention a channel.");
                }else{
                    Channel channel = message.getMentionedChannels().get(0);
                    int index = channelList.indexOf(channel.getIdLong());
                    if(index < 0){
                        output.append("Channel is not currently being handled.");
                    }else{
                        Role role = getAttachedRole(channel);
                        if(role == null){
                            output.append("Channel has no attached Role.");
                        }else{
                            try {
                                role.delete().queue();
                                roleList.remove(index);
                                channelList.remove(index);
                                updateFile();
                            } catch (BotException e) {
                                e.printStackTrace();
                            }
                            output.append("Role deleted. Channel can be recovered with !handle <channel name>");
                        }
                    }
                }
            }
        }
        if (!output.isEmpty()) {
            event.getChannel().sendMessage(output.build()).queue();
        }
    }

    private Role getRole(RoleIdentity identity) {
        long id = identity.getId();
        if (roles.containsKey(id)) {
            return roles.get(id);
        }
        Role role = server.getRoleById(id);
        if (role == null) {
            return null;
        }
        roles.put(id, role);
        return role;
    }

    private TextChannel getChannel(ChannelIdentity identity) {
        long id = identity.getId();
        if (channels.containsKey(id)) {
            return channels.get(id);
        }
        TextChannel channel = server.getTextChannelById(id);
        if (channel == null) {
            return null;
        }
        channels.put(id, channel);
        return channel;
    }

    private Role getAttachedRole(Channel channel) {
        int index = channelList.indexOf(channel.getIdLong());
        return index < 0 ? null : getRole(roleList.get(index));
    }

    private TextChannel getAttachedChannel(Role role) {
        int index = roleList.indexOf(role.getIdLong());
        return index < 0 ? null : getChannel(channelList.get(index));
    }

    private void updateFile() throws BotException {
        File f = new File(path);
        FileWriter fw;
        try {
            fw = new FileWriter(f);
            for (int i = 0; i < roleList.size(); i++) {
                fw.write(roleList.get(i) + " " + channelList.get(i) + " ");
            }
            fw.close();
        } catch (IOException e) {
            throw new BotException(e);
        }
    }
}