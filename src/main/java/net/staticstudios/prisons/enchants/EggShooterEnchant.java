package net.staticstudios.prisons.enchants;

import com.sk89q.worldedit.math.BlockVector3;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockBroken.BlockBreakListener;
import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.mineBombs.MineBomb;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Map;

public class EggShooterEnchant extends BaseEnchant {
    public EggShooterEnchant() {
        super("eggShooter", "&e&lEgg Shooter", 1000, BigInteger.valueOf(500), "&7Shoot explosive eggs while right clicking");
    }
    public void whileRightClicking(PlayerInteractEvent e, PrisonPickaxe pickaxe) {
        Player player = e.getPlayer();
        if (e.getPlayer().getWorld() != Constants.MINES_WORLD) return;
        Egg egg = player.getWorld().spawn(player.getEyeLocation(), Egg.class);
        egg.setShooter(new EggShooterPickaxe(player, pickaxe));
        egg.setVelocity(player.getLocation().getDirection().multiply(1));
    }

    public static void eggHit(ProjectileHitEvent e, EggShooterPickaxe eggShooterPickaxe) {
        Player player = eggShooterPickaxe.player;
        if (e.getHitEntity() != null) if (e.getHitEntity().equals(player)) {
            e.setCancelled(true);
            return;
        }
        if (e.getHitBlock() == null) return;
        PrisonPickaxe pickaxe = eggShooterPickaxe.pickaxe;
        StaticMine mine = null;
        Location loc = e.getHitBlock().getLocation();
        for (StaticMine m : StaticMine.getAllMines()) {
            BlockVector3 minPoint = m.getMinVector();
            BlockVector3 maxPoint = m.getMaxVector();
            if (minPoint.getBlockX() <= loc.getBlockX() && minPoint.getBlockZ() <= loc.getBlockZ() && maxPoint.getBlockX() >= loc.getBlockX() && maxPoint.getBlockZ() >= loc.getBlockZ()) {
                mine = m;
                break;
            }
        }
        if (mine == null) return;
        MineBomb bomb = new MineBomb(e.getHitBlock().getLocation(), (double) pickaxe.getEnchantLevel(PrisonEnchants.EGG_SHOOTER) / (PrisonEnchants.EGG_SHOOTER.MAX_LEVEL / 5) + 1);
        StaticMine finalMine = mine;
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            Map<Material, BigInteger> blocksBroken = bomb.explode(finalMine, 10);
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                finalMine.removeBlocksBrokenInMine(bomb.blocksChanged);
                int fortune = pickaxe.getEnchantLevel(PrisonEnchants.FORTUNE);
                if (PrisonUtils.randomInt(0, PrisonEnchants.DOUBLE_FORTUNE.MAX_LEVEL) < pickaxe.getEnchantLevel(PrisonEnchants.DOUBLE_FORTUNE)) fortune *= 2;
                PlayerData playerData = new PlayerData(player);
                boolean backpackWasFull = playerData.getBackpackIsFull();
                for (Material key : blocksBroken.keySet()) playerData.addBackpackAmountOf(key, blocksBroken.get(key).multiply(BigInteger.valueOf(fortune)));
                BlockBreakListener.backpackFullCheck(backpackWasFull, player, playerData);
            });
        });
    }

    public static class EggShooterPickaxe implements ProjectileSource {

        public Player player;
        public PrisonPickaxe pickaxe;

        public EggShooterPickaxe(Player player, PrisonPickaxe pickaxe) {
            this.player = player;
            this.pickaxe = pickaxe;
        }

        @Override
        public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile) {
            return null;
        }

        @Override
        public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity) {
            return null;
        }
    }
}
