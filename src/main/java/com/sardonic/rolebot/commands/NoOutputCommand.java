package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public abstract class NoOutputCommand extends AbstractCommand{

    public NoOutputCommand(String name) {
        super(name);
    }

    public abstract void fire();

    @Override
    public Message fire(Message command) {
        fire();
        return null;
    }
}
