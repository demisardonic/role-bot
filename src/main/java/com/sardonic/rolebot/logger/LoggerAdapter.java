package com.sardonic.rolebot.logger;


import net.dv8tion.jda.core.entities.Message;

/**
 * Adapter instance of logging interface.
 * Created by Micky Lindsay on 7/21/2017.
 */
public class LoggerAdapter implements Logger {
    @Override
    public void logIncomingMessage(Message message) {
        //Does nothing
    }

    @Override
    public void logOutgoingMessage(Message message) {
        //Does nothing
    }
}
