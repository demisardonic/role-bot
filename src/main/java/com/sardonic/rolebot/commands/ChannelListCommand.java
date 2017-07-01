package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.identity.ChannelList;

/**
 * Abstract class for commands which only use the channel list stored within the bot.
 * Created by Micky Lindsay on 6/30/2017.
 */
abstract class ChannelListCommand extends SingleListCommand<ChannelList>{

    ChannelListCommand(String name, ChannelList list){
        super(name, list);
    }
}
