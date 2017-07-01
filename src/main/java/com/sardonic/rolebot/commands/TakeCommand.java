package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.RoleBot;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

/**
 * Removes the {@link Role} attached to the mentioned {@link net.dv8tion.jda.core.entities.TextChannel} from the command executing member.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class TakeCommand extends AbstractCommand{
    public TakeCommand() {
        super("take");
    }

    @Override
    public Message fire(Message command) {
        RoleBot bot = RoleBot.getInstance();
        MessageBuilder output = new MessageBuilder();
        if (command.getMentionedChannels().size() < 1) {
            output.append("Please mention a channel.");
        } else {
            Channel mentionedChannel = command.getMentionedChannels().get(0);
            Role role = bot.getAttachedRole(mentionedChannel);
            if (role != null) {
                if (command.getMember().getRoles().contains(role)) {
                    bot.getController().removeRolesFromMember(command.getMember(), role).queue();
                    output.append("Done.");
                } else {
                    output.append("You don't have that role.");
                }
            } else {
                output.append("Channel is not currently being handled.");
            }
        }
        return output.build();
    }

    @Override
    public String getDescription() {
        return "Removes the role attached to the mentioned channel from the user.";
    }
}
