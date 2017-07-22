package com.sardonic.rolebot;

import com.sardonic.rolebot.commands.Command;
import com.sardonic.rolebot.logger.Logger;
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

    private Logger logger;

    private static final Commands ourInstance = new Commands();

    public static Commands getInstance() {
        return ourInstance;
    }

    private final Map<String, Command> activeCommands;

    private Commands() {
        activeCommands = new HashMap<>();
        logger = null;
    }

    /**
     * Activates the given command into a executable state.
     *
     * @param command {@link Command} to activate
     */
    public void registerCommand(Command command) {
        activeCommands.put(command.getName(), command);
    }

    /**
     * Deactivates the given command making it un-executable.
     *
     * @param command {@link Command} to deactivate
     * @return
     */
    public Command deregisterCommand(Command command) {
        return activeCommands.remove(command.getName());
    }

    /**
     * Returns a list of all currently activated commands.
     *
     * @return
     */
    public Collection<Command> getActiveCommands() {
        return activeCommands.values();
    }

    /**
     * Changes the logger which is called when an incoming message is received and when an outgoing message is generated.
     * <p>Set to <code>null</code> to disable logging.</p>
     *
     * @param logger
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Returns the logger which is called on incoming and outgoing message.
     * <p>Returns <code>null</code> if logging is not being used.</p>
     * @return
     */
    public Logger getLogger() {
        return logger;
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

            if (logger != null) {
                logger.logIncomingMessage(message);
            }
            Message output = c.fire(message);
            if (logger != null) {
                logger.logOutgoingMessage(output);
            }

            if (output != null) {
                channel.sendMessage(output).queue();
            }
        }
    }
}
