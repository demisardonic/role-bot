package com.sardonic.rolebot.exceptions;

/**
 * Created by Micky Lindsay on 6/29/2017.
 */
public class BotException extends Exception {

    public BotException(String message){
        super(message);
    }

    public BotException(Throwable e){
        super(e);
    }
}
