package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.identity.IdentityList;

/**
 * Abstract Command which utilizes only a single IdentityList.
 * Created by Micky Lindsay on 6/30/2017.
 */
abstract class SingleListCommand<E extends IdentityList> extends AbstractCommand{
    private final E list;

    SingleListCommand(String name, E list){
        super(name);
        this.list = list;
    }

    E getList(){
        return list;
    }
}
