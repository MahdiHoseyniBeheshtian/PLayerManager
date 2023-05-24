package com.winermine;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class BannedPlayersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player) {
            Player player1 = (Player) commandSender;
            Gui playerGui = new Gui(6, ChatColor.AQUA + "Banned Players");

            for (OfflinePlayer p : Bukkit.getBannedPlayers()) {
                ItemStack player = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                ItemMeta playerMeta = player.getItemMeta();
                playerMeta.setDisplayName(ChatColor.BLUE + p.getName());
                player.setItemMeta(playerMeta);

                GuiItem guiItem = new GuiItem(player, event -> {
                    event.setCancelled(true);
                    ItemStack unbanOptions = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    String playerName = p.getName();
                    Gui optionsGui = new Gui(3, ChatColor.AQUA + "Manage " + ChatColor.RED + playerName);
                    ItemMeta unbanMeta = unbanOptions.getItemMeta();
                    unbanMeta.setDisplayName(ChatColor.GREEN + "unban " + playerName);
                    unbanOptions.setItemMeta(unbanMeta);

                    GuiItem unbanItem = new GuiItem(unbanOptions, unbanEvent -> {
                        unbanEvent.setCancelled(true);
                        Bukkit.getBanList(BanList.Type.NAME).pardon(playerName);
                        commandSender.sendMessage(ChatColor.GREEN + playerName + " Ba Movafagiyat unban Shod!");
                    });

                    ItemStack exitOptions = new ItemStack(Material.BARRIER);
                    ItemMeta exitMeta = exitOptions.getItemMeta();
                    exitMeta.setDisplayName(ChatColor.RED + "exit ");
                    exitOptions.setItemMeta(exitMeta);

                    GuiItem exitItem = new GuiItem(exitOptions, exitEvent -> {
                        exitEvent.setCancelled(true);
                        player1.closeInventory();
                    });

                    optionsGui.setItem(12 , unbanItem);
                    optionsGui.setItem(14 , exitItem);

                    optionsGui.open(player1);
                });

                playerGui.addItem(guiItem);
            }

            playerGui.open(player1);
        }else {
            commandSender.sendMessage(ChatColor.RED + "This Command Is Not Executable From Console");
        }

        return false;
    }
}
