package com.sardonic.rolebot.commands.decorator;

import com.sardonic.rolebot.commands.AbstractCommand;
import com.sardonic.rolebot.commands.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public abstract class CommandDecorator extends AbstractCommand{

    Command innerCommand;

    protected CommandDecorator(Command innerCommand) {
        super(innerCommand.getName());
        this.innerCommand = innerCommand;
    }

    public Message fire(Message command){
        return innerCommand.fire(command);
    };
}
