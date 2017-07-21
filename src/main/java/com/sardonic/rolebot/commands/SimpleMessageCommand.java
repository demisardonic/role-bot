package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 7/20/2017.
 * <p>
 * Simple command type which does nothing more than output a message when fired.
 * <p>Not intended to be subclassed.</p>
 */
public class SimpleMessageCommand extends MessageCommand {

    /**
     * The name which triggers the command
     */
    private final String name;
    /**
     * The output text to be sent upon fire.
     */
    private final String output;
    /**
     * Optional command description.
     */
    private final String desc;

    /**
     * Creates a new simple message which is triggered by the given name and will output the given string.
     * <p>The description of this command is equal to the given name.</p>
     *
     * @param name   name which fires the command
     * @param output output which is sent as message
     */
    public SimpleMessageCommand(String name, String output) {
        this(name, output, null);
    }

    /**
     * Creates a new simple message which is triggered by the given name and will output the given string.
     * <p>The command has a description as provided as argument.</p>
     *
     * @param name   name which fires the command
     * @param output output which is sent as message
     */
    public SimpleMessageCommand(String name, String output, String desc) {
        super(name);
        this.name = name;
        this.output = output;
        this.desc = desc;
    }


    @Override
    protected final void buildMessage(Message command, MessageBuilder builder) {
        builder.append(output);
    }

    @Override
    public final String getDescription() {
        return desc == null ? name : desc;
    }
}
