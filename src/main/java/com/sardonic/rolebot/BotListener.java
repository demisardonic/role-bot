package com.sardonic.rolebot;

import com.sardonic.rolebot.commands.Commands;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Core bot class which handles all events, and reading and writing the Role-Channel file.
 * Created by Micky Lindsay  on 6/29/2017.
 */
class BotListener extends ListenerAdapter {


    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        if (!message.getContent().startsWith("!")) {
            return;
        }
        Commands.getInstance().trigger(event.getChannel(), message);
/*
       if (content.startsWith("!inchannel")) {
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
                    if (!channelList.contains(channel.getIdLong())) {
                        Role role = controller.createRole().setName(channel.getName()).setMentionable(true).complete();

                        channel.getPermissionOverride(server.getPublicRole()).getManager().deny(Permission.MESSAGE_READ).queue();
                        channel.createPermissionOverride(role).setAllow(Permission.MESSAGE_READ).queue();

                        roleList.add(new RoleIdentity(channel.getName(), role.getIdLong()));
                        channelList.add(new ChannelIdentity(channel.getName(), channel.getIdLong()));
                        updateFile();
                        output.append("Channel is now being handled.");
                    }
                }
            } else if (content.startsWith("!create")) {
                String[] split = content.split(" ", 2);
                if (split.length < 2) {
                    output.append("Please include a channel name.");
                } else {
                    String name = split[1].replaceAll(" ", "-");
                    if (channelList.contains(name)) {
                        output.append("Channel already exists.");
                    } else {
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
                }
            } else if (content.startsWith("!delete")) {
                if (message.getMentionedChannels().size() < 1) {
                    output.append("Please mention a channel.");
                } else {
                    Channel channel = message.getMentionedChannels().get(0);
                    int index = channelList.indexOf(channel.getIdLong());
                    if (index < 0) {
                        output.append("Channel is not currently being handled.");
                    } else {
                        Role role = getAttachedRole(channel);
                        if (role == null) {
                            output.append("Channel has no attached Role.");
                        } else {
                            role.delete().queue();
                            roleList.remove(index);
                            channelList.remove(index);
                            updateFile();
                            output.append("Role deleted. Channel can be recovered with !handle <channel name>");
                        }
                    }
                }
            }
        }
        if (!output.isEmpty()) {
            event.getChannel().sendMessage(output.build()).queue();
        }
        */
    }
}