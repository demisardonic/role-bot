package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.identity.ChannelList;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public abstract class ChannelListCommand extends SingleListCommand<ChannelList>{

    protected ChannelListCommand(String name, ChannelList list){
        super(name, list);
    }
}
