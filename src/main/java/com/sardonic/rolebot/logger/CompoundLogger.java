package com.sardonic.rolebot.logger;

import net.dv8tion.jda.core.entities.Message;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Contains a collection of loggers which are all executed on incoming and outgoing message.
 * Created by Micky Lindsay on 7/21/2017.
 */
public final class CompoundLogger implements Logger {
    Collection<Logger> loggers;

    /**
     * Creates an empty logger collection.
     * <p>Add loggers via  {@link #addLogger(Logger)}
     * </p>
     */
    public CompoundLogger() {
        this(Collections.emptyList());
    }
    /**
     * Creates an logger collection containing all given loggers
     */
    public CompoundLogger(Logger... loggers) {
        this(Arrays.asList(loggers));
    }
    /**
     * Creates an logger collection containing the given logger collection
     */
    public CompoundLogger(Collection<Logger> loggers) {
        this.loggers = loggers;
    }

    public boolean addLogger(Logger l) {
        if (l != null)
            return loggers.add(l);
        return false;
    }

    public boolean removeLogger(Logger l) {
        if (l != null)
            return loggers.remove(l);
        return false;
    }

    @Override
    public void logIncomingMessage(Message message) {
        for (Logger l : loggers) {
            l.logIncomingMessage(message);
        }
    }

    @Override
    public void logOutgoingMessage(Message message) {
        for (Logger l : loggers) {
            l.logOutgoingMessage(message);
        }
    }
}
