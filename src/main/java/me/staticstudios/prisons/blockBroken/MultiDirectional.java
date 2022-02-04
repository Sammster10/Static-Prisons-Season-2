package me.staticstudios.prisons.blockBroken;

import me.staticstudios.prisons.mines.BaseMine;
import me.staticstudios.prisons.mines.MineManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiDirectional {

    private final BaseMine mine;
    private final Location loc;
    public MultiDirectional(BaseMine mine, Location loc) {
        this.loc = loc;
        this.mine = mine;
    }

    /**
     * Destroys a section of the mine in the world specified
     *
     * @return all the blocks that were removed
     */
    public Map<Material, BigInteger> destroySection(int fromY, int minY) {
        Map<Material, BigInteger> blocksBroken = new HashMap<>();
        int totalBlocksBroken = 0;
        int blockZ = loc.getBlockZ();
        int blockX = loc.getBlockX();
        for(int y = minY; y <= fromY; y++) {
            for (int x = (int) mine.minLocation.getX(); x < mine.maxLocation.getX() + 1; x++) {
                Material mat = new Location(Bukkit.getWorld("mines"), x, y, blockZ).getBlock().getType();
                if (!mat.equals(Material.AIR)) {
                    totalBlocksBroken++;
                    if (!blocksBroken.containsKey(mat)) {
                        blocksBroken.put(mat, BigInteger.ONE);
                    } else blocksBroken.put(mat, blocksBroken.get(mat).add(BigInteger.ONE));
                }
            }
            for (int z = (int) mine.minLocation.getX(); z < mine.maxLocation.getX() + 1; z++) {
                if (z == blockZ) continue; //We already got the 0 pos with the x loop
                Material mat = new Location(Bukkit.getWorld("mines"), blockX, y, z).getBlock().getType();
                if (!mat.equals(Material.AIR)) {
                    totalBlocksBroken++;
                    if (!blocksBroken.containsKey(mat)) {
                        blocksBroken.put(mat, BigInteger.ONE);
                    } else blocksBroken.put(mat, blocksBroken.get(mat).add(BigInteger.ONE));
                }
            }
        }
        if (!mine.brokeBlocksInMine(totalBlocksBroken)) BlockChange.addMultiDirectionalBlockChange(Bukkit.getWorld("mines"), (int) mine.minLocation.getX(), (int) loc.getY(), (int) mine.minLocation.getZ(), (int) mine.maxLocation.getX(), minY, (int) mine.maxLocation.getZ(), (int) loc.getX(), (int) loc.getZ());
        return blocksBroken;
    }
}
