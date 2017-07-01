package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.RoleBot;
import com.sardonic.rolebot.identity.ChannelIdentity;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Outputs a message listing all of the text channels handled by the bot.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class ChannelsCommand extends ChannelListCommand{

    public ChannelsCommand() {
        super("channels", RoleBot.getInstance().getChannelList());
    }

    @Override
    public Message fire(Message command) {
        RoleBot bot = RoleBot.getInstance();
        MessageBuilder output = new MessageBuilder();
        for (ChannelIdentity chanIden : getList()) {
            TextChannel chan = bot.getChannel(chanIden);
            if (chan != null) {
                output.append(chan);
                output.append("\n");
            } else {
                String s = chanIden.getId() + " : is not valid channel.";
                output.append(s);
            }
        }
        return output.build();
    }

    @Override
    public String getDescription() {
        return "Generates a list of all channels managed by the bot.";
    }
}
