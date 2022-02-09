package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.data.PlayerBackpack;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.enchants.PrisonEnchants;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.rankup.RankUpPrices;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.math.BigInteger;
import java.util.ArrayList;

public class BackpackMenus {
    //Main menu
    public static void main() {
        int slotCost = 1;
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                BigInteger amount = BigInteger.valueOf(1);
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToBigInteger(amount) + " Slot","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToBigInteger(BigInteger.valueOf(slotCost).multiply(amount)) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(0).setAmount(1);
                amount = BigInteger.valueOf(10);
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToBigInteger(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToBigInteger(BigInteger.valueOf(slotCost).multiply(amount)) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(1).setAmount(2);
                amount = BigInteger.valueOf(100);
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToBigInteger(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToBigInteger(BigInteger.valueOf(slotCost).multiply(amount)) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(2).setAmount(3);
                amount = BigInteger.valueOf(1000);
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToBigInteger(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToBigInteger(BigInteger.valueOf(slotCost).multiply(amount)) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(3).setAmount(4);
                amount = BigInteger.valueOf(10000);
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToBigInteger(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToBigInteger(BigInteger.valueOf(slotCost).multiply(amount)) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(4).setAmount(5);
                amount = BigInteger.valueOf(1000000);
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToBigInteger(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToBigInteger(BigInteger.valueOf(slotCost).multiply(amount)) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(5).setAmount(6);
                amount = BigInteger.valueOf(100000000);
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToBigInteger(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToBigInteger(BigInteger.valueOf(slotCost).multiply(amount)) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(6).setAmount(7);
                amount = playerData.getTokens().divide(BigInteger.valueOf(slotCost));
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToBigInteger(amount) + " Slots", ChatColor.GRAY + "" + ChatColor.ITALIC + "Max you can buy with your current tokens", "",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToBigInteger(BigInteger.valueOf(slotCost).multiply(amount)) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(7).setAmount(64);
            }
            void upgrade(Player player, BigInteger slotsToBuy) {
                PlayerData playerData = new PlayerData(player);
                if (playerData.getTokens().compareTo(slotsToBuy.multiply(BigInteger.valueOf(slotCost))) > -1) {
                    playerData.removeTokens(slotsToBuy.multiply(BigInteger.valueOf(slotCost)));
                    PlayerBackpack playerBackpack = playerData.getBackpack();
                    playerBackpack.setSize(playerBackpack.getSize().add(slotsToBuy));
                    playerBackpack.updateIsFull(); //Cannot add blocks to backpack if backpack is full, then upgraded, then this method is not called
                    playerData.setBackpack(playerBackpack);
                    player.sendMessage(ChatColor.AQUA + "You've successfully upgraded your backpack!");
                    open(player);
                } else player.sendMessage(ChatColor.RED + "You do not have enough tokens to prestige!");
            }
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(1));
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(1));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(1));
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(1));
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(1));
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(1));
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(1));
            }
            @Override
            public void item7Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), new PlayerData((Player) e.getWhoClicked()).getTokens().divide(BigInteger.valueOf(slotCost)));
            }
        };
        guiPage.identifier = "upgradeBackpack";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Your Backpack");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
