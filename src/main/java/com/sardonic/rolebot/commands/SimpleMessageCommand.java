package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 7/20/2017.
 */
public class SimpleMessageCommand extends MessageCommand {

    private String name;
    private String output;
    private String desc;

    public SimpleMessageCommand(String name, String output) {
        this(name, output, null);
    }

    public SimpleMessageCommand(String name, String output, String desc) {
        super(name);
        this.name = name;
        this.output = output;
        this.desc = desc;
    }


    @Override
    public final void buildMessage(Message command, MessageBuilder builder) {
        builder.append(output);
    }

    @Override
    public final String getDescription() {
        return desc == null ? name : desc;
    }
}
