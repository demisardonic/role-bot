package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.Commands;
import com.sardonic.rolebot.RoleBot;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.util.Collection;

/**
 * Created by Micky Lindsay on 7/1/2017.
 */
public class HelpCommand extends AbstractCommand{
    public HelpCommand() {
        super("help");
    }

    @Override
    public Message fire(Message command) {
        MessageBuilder output = new MessageBuilder();
        Collection<Command> commands = Commands.getInstance().getActiveCommands();
        String trigger = RoleBot.getInstance().getTrigger();
        output.append("**Bot Commands:**\n");
        for (Command c: commands) {
            output.append(trigger + c + "\n");
        }
        return output.build();
    }

    @Override
    public String getDescription() {
        return "Prints this message";
    }
}
