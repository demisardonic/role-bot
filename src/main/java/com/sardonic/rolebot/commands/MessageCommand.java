package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 7/20/2017.
 */
public abstract class MessageCommand extends AbstractCommand{
    private MessageBuilder builder;
    protected MessageCommand(String name) {
        super(name);
    }

    protected abstract void buildMessage(Message command, MessageBuilder builder);

    @Override
    public final Message fire(Message command) {
        builder = new MessageBuilder();
        buildMessage(command, builder);
        return builder.build();
    }
}
