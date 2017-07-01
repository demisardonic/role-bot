package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 6/30/2017.
 * An action fired once the triggerCommand name is executed. This action can optionally generate a message, which will be displayed in the same channel where the command was executed.
 */
public interface Command {

    /**
     * Method called once the triggerCommand name is executed within a channel accessible to the bot.
     *
     * @param command {@link Message} used to execute the command
     * @return A message to be output within the channel the command fired from
     */
    Message fire(Message command);

    /**
     * Returns the name which is used to fire off the command.
     *
     * @return name used to fire the command.
     */
    String getName();
}
