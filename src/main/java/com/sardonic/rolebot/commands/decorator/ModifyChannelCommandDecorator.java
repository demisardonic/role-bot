package com.sardonic.rolebot.commands.decorator;

import com.sardonic.rolebot.commands.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

/**
 * Decorates the given command with the requirement: member executing command requires the manage channel permission.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class ModifyChannelCommandDecorator extends CommandDecorator {

    public ModifyChannelCommandDecorator(Command innerCommand) {
        super(innerCommand);
    }

    @Override
    public Message fire(Message command) {
        if (command.getMember().hasPermission(Permission.MANAGE_CHANNEL)){
            return super.fire(command);
        }
        return null;
    }
}
