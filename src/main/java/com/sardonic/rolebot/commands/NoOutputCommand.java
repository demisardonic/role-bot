package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.entities.Message;

/**
 * Wrapper for commands which do not generate an output message.
 * Created by Micky Lindsay on 6/30/2017.
 */
public abstract class NoOutputCommand extends AbstractCommand{

    public NoOutputCommand(String name) {
        super(name);
    }

    public abstract void outputlessFire(Message command);

    @Override
    final public Message fire(Message command) {
        outputlessFire(command);
        return null;
    }
}
