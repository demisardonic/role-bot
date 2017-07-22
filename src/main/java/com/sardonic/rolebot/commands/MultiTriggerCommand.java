package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.entities.Message;

import java.util.Arrays;

/**
 * Created by Micky Lindsay on 7/21/2017.
 */
public abstract class MultiTriggerCommand extends AbstractCommand implements MultiTrigger {

    String[] triggers;

    protected MultiTriggerCommand(String... names) {
        super(null);
        triggers = names;
    }

    public abstract Message fire(Message command);

    public abstract String getDescription();

    @Override
    public String[] getTriggers() {
        return triggers;
    }
}
