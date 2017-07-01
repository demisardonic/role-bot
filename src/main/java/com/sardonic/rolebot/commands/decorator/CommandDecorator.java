package com.sardonic.rolebot.commands.decorator;

import com.sardonic.rolebot.commands.AbstractCommand;
import com.sardonic.rolebot.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * Base implementation for decorating a commands fire method with external actions.
 * Subclasses should call super.fire() to ensure the innerCommand is fired.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class CommandDecorator extends AbstractCommand{

    private final Command innerCommand;

    CommandDecorator(Command innerCommand) {
        super(innerCommand.getName());
        this.innerCommand = innerCommand;
    }

    public Message fire(Message command){
        return innerCommand.fire(command);
    }
}
