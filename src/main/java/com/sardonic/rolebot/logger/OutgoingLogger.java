package com.sardonic.rolebot.logger;

import net.dv8tion.jda.core.entities.Message;

/**
 * Abstract implementation of logging interface, used when only wanting outgoing message functionality.
 * Created by Micky Lindsay on 7/21/2017.
 */
public abstract class OutgoingLogger implements Logger{
    @Override
    public void logIncomingMessage(Message message) {
        //Does nothing
    }

    public abstract void logOutgoingMessage(Message message);
}
