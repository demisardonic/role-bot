package com.sardonic.rolebot.commands.decorator;

import com.sardonic.rolebot.commands.AbstractCommand;
import com.sardonic.rolebot.commands.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public class ModifyChannelCommandDecorator extends CommandDecorator {

    protected ModifyChannelCommandDecorator(Command innerCommand) {
        super(innerCommand);
    }

    @Override
    public Message fire(Message command) {
        if (command.getMember().hasPermission(Permission.MANAGE_CHANNEL)){
            return fire(command);
        }
        return null;
    }
}
