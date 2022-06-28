package net.staticstudios.prisons.customItems.mineBombs;

import com.sk89q.worldedit.math.BlockVector3;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.Prices;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MineBombItem {
    private static final BigInteger VIRTUAL_FORTUNE = BigInteger.valueOf(10000);
    public static void blockPlaced(BlockPlaceEvent e) {
        if (!e.getPlayer().getLocation().getWorld().equals(Constants.MINES_WORLD) && !e.getPlayer().getLocation().getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) return;
        if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER)) return;
        int size = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER);
        double radius = 0;
        switch (size) {
            case 1 -> radius = 15;
            case 2 -> radius = 20;
            case 3 -> radius = 27;
            case 4 -> radius = 40;
        }
        double finalRadius = radius;
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            PlayerData playerData = new PlayerData(e.getPlayer());
            Location loc = e.getBlock().getLocation();
            StaticMine mine = null;
            for (StaticMine m : StaticMine.getAllMines()) {
                if (!loc.getWorld().equals(m.getWorld())) continue;
                BlockVector3 minPoint = m.getMinVector();
                BlockVector3 maxPoint = m.getMaxVector();
                if (minPoint.getBlockX() <= loc.getBlockX() && minPoint.getBlockZ() <= loc.getBlockZ() && maxPoint.getBlockX() >= loc.getBlockX() && maxPoint.getBlockZ() >= loc.getBlockZ()) {
                    mine = m;
                    break;
                }
            }
            if (mine == null) {
                e.setCancelled(true);
                return;
            }
            MineBomb bomb = new MineBomb(loc, finalRadius);
            Map<Material, BigInteger> blocksBroken = bomb.explode(mine);
            StaticMine finalMine = mine;
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                finalMine.removeBlocksBrokenInMine(bomb.blocksChanged);
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                boolean backpackWasFull = playerData.getBackpackIsFull();
                if (!backpackWasFull) {
                    Map<BigDecimal, BigInteger> map = new HashMap<>();
                    for (Map.Entry<Material, BigInteger> entry: blocksBroken.entrySet()) {
                        map.put(Prices.getSellPriceOf(entry.getKey()), entry.getValue().multiply(VIRTUAL_FORTUNE));
                    }
                    playerData.addAllToBackpack(map);
                }
                if (playerData.getBackpackIsFull()) {
                    if (!backpackWasFull) {
                        if (PrisonUtils.Players.canAutoSell(playerData) && playerData.getIsAutoSellEnabled())
                            playerData.sellBackpack(e.getPlayer(), true);
                        e.getPlayer().sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + PrisonUtils.prettyNum(playerData.getBackpackSize()) + "/" + PrisonUtils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                        e.getPlayer().sendMessage(ChatColor.RED + "Your backpack is full!");
                    }
                }
            });
        });
    }
    public static void itemDropped(PlayerDropItemEvent e) {
        //Check if the item was a mine bomb, if so start a timer for it and set the pickup delay to a big number
        if (!e.getPlayer().getLocation().getWorld().equals(Constants.MINES_WORLD) && !e.getPlayer().getLocation().getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) return;
        if (!e.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER)) return;
        e.getItemDrop().setPickupDelay(20 * 10);
        int size = e.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER);
        double radius = 0;
        switch (size) {
            case 1 -> radius = 15;
            case 2 -> radius = 20;
            case 3 -> radius = 27;
            case 4 -> radius = 40;
        }
        double finalRadius = radius;
        Bukkit.getScheduler().runTaskLaterAsynchronously(StaticPrisons.getInstance(), () -> {
            PlayerData playerData = new PlayerData(e.getPlayer());
            Location loc = e.getItemDrop().getLocation();
            StaticMine mine = null;
            for (StaticMine m : StaticMine.getAllMines()) {
                if (!loc.getWorld().equals(m.getWorld())) continue;
                BlockVector3 minPoint = m.getMinVector();
                BlockVector3 maxPoint = m.getMaxVector();
                if (minPoint.getBlockX() <= loc.getBlockX() && minPoint.getBlockZ() <= loc.getBlockZ() && maxPoint.getBlockX() >= loc.getBlockX() && maxPoint.getBlockZ() >= loc.getBlockZ()) {
                    mine = m;
                    break;
                }
            }
            if (mine == null) {
                e.getItemDrop().setPickupDelay(0);
                return;
            }
            MineBomb bomb = new MineBomb(e.getItemDrop().getLocation(), finalRadius);
            Map<Material, BigInteger> blocksBroken = bomb.explode(mine);
            StaticMine finalMine = mine;
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                finalMine.removeBlocksBrokenInMine(bomb.blocksChanged);
                e.getItemDrop().remove();
                boolean backpackWasFull = playerData.getBackpackIsFull();
                if (!backpackWasFull) {
                    Map<BigDecimal, BigInteger> map = new HashMap<>();
                    for (Map.Entry<Material, BigInteger> entry: blocksBroken.entrySet()) {
                        map.put(Prices.getSellPriceOf(entry.getKey()), entry.getValue().multiply(VIRTUAL_FORTUNE));
                    }
                    playerData.addAllToBackpack(map);
                }
                if (playerData.getBackpackIsFull()) {
                    if (!backpackWasFull) {
                        if (PrisonUtils.Players.canAutoSell(playerData) && playerData.getIsAutoSellEnabled())
                            playerData.sellBackpack(e.getPlayer(), true);
                        e.getPlayer().sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + PrisonUtils.prettyNum(playerData.getBackpackSize()) + "/" + PrisonUtils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                        e.getPlayer().sendMessage(ChatColor.RED + "Your backpack is full!");
                    }
                }
            });
        }, 20 * 5);
    }
}
