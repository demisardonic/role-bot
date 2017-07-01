package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.identity.IdentityList;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public abstract class SingleListCommand<E extends IdentityList> extends AbstractCommand{
    public E list;

    public SingleListCommand(String name, E list){
        super(name);
        this.list = list;
    }

    public E getList(){
        return list;
    }
}
