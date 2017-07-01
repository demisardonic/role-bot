package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.RoleBot;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

/**
 * Created by Micky Lindsay on 6/30/2017.
 */
public class InChannelCommand extends AbstractCommand {

    public InChannelCommand() {
        super("inchannel");
    }

    @Override
    public Message fire(Message command) {
        RoleBot bot = RoleBot.getInstance();
        MessageBuilder output = new MessageBuilder();
        if (command.getMentionedChannels().size() < 1) {
            output.append("Please mention a channel.");
        } else {
            long mentionedId = command.getMentionedChannels().get(0).getIdLong();
            Role role = bot.getRole(bot.getRoleList().get(bot.getChannelList().indexOf(mentionedId)));
            if(role == null){
                output.append("Channel is not currently being handled.");
            }else {
                List<Member> members = bot.getServer().getMembersWithRoles(role);
                if (members.size() == 0) {
                    output.append("Nobody is in that channel.");
                } else {
                    for (Member member : members) {
                        output.append(member.getEffectiveName());
                    }
                }
            }
        }
        return output.build();
    }
}
