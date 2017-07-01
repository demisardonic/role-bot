package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public class Commands {
    private static Commands ourInstance = new Commands();

    public static Commands getInstance() {
        return ourInstance;
    }

    private Map<String, Command> activeCommands;

    private Commands() {
        activeCommands = new HashMap<>();
    }

    public Command addCommand(Command command) {
        activeCommands.put(command.getName(), command);
        return command;
    }

    public Command removeCommand(String name) {
        return activeCommands.remove(name);
    }

    public void trigger(TextChannel channel, Message command) {
        String name = command.getContent().split(" ")[0].substring(1);

        Command c = activeCommands.get(name);
        if (c != null) {
            Message output = c.fire(command);
            if (output != null) {
                channel.sendMessage(output).queue();
            }
        }

    }
}
