package com.sardonic.rolebot;

import com.sardonic.rolebot.exceptions.BotException;
import com.sardonic.rolebot.identity.ChannelIdentity;
import com.sardonic.rolebot.identity.ChannelList;
import com.sardonic.rolebot.identity.RoleIdentity;
import com.sardonic.rolebot.identity.RoleList;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Core bot class which stores all of the information utilized by the bot.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class RoleBot {
    private static RoleBot ourInstance;
    private static String clientId;

    public static RoleBot getInstance() {
        return ourInstance;
    }

    public static void instantiate(JDA jda, String path, String id) {
        clientId = id;
        if (ourInstance == null) {
            try {
                ourInstance = new RoleBot(jda, path);
            } catch (BotException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private final String path;
    private String trigger;
    private Guild server;
    private GuildController controller;
    private RoleList roleList;
    private ChannelList channelList;
    private Map<Long, Role> roles;
    private Map<Integer, Role> colorRoles;
    private Map<Long, TextChannel> channels;

    private RoleBot(JDA jda, String path) throws BotException {
        this.path = path;
        this.trigger = "!!";


        if (jda.getGuilds().size() == 1) {
            this.server = jda.getGuilds().get(0);
        } else {
            throw new BotException("Bot is active in more than one server.");
        }
        this.controller = new GuildController(server);
        this.roleList = new RoleList();
        this.channelList = new ChannelList();
        this.roles = new HashMap<>();
        this.colorRoles = new HashMap<>();
        this.channels = new HashMap<>();

        File f = new File(path);
        try {
            Scanner scan = new Scanner(f);
            while (scan.hasNext()) {
                RoleIdentity role = new RoleIdentity(scan.next(), scan.nextLong());
                roleList.add(role);
                ChannelIdentity channel = new ChannelIdentity(scan.next(), scan.nextLong());
                channelList.add(channel);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw new BotException(e);
        }
    }

    public Guild getServer() {
        return server;
    }

    public void updateFile() {
        File f = new File(path);
        FileWriter fw;
        try {
            fw = new FileWriter(f);
            for (int i = 0; i < roleList.size(); i++) {
                fw.write(roleList.get(i) + " " + channelList.get(i) + " ");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Role getRole(RoleIdentity identity) {
        long id = identity.getId();
        if (roles.containsKey(id)) {
            return roles.get(id);
        }
        Role role = server.getRoleById(id);
        if (role == null) {
            return null;
        }
        roles.put(id, role);
        return role;
    }

    public TextChannel getChannel(ChannelIdentity identity) {
        long id = identity.getId();
        if (channels.containsKey(id)) {
            return channels.get(id);
        }
        TextChannel channel = server.getTextChannelById(id);
        if (channel == null) {
            return null;
        }
        channels.put(id, channel);
        return channel;
    }

    public Role getColorRole(int color){
        return colorRoles.get(color & 0xffffff);
    }

    public Role getAttachedRole(Channel channel) {
        int index = channelList.indexOf(channel.getIdLong());
        return index < 0 ? null : getRole(roleList.get(index));
    }

    public TextChannel getAttachedChannel(Role role) {
        int index = roleList.indexOf(role.getIdLong());
        return index < 0 ? null : getChannel(channelList.get(index));
    }

    public ChannelList getChannelList() {
        return channelList;
    }

    public RoleList getRoleList() {
        return roleList;
    }

    public GuildController getController() {
        return controller;
    }

    public String getTrigger() {
        return trigger;
    }

    public Member getBotMember(){
        return server.getMemberById(clientId);
    }

    public Role getBotRole(){
        return getBotMember().getRoles().stream().filter(role -> role.isManaged()).findAny().get();
    }
}
