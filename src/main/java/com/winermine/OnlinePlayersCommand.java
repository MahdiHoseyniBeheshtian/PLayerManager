package com.winermine;

import com.sun.java.accessibility.util.GUIInitializedListener;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public class OnlinePlayersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {
            Gui playerGui = new Gui(6, ChatColor.AQUA + "Online Players");

            for (Player p : Bukkit.getOnlinePlayers()) {
                ItemStack player = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                ItemMeta playerMeta = player.getItemMeta();
                playerMeta.setDisplayName(ChatColor.BLUE + p.getName());
                player.setItemMeta(playerMeta);

                GuiItem guiItem = new GuiItem(player, event -> {
                    event.setCancelled(true);
                    String playerName = player.getItemMeta().getDisplayName();
                    Gui optionsGui = new Gui(3, ChatColor.AQUA + playerName + " Options");

                    ItemStack banOptions = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    ItemMeta BanMeta = banOptions.getItemMeta();
                    BanMeta.setDisplayName(ChatColor.RED + "Ban " + playerName);
                    banOptions.setItemMeta(BanMeta);

                    GuiItem BanItem = new GuiItem(banOptions, banEvent -> {
                        banEvent.setCancelled(true);
                        Bukkit.getBanList(BanList.Type.NAME).addBan(playerName, "banned forever", null, null);
                        p.kickPlayer("banned by admin");
                        commandSender.sendMessage(ChatColor.GREEN + playerName + " Ba Movafagiyat Ban Shod!");
                    });

                    ItemStack kickOptions = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                    ItemMeta kickMeta = kickOptions.getItemMeta();
                    kickMeta.setDisplayName(ChatColor.YELLOW + "Kick " + playerName);
                    kickOptions.setItemMeta(kickMeta);

                    GuiItem kickItem = new GuiItem(kickOptions, kickEvent -> {
                        kickEvent.setCancelled(true);
                        p.kickPlayer("kicked by admin");
                        commandSender.sendMessage(ChatColor.GREEN + playerName + " Ba Movafagiyat Ban Shod!");
                    });

                    ItemStack barrier = new ItemStack(Material.BARRIER);
                    ItemMeta meta4 = barrier.getItemMeta();
                    meta4.setDisplayName("Exit");
                    barrier.setItemMeta(meta4);

                    GuiItem barrierItem = new GuiItem(barrier, event1 -> {
                        event1.setCancelled(true);
                        Player player2 = (Player) commandSender;
                        player2.closeInventory();
                    });

                    optionsGui.setItem(10, BanItem);
                    optionsGui.setItem(13, kickItem);
                    optionsGui.setItem(16, barrierItem);

                    if (commandSender instanceof Player) {
                        Player player1 = (Player) commandSender;
                        optionsGui.open(player1);
                    } else {
                        commandSender.sendMessage(ChatColor.RED + "This Command Is Not Executable From Console");
                    }
                });
                playerGui.addItem(guiItem);
            }

            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                playerGui.open(player);
            } else {
                commandSender.sendMessage(ChatColor.RED + "This Command Is Not Executable From Console");
            }
        }else {
            commandSender.sendMessage(ChatColor.RED + "This Command Is Not Executable From Console");
        }
        return false;
    }
}
