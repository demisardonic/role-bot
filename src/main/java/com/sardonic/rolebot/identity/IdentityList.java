package com.sardonic.rolebot.identity;

import com.sardonic.rolebot.identity.Identity;

import java.util.ArrayList;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public class IdentityList<E extends Identity> extends ArrayList<E> {

    public boolean contains(long id) {
        return indexOf(id) > -1;
    }

    public boolean contains(String name) {
        return indexOf(name) > -1;
    }

    public int indexOf(long id) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public int indexOf(String name) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean add(E identity) {
        if (identity == null) {
            return false;
        }
        if(contains(identity.getName())){
            return false;
        }
        if (contains(identity.getId())){
            return false;
        }
        return super.add(identity);
    }

    public E find(long id){
        int index = indexOf(id);
        return index > -1 ? get(index) : null;
    }

    public E find(String name){
        if (name.charAt(0) == '#'){
            name = name.substring(1);
        }
        int index = indexOf(name);
        return index > -1 ? get(index) : null;
    }
}
