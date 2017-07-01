package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.entities.Message;

/**
 * Abstract implementation of Command, to be extended for majority of Command creation
 * Created by Micky Lindsay on 6/30/2017.
 */
public abstract class AbstractCommand implements Command {

    private final String name;

    protected AbstractCommand(String name){
        this.name = name;
    }

    public abstract Message fire(Message command);

    public abstract String getDescription();

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "**" + name + "**: " + getDescription();
    }
}
