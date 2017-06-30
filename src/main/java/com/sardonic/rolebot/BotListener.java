package com.sardonic.rolebot;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Micky Lindsay  on 6/29/2017.
 */
public class BotListener extends ListenerAdapter {

    private Guild server;
    private GuildController controller;
    private List<Long> roleIds;
    private List<Long> channelsIds;
    private Map<Long, Role> roles;
    private Map<Long, TextChannel> channels;

    public BotListener(JDA jda, String path) throws BotException {

        if(jda.getGuilds().size() == 1) {
            this.server = jda.getGuilds().get(0);
        }else{
            throw new BotException("Bot is active in more than one server.");
        }
        this.controller = new GuildController(server);
        this.roleIds = new ArrayList<Long>();
        this.channelsIds = new ArrayList<Long>();
        this.roles = new HashMap<Long, Role>();
        this.channels = new HashMap<Long, TextChannel>();

        File f = new File(path);
        try {
            Scanner scan = new Scanner(f);
            while(scan.hasNextLine()){
                roleIds.add(scan.nextLong());
                channelsIds.add(scan.nextLong());
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

        MessageBuilder output = new MessageBuilder();
        if(message.getContent().startsWith("!channels")){
            for (long id : channelsIds) {
                TextChannel chan = getChannel(id);
                output.append(chan);
            }
        }else if(message.getContent().startsWith("!inchannel")){
            if(message.getMentionedChannels().size() < 1){
                output.append("Please mention a channel.");
            }else{
                long mentionedId = message.getMentionedChannels().get(0).getIdLong();
                Role role = getRole(roleIds.get(channelsIds.indexOf(mentionedId)));
                List<Member> members = server.getMembersWithRoles(role);
                for (Member member : members){
                    output.append(member.getEffectiveName());
                }
            }
        }else if(message.getContent().startsWith("!gib")){
            if(message.getMentionedChannels().size() < 1){
                output.append("Please mention a channel.");
            }else{
                Channel mentionedChannel = message.getMentionedChannels().get(0);
                Role role = getAttachedRole(mentionedChannel);
                if (!message.getMember().getRoles().contains(role)){
                    controller.addRolesToMember(message.getMember(), role).queue();
                    output.append("Done.");
                }else{
                    output.append("You already have that role.");
                }
            }
        }else if(message.getContent().startsWith("!take")){
            if(message.getMentionedChannels().size() < 1){
                output.append("Please mention a channel.");
            }else{
                Channel mentionedChannel = message.getMentionedChannels().get(0);
                Role role = getAttachedRole(mentionedChannel);
                List<Role> hasRoles = message.getMember().getRoles();
                if (hasRoles.contains(role)){
                    controller.removeRolesFromMember(message.getMember(), role).queue();
                    output.append("Done.");
                }else{
                    output.append("You don't have that role.");
                }
            }
        }
        if(!output.isEmpty()){
            event.getChannel().sendMessage(output.build()).queue();
        }
    }

    private Role getRole(long id){
        if(roles.containsKey(id)){
            return roles.get(id);
        }
        Role role = server.getRoleById(id);
        if(role == null){
            return null;
        }
        roles.put(id, role);
        return role;
    }

    private TextChannel getChannel(long id){
        if(channels.containsKey(id)){
            return channels.get(id);
        }
        TextChannel channel = server.getTextChannelById(id);
        if(channel == null){
            return null;
        }
        channels.put(id, channel);
        return channel;
    }

    private Role getAttachedRole(Channel channel){
        int index = channelsIds.indexOf(channel.getIdLong());
        return index < 0 ? null : getRole(roleIds.get(index));
    }

    private TextChannel getAttachedChannel(Role role){
        int index = roleIds.indexOf(role.getIdLong());
        return index < 0 ? null : getChannel(channelsIds.get(index));
    }

}