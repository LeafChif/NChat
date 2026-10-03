package ru.N.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.N.Chat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static ru.N.util.MessageUtils.sendFormattedMessage;


public class Commands implements BasicCommand {
    private final Chat plugin;

    public Commands(Chat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (args.length == 0) {
            return;
        }

        if (args[0].equalsIgnoreCase("spy")) {
            if (!(source.getSender() instanceof Player player)) {
                return;
            }
            if (source.getSender().hasPermission("nchat.spy")) {
                if (plugin.spyPlayers.contains(player.getUniqueId())) {
                    plugin.spyPlayers.remove(player.getUniqueId());
                    sendFormattedMessage(source.getSender(), plugin.getConfig().getString("commands.spys", "Слежка за локальными сообщения успешно %state%").replace("%state%", "выключен"));
                    return;
                } else {
                    plugin.spyPlayers.add(player.getUniqueId());
                    sendFormattedMessage(source.getSender(), plugin.getConfig().getString("commands.spys", "Слежка за локальными сообщения успешно %state%").replace("%state%", "включен"));
                    return;
                }
            } else {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString("commands.permission", "У вас нет прав!"));
                return;

            }
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (source.getSender().hasPermission("nchat.reload")) {
                plugin.reloadConfig();
                source.getSender().sendMessage("NChat перезагружен!");
                return;
            } else {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString("commands.permission", "У вас нет прав!"));
                return;

            }
        }

        if (args[0].equalsIgnoreCase("clear")) {
            if (source.getSender().hasPermission("nchat.clear")) {
                for (int i = 0; i < 200; i++) {
                    Bukkit.getServer().broadcast(Component.empty());
                }
            } else {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString("commands.permission", "У вас нет прав!"));
                return;
            }

            source.getSender().sendMessage("Чат был очищен");
            return;
        }

        if (args[0].equalsIgnoreCase("help")) {
            if (source.getSender().hasPermission("nchat.help")) {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString(
                        "commands.help",

                        "%nchat-name% %nchat-version%\n"
                                + "/nchat reload - перезагрузка конфига\n"
                                + "/nchat clear - очистка чата\n"
                                + "/nchat schat - стафф чат").replace("%nchat-name%", plugin.getName()).replace("%nchat-version%", plugin.getPluginMeta().getVersion()));
                return;
            } else {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString("commands.permission", "У вас нет прав!"));
                return;

            }
        }
        if (args[0].equalsIgnoreCase("schat")) {
            if (source.getSender().hasPermission("nchat.chat.staff")) {
                String message = "";
                if (args.length < 2) {
                    return;
                }
                for (int i = 1; i < args.length; i++) {
                    if (!message.isEmpty()) {
                        message = message + " ";
                    }
                    message = message + args[i];
                }
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("nchat.chat.staff")) {
                        sendFormattedMessage(staff, plugin.getConfig().getString("commands.schat", "[SChat] %player% » %message%").replace("%player%", source.getSender().getName()).replace("%message%", message));
                    }
                }
                return;

            } else {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString("commands.permission", "У вас нет прав!"));
                return;
            }
        }


        source.getSender().sendMessage("Неизвестная подкоманда.");
    }
}
