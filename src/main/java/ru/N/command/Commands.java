package ru.N.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.N.Chat;

import static ru.N.util.MessageUtils.sendFormattedMessage;


public class Commands implements BasicCommand {
    private final Chat plugin;
    public Commands(Chat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {

        if (args.length == 0) {
            source.getSender().sendMessage("NChat v1.0");
            source.getSender().sendMessage("/nchat reload");
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!(source.getSender() instanceof Player)) {
                source.getSender().sendMessage("Команда только для игроков");
                return;
            }
            if (source.getSender().hasPermission("nchat.reload")) {
                plugin.reloadConfig();
                source.getSender().sendMessage("NChat перезагружен!");
                return;
            } else {
                sendFormattedMessage((Player) source.getSender(), plugin.getConfig().getString("misc.permission", "У вас нет прав!"));
                return;

            }
        }
        if (args[0].equalsIgnoreCase("clear")) {
            if (!(source.getSender() instanceof Player)) {
                source.getSender().sendMessage("Команда только для игроков");
                return;
            }
            if (source.getSender().hasPermission("nchat.clear")) {
                for (int i = 0; i < 200; i++) {
                    Bukkit.getServer().broadcast(Component.empty());
                }
            } else {
                sendFormattedMessage((Player) source.getSender(), plugin.getConfig().getString("misc.permission", "У вас нет прав!"));
                return;
            }

            source.getSender().sendMessage("Чат был очищен");
            return;
        }
        source.getSender().sendMessage("Неизвестная подкоманда.");
    }

}
