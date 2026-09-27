package ru.N.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.N.Chat;

import static ru.N.util.MessageUtils.sendFormattedMessage;

public class ChatListener implements Listener {

    private final Chat plugin;

    public ChatListener(Chat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onAsyncChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Component message = event.message();


        String text = PlainTextComponentSerializer.plainText()
                .serialize(message);

        event.setCancelled(true);

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (text.startsWith("!") && text.length() > 1) {

                String cleanText = text.substring(1);
                String messageG = plugin.getConfig().getString("global.message", "G %player% » %message%").replace("%message%", cleanText).replace("%player%", player.getName());

                for (Player players : Bukkit.getOnlinePlayers()) {
                    sendFormattedMessage(players, messageG);

                }
                return;
            }

            String messageL = plugin.getConfig().getString("local.message", "L %player% » %message%").replace("%message%", text).replace("%player%", player.getName());
            int radius = plugin.getConfig().getInt("local.radius", 100);
            String nobody = plugin.getConfig().getString("local.nobody-nearby", "В радиусе %radius% не кого нету!").replace("%radius%", String.valueOf(radius));


            var nearbyPlayers = player.getLocation().getNearbyPlayers(radius);

            sendFormattedMessage(player, messageL);
            if (nearbyPlayers.isEmpty()) {
                sendFormattedMessage(player, nobody);

                return;
            }
            for (Player player1 : nearbyPlayers) {
                sendFormattedMessage(player1, messageL);
            }

        });
    }


}