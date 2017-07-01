package com.sardonic.rolebot.commands;

import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public interface Command {

    Message fire(Message command);

    String getName();
}
