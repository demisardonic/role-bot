package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.RoleBot;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public class DeleteCommand extends AbstractCommand{
    public DeleteCommand() {
        super("delete");
    }

    @Override
    public Message fire(Message command) {
        RoleBot bot = RoleBot.getInstance();
        MessageBuilder output = new MessageBuilder();
        if (command.getMentionedChannels().size() < 1) {
            output.append("Please mention a channel.");
        } else {
            Channel channel = command.getMentionedChannels().get(0);
            int index = bot.getChannelList().indexOf(channel.getIdLong());
            if (index < 0) {
                output.append("Channel is not currently being handled.");
            } else {
                Role role = bot.getAttachedRole(channel);
                if (role == null) {
                    output.append("Channel has no attached Role.");
                } else {
                    role.delete().queue();
                    bot.getRoleList().remove(index);
                    bot.getChannelList().remove(index);
                    bot.updateFile();
                    output.append("Role deleted. Channel can be recovered with !handle <channel name>");
                }
            }
        }
        return output.build();
    }
}
