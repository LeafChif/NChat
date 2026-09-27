package ru.N;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.N.listener.ChatListener;


public final class Chat extends JavaPlugin {

    public String name = "NChat";
    public double version = 1.0;


    @Override
    public void onEnable() {
        saveDefaultConfig();


        this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getComponentLogger().info("");
        getComponentLogger().info("Запуск плагина " + name + " " + "версия " + version);
        getComponentLogger().info("");
    }


    @Override
    public void onDisable() {
        getComponentLogger().info("Выключается плагин");
    }
}