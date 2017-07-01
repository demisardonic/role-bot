package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.RoleBot;
import com.sardonic.rolebot.identity.ChannelIdentity;
import com.sardonic.rolebot.identity.RoleIdentity;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;

/**
 * Creates a {@link net.dv8tion.jda.core.entities.TextChannel} and a corresponding {@link Role}. The channel cannot be viewed by a member, unless they have this role.
 * Created by Micky Lindsay on 6/30/2017.
 */
public class CreateCommand extends AbstractCommand {
    public CreateCommand() {
        super("create");
    }

    @Override
    public Message fire(Message command) {
        RoleBot bot = RoleBot.getInstance();
        MessageBuilder output = new MessageBuilder();
        String[] split = command.getContent().split(" ", 2);
        if (split.length < 2) {
            output.append("Please include a channel name.");
        } else {
            String name = split[1].replaceAll(" ", "-");
            if (bot.getChannelList().contains(name)) {
                output.append("Channel already exists.");
            } else {
                try {
                    Role role = bot.getController().createRole().setName(name).setMentionable(true).complete();
                    ChannelAction action = bot.getController().createTextChannel(name);
                    action.addPermissionOverride(bot.getServer().getPublicRole(), 0, Permission.MESSAGE_READ.getRawValue());
                    action.addPermissionOverride(role, Permission.MESSAGE_READ.getRawValue(), 0);
                    Channel channel = action.complete();

                    bot.getRoleList().add(new RoleIdentity(name, role.getIdLong()));
                    bot.getChannelList().add(new ChannelIdentity(name, channel.getIdLong()));
                    bot.updateFile();
                    output.append("Successfully created channel.");
                } catch (PermissionException e) {
                    output.append("I do not have permission to create channels in this server.");
                } catch (Exception e) {
                    output.append("Failed to create channel");
                }
            }
        }
        return output.build();
    }

    @Override
    public String getDescription() {
        return "Creates a new text channel and corresponding role.";
    }
}
