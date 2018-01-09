package com.sardonic.rolebot;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Listener which handles but functionality like receiving messages within a server.
 * Created by Micky Lindsay  on 6/29/2017.
 */
class BotListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        if (!message.getContent().startsWith(RoleBot.getInstance().getTrigger())) {
            return;
        }
        Commands.getInstance().triggerCommand(event.getChannel(), author, message);
    }
}