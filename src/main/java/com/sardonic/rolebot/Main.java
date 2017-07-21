package com.sardonic.rolebot;

import com.sardonic.rolebot.commands.*;
import com.sardonic.rolebot.commands.decorator.ModifyChannelCommandDecorator;
import com.sardonic.rolebot.exceptions.BotException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

class Main {

    public static void main(String[] args) throws BotException {

        String token = "";
        String roleFilePath = "role.txt";
        String clientId = "";

        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("-token")){
                token = args[++i];
            }else if(args[i].equals("-roles")){
                roleFilePath = args[++i];
            }else if (args[i].equals("-id")){
                clientId = args[++i];
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

        RoleBot.instantiate(jda, roleFilePath, clientId);
        Commands commands = Commands.getInstance();
        commands.registerCommand(new ChannelsCommand());
        commands.registerCommand(new InChannelCommand());
        commands.registerCommand(new GibCommand());
        commands.registerCommand(new TakeCommand());
        commands.registerCommand(new ColorCommand());
        commands.registerCommand(new ModifyChannelCommandDecorator(new HandleCommand()));
        commands.registerCommand(new ModifyChannelCommandDecorator(new HandleCommand()));
        commands.registerCommand(new ModifyChannelCommandDecorator(new CreateCommand()));
        commands.registerCommand(new ModifyChannelCommandDecorator(new DeleteCommand()));
        commands.registerCommand(new HelpCommand());
        commands.registerCommand(new SimpleMessageCommand("henlo", "Henlo you stinky user.", "Just says henlo."));

        BotListener listener = new BotListener();
        jda.addEventListener(listener);
    }
}
