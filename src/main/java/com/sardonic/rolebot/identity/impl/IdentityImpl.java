package com.sardonic.rolebot.identity.impl;

import com.sardonic.rolebot.identity.Identity;

/**
 * 'String, Long' tuple implementation.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class IdentityImpl implements Identity {
    private final String name;
    private final long id;

    protected IdentityImpl(String name, long id){
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String toString(){
        return this.getName() + " " + this.getId();
    }

    @Override
    public int hashCode(){
        int result = 0;
        final int prime = 31;
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + ((int) (id ^ (id >>> 32)));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (this == obj || this.getClass() == obj.getClass() && hashCode() == obj.hashCode());
    }
}
