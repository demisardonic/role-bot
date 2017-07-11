package com.sardonic.rolebot.commands;

import com.sardonic.rolebot.RoleBot;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Micky Lindsay on 7/10/2017.
 */
public class ColorCommand extends NoOutputCommand {
    public ColorCommand() {
        super("color");
    }

    @Override
    public void outputlessFire(Message command) {
        RoleBot bot = RoleBot.getInstance();
        Member author = command.getMember();
        String[] parts = command.getContent().split(" ");
        if (parts.length == 2) {
            if (parts[1].startsWith("#")) {
                parts[1] = parts[1].substring(1);
            } else if (parts[1].startsWith("0x")) {
                parts[1] = parts[1].substring(2);
            }
            int colorHex = Integer.parseInt(parts[1], 16) & 0xffffff;
            Color color = new Color(colorHex >> 16, (colorHex >> 8 & 0xff), (colorHex & 0xff));

            Collection<Role> removeList = author.getRoles().stream().filter(role -> role.getName().equals("~color~")).collect(Collectors.toCollection(ArrayList::new));
            Collection<Role> addList = new ArrayList<>();
            Collection<Role> colorRoles = command.getGuild().getRolesByName("~color~", true);

            Optional<Role> matchingColorRoles = colorRoles.stream().filter(role -> role.getColor().equals(color)).findFirst();
            Role role;
            if (matchingColorRoles.isPresent()) {
                role = matchingColorRoles.get();
            } else {
                role = bot.getController().createRole().setName("~color~").setColor(color).setPermissions().complete();
                bot.getController().modifyRolePositions().selectPosition(role).selectPosition(bot.getBotRole());
            }
            addList.add(role);
            bot.getController().modifyMemberRoles(author, addList, removeList).complete();

            colorRoles.forEach(tmpRole -> {
                if (bot.getServer().getMembersWithRoles(tmpRole).isEmpty()){
                    tmpRole.delete().queue();
                }
            });
        }
    }

    @Override
    public String getDescription() {
        return "Gives the author of the command a role for the desired color.";
    }
}
