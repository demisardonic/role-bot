package com.sardonic.rolebot.identity;

import com.sardonic.rolebot.identity.impl.IdentityImpl;

/**
 * Implementation for role id and name pair.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class RoleIdentity extends IdentityImpl {

    public RoleIdentity(String name, long id) {
      super(name, id);
    }
}
