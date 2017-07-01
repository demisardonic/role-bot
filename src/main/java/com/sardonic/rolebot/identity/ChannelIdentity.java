package com.sardonic.rolebot.identity;

import com.sardonic.rolebot.identity.impl.IdentityImpl;

/**
 * Implementation for channel id and name pair.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class ChannelIdentity extends IdentityImpl {
    public ChannelIdentity(String name, long id){
        super(name, id);
    }
}
