package com.winermine;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerManager extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "PlayerManager Activated Successfully");
        getServer().getPluginCommand("onlineplayers").setExecutor(new OnlinePlayersCommand());
        getServer().getPluginCommand("bannedplayers").setExecutor(new BannedPlayersCommand());
        getServer().getPluginCommand("whitelistmanage").setExecutor(new WhiteListPlayersCommand());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "PlayerManager Disabled Successfully");
    }
}
