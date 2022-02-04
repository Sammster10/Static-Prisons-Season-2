package me.staticstudios.prisons.data;

import org.bukkit.Material;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SellPrices {
    private static final Map<Material, BigInteger> BLOCK_SELL_PRICES = new HashMap<>();
    static {
        BLOCK_SELL_PRICES.put(Material.STONE, BigInteger.valueOf(1));
        BLOCK_SELL_PRICES.put(Material.COBBLESTONE, BigInteger.valueOf(2));
        BLOCK_SELL_PRICES.put(Material.COAL_ORE, BigInteger.valueOf(3));
        BLOCK_SELL_PRICES.put(Material.IRON_ORE, BigInteger.valueOf(4));
        BLOCK_SELL_PRICES.put(Material.GOLD_ORE, BigInteger.valueOf(5));
        BLOCK_SELL_PRICES.put(Material.LAPIS_ORE, BigInteger.valueOf(6));
        BLOCK_SELL_PRICES.put(Material.REDSTONE_ORE, BigInteger.valueOf(7));
        BLOCK_SELL_PRICES.put(Material.DIAMOND_ORE, BigInteger.valueOf(8));
        BLOCK_SELL_PRICES.put(Material.EMERALD_ORE, BigInteger.valueOf(9));
        BLOCK_SELL_PRICES.put(Material.COAL_BLOCK, BigInteger.valueOf(10));
        BLOCK_SELL_PRICES.put(Material.IRON_BLOCK, BigInteger.valueOf(11));
        BLOCK_SELL_PRICES.put(Material.GOLD_BLOCK, BigInteger.valueOf(12));
        BLOCK_SELL_PRICES.put(Material.LAPIS_BLOCK, BigInteger.valueOf(13));
        BLOCK_SELL_PRICES.put(Material.REDSTONE_BLOCK, BigInteger.valueOf(14));
        BLOCK_SELL_PRICES.put(Material.DIAMOND_BLOCK, BigInteger.valueOf(15));
        BLOCK_SELL_PRICES.put(Material.EMERALD_BLOCK, BigInteger.valueOf(16));
        BLOCK_SELL_PRICES.put(Material.NETHERRACK, BigInteger.valueOf(17));
        BLOCK_SELL_PRICES.put(Material.NETHER_BRICKS, BigInteger.valueOf(18));
        BLOCK_SELL_PRICES.put(Material.QUARTZ_BLOCK, BigInteger.valueOf(19));
        BLOCK_SELL_PRICES.put(Material.END_STONE, BigInteger.valueOf(20));
        BLOCK_SELL_PRICES.put(Material.OBSIDIAN, BigInteger.valueOf(21));
        BLOCK_SELL_PRICES.put(Material.CRYING_OBSIDIAN, BigInteger.valueOf(22));
        BLOCK_SELL_PRICES.put(Material.PRISMARINE, BigInteger.valueOf(23));
        BLOCK_SELL_PRICES.put(Material.AMETHYST_BLOCK, BigInteger.valueOf(24));
        BLOCK_SELL_PRICES.put(Material.DEEPSLATE_COAL_ORE, BigInteger.valueOf(25));
        BLOCK_SELL_PRICES.put(Material.DEEPSLATE_IRON_ORE, BigInteger.valueOf(26));
        BLOCK_SELL_PRICES.put(Material.DEEPSLATE_GOLD_ORE, BigInteger.valueOf(27));
        BLOCK_SELL_PRICES.put(Material.DEEPSLATE_DIAMOND_ORE, BigInteger.valueOf(28));
        BLOCK_SELL_PRICES.put(Material.DEEPSLATE_EMERALD_ORE, BigInteger.valueOf(29));
    }
    public static BigInteger getSellPriceOf(Material mat) {
        if (!BLOCK_SELL_PRICES.containsKey(mat)) return BigInteger.ZERO;
        return BLOCK_SELL_PRICES.get(mat);
    }
}
