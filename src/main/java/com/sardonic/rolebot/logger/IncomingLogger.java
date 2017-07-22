package com.sardonic.rolebot.logger;

import net.dv8tion.jda.core.entities.Message;

/**
 * Abstract implementation of logging interface, used when only wanting incoming message functionality.
 * Created by Micky Lindsay on 7/21/2017.
 */
public abstract class IncomingLogger implements Logger {

    public abstract void logIncomingMessage(Message message);

    @Override
    public void logOutgoingMessage(Message message) {
        //Does nothing
    }
}
