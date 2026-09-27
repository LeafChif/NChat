package ru.N.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.N.Chat;

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
                    players.sendMessage(messageG);

                }
                return;
            }

            String messageL = plugin.getConfig().getString("local.message", "L %player% » %message%").replace("%message%", text).replace("%player%", player.getName());
            int radius = plugin.getConfig().getInt("local.radius", 100);
            String radiuss = String.valueOf(radius);
            var nearbyPlayers = player.getLocation().getNearbyPlayers(radius);

            player.sendMessage(messageL);
            if (nearbyPlayers.isEmpty()) {
                player.sendMessage("Не кого в радиусе " + radiuss.replace("%radius%", String.valueOf(radius)));
                return;
            }
            for (Player player1 : nearbyPlayers) {
                player1.sendMessage(messageL);
            }

        });
    }


}