package ru.N.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class MessageUtils {

    public static void sendFormattedMessage(CommandSender player, String miniMessageText) {
            MiniMessage mm = MiniMessage.miniMessage();

            Component parsedComponent = mm.deserialize(miniMessageText);

            player.sendMessage(parsedComponent);
    }
}