package com.sardonic.rolebot.logger;

import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 7/21/2017.
 * Interface which is used by the Commands singleton for a custom logging handler.
 * <p>logIncomingMessage is passed an incoming message that the bot should handle</p>
 * <p>logOutgoingMessage is passed the message that the bot responds with</p>
 */
public interface Logger {
    /**
     * Handles logging the incoming message before a Command is fired.
     *
     * @param message The message from a user for which the bot fires a command.
     */
    void logIncomingMessage(Message message);

    /**
     * Handles logging the outgoing message after a Command is fired.
     *
     * @param message The message from the bot which had a Command fired.
     */
    void logOutgoingMessage(Message message);
}
