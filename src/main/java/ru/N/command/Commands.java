package ru.N.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (source.getSender().hasPermission("nchat.reload")) {
                plugin.reloadConfig();
                source.getSender().sendMessage("NChat перезагружен!");
                return;
            } else {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString("misc.permission", "У вас нет прав!"));
                return;

            }
        }

        if (args[0].equalsIgnoreCase("clear")) {
            if (source.getSender().hasPermission("nchat.clear")) {
                for (int i = 0; i < 200; i++) {
                    Bukkit.getServer().broadcast(Component.empty());
                }
            } else {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString("misc.permission", "У вас нет прав!"));
                return;
            }

            source.getSender().sendMessage("Чат был очищен");
            return;
        }

        if (args[0].equalsIgnoreCase("help")) {
            if (source.getSender().hasPermission("nchat.help")) {
                sendFormattedMessage(source.getSender(), plugin.getConfig().getString(
                        "misc.help",

                        "%nchat-name% %nchat-version%\n"
                                + "/nchat reload - перезагрузка конфига\n"
                                + "/nchat clear - очистка чата" +

                        "%nchat-name% "  +  "%nchat-version%" +
                        "\n/nchat reload - перезагрузка конфига " +
                        "\n/nchat clear - очистка чата").replace("%nchat-name%", plugin.getName().replace("%nchat-version%", plugin.getPluginMeta().getVersion())));
                return;
            }
        }


        source.getSender().sendMessage("Неизвестная подкоманда.");
    }

}
