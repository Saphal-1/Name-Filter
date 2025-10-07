package com.namefilter;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class NameFilterPlugin extends JavaPlugin implements Listener {
    private List<String> bannedWords;
    private String kickMessage;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        bannedWords = getConfig().getStringList("banned-words");
        kickMessage = getConfig().getString("kick-message");
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("✅ NameFilter Plugin enabled!");
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String name = event.getPlayer().getName().toLowerCase();
        for (String bad : bannedWords) {
            if (name.contains(bad.toLowerCase())) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMessage);
                getLogger().info("🚫 Blocked player: " + name);
                return;
            }
        }
    }
}
