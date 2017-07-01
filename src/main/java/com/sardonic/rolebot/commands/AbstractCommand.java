package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public abstract class AbstractCommand implements Command {

    private String name;

    protected AbstractCommand(String name){
        this.name = name;
    }

    public abstract Message fire(Message command);

    @Override
    public String getName() {
        return name;
    }
}
