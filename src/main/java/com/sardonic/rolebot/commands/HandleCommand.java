package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.RoleBot;
import com.sardonic.rolebot.identity.ChannelIdentity;
import com.sardonic.rolebot.identity.RoleIdentity;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PermissionOverride;
import net.dv8tion.jda.core.entities.Role;

/**
 * Similar to {@link CreateCommand}, but utilizes an existing {@link net.dv8tion.jda.core.entities.TextChannel}.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class HandleCommand extends AbstractCommand {

    public HandleCommand() {
        super("handle");
    }

    @Override
    public Message fire(Message command) {
        RoleBot bot = RoleBot.getInstance();
        MessageBuilder output = new MessageBuilder();
        if (command.getMentionedChannels().size() < 1) {
            output.append("Please mention a channel.");
        } else {
            Channel channel = command.getMentionedChannels().get(0);
            if (!bot.getChannelList().contains(channel.getIdLong())) {
                Role role = bot.getController().createRole().setName(channel.getName()).setMentionable(true).complete();
                PermissionOverride perm = channel.getPermissionOverride(bot.getServer().getPublicRole());
                if(perm == null){
                    channel.createPermissionOverride(bot.getServer().getPublicRole()).setDeny(Permission.MESSAGE_READ).queue();
                }else{
                    perm.getManager().deny(Permission.MESSAGE_READ).queue();
                }
                channel.createPermissionOverride(role).setAllow(Permission.MESSAGE_READ).queue();

                bot.getRoleList().add(new RoleIdentity(channel.getName(), role.getIdLong()));
                bot.getChannelList().add(new ChannelIdentity(channel.getName(), channel.getIdLong()));
                bot.updateFile();
                output.append("Channel is now being handled.");
            }
        }
        return output.build();
    }

    @Override
    public String getDescription() {
        return "Creates a role for the mentioned channel.";
    }
}
