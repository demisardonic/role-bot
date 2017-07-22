package com.sardonic.rolebot.commands.decorator;

import com.sardonic.rolebot.commands.Command;
import com.sardonic.rolebot.commands.MultiTrigger;

import java.util.Arrays;

/**
 * Created by Micky Lindsay on 7/21/2017.
 */
public class MultiTriggerDecorator extends CommandDecorator implements MultiTrigger{
    String[] triggers;
    public MultiTriggerDecorator(Command innerCommand, String... triggers) {
        super(innerCommand);
        this.triggers = triggers;
    }

    @Override
    public String getDecoratorDesc() {
        return "Command can be triggered by: " + Arrays.toString(triggers);
    }

    @Override
    public String[] getTriggers() {
        return triggers;
    }
}
