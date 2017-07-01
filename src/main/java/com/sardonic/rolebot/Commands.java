package com.sardonic.rolebot;

import com.sardonic.rolebot.commands.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class which stores every active command and handles firing them.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class Commands {
    private static final Commands ourInstance = new Commands();

    public static Commands getInstance() {
        return ourInstance;
    }

    private final Map<String, Command> activeCommands;

    private Commands() {
        activeCommands = new HashMap<>();
    }

    /**
     * Activates the given command into a executable state.
     *
     * @param command {@link Command} to activate
     */
    public void activateCommand(Command command) {
        activeCommands.put(command.getName(), command);
    }

    /**
     * Deactivates the given command making it un-executable.
     *
     * @param command {@link Command} to deactivate
     * @return
     */
    public Command deactivateCommand(Command command) {
        return activeCommands.remove(command.getName());
    }

    /**
     * Returns a list of all currently activated commands.
     * @return
     */
    public Collection<Command> getActiveCommands() {
        return activeCommands.values();
    }

    /**
     * Fires a command based on the context string within the message.
     * Command executes with respect to the given TextChannel.
     *
     * @param channel {@link TextChannel} which the {@link Command} is fired from.
     * @param message {@link Message} which fired the {@link Command}
     */
    void triggerCommand(TextChannel channel, Message message) {
        String name = message.getContent().split(" ")[0].substring(RoleBot.getInstance().getTrigger().length());
        Command c = activeCommands.get(name);
        if (c != null) {
            Message output = c.fire(message);
            if (output != null) {
                channel.sendMessage(output).queue();
            }
        }
    }
}
