package com.sardonic.rolebot;

import com.sardonic.rolebot.commands.*;
import com.sardonic.rolebot.commands.decorator.ModifyChannelCommandDecorator;
import com.sardonic.rolebot.exceptions.BotException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main {

    public static void main(String[] args) throws BotException {

        String token = "";
        String roleFilePath = "role.txt";

        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("-token")){
                token = args[++i];
            }else if(args[i].equals("-roles")){
                roleFilePath = args[++i];
            }
        }

        if(token.length() == 0){
            throw new BotException("Missing bot token.");
        }

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        JDA jda;

        try {
            jda = builder.buildBlocking();
        } catch (Exception e) {
            throw new BotException(e);
        }

        RoleBot.getInstance(jda, roleFilePath);
        Commands commands = Commands.getInstance();
        commands.addCommand(new ChannelsCommand());
        commands.addCommand(new InChannelCommand());
        commands.addCommand(new GibCommand());
        commands.addCommand(new TakeCommand());
        commands.addCommand(new ModifyChannelCommandDecorator(new HandleCommand()));
        commands.addCommand(new ModifyChannelCommandDecorator(new CreateCommand()));
        commands.addCommand(new ModifyChannelCommandDecorator(new DeleteCommand()));

        BotListener listener = new BotListener();
        jda.addEventListener(listener);
    }
}
