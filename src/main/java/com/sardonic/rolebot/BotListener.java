package com.sardonic.rolebot;

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
       if (message.getMember().getPermissions().contains(Permission.MANAGE_CHANNEL)) {
           if (content.startsWith("!delete")) {
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