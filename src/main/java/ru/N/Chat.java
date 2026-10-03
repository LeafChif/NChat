package ru.N;

import org.bukkit.plugin.java.JavaPlugin;
import ru.N.command.Commands;
import ru.N.listener.ChatListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public final class Chat extends JavaPlugin {


    public final Set<UUID> spyPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerCommand("nchat", new Commands(this));


        this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getComponentLogger().info("");
        getComponentLogger().info("Запуск плагина " + getName() + " " + "версия " + getPluginMeta().getVersion());
        getComponentLogger().info("");
    }


    @Override
    public void onDisable() {
        getComponentLogger().info("Выключается плагин");
    }
}