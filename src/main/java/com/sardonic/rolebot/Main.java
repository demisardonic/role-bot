package com.sardonic.rolebot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

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

        try {
            JDA jda = builder.buildBlocking();
            jda.addEventListener(new BotListener());
        } catch (InterruptedException e) {
            throw new BotException(e);
        } catch (RateLimitedException e) {
            throw new BotException(e);
        } catch (LoginException e) {
            throw new BotException(e);
        }

    }
}
