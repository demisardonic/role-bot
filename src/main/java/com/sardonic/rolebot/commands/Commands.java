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

    public void trigger(TextChannel channel, String command) {
        String[] parts = command.split(" ", 2);
        String name = "";
        String argument = "";
        if(parts.length > 0){
            name = parts[0];
            if(parts.length > 1){
                argument = parts[1];
            }
        }

        Command c = activeCommands.get(name);
        if (c != null) {
            Message output = c.fire(argument);
            if (output != null) {
                channel.sendMessage(output).queue();
            }
        }

    }
}
