package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.RoleBot;
import com.sardonic.rolebot.identity.RoleIdentity;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public class GibCommand extends AbstractCommand{

    public GibCommand() {
        super("gib");
    }

    @Override
    public Message fire(Message command) {
        RoleBot bot = RoleBot.getInstance();
        MessageBuilder output = new MessageBuilder();
        Role gibbedRole = null;
        if (command.getMentionedChannels().size() < 1) {
            RoleIdentity identity = bot.getRoleList().find(command.getContent().split(" ")[1].trim());
            if (identity != null) {
                gibbedRole = bot.getRole(identity);
            }
        } else {
            Channel mentionedChannel = command.getMentionedChannels().get(0);
            gibbedRole = bot.getAttachedRole(mentionedChannel);
        }

        if (gibbedRole != null) {
            if (!command.getMember().getRoles().contains(gibbedRole)) {
                bot.getController().addRolesToMember(command.getMember(), gibbedRole).queue();
                output.append("Done.");
            } else {
                output.append("You already have that role.");
            }
        }
        return output.build();
    }
}
