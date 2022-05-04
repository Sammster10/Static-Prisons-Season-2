package me.staticstudios.prisons.customItems;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class MultiPouchTier1 {
    public static int minAmount;
    public static int maxAmount;
    public static int minTime;
    public static int maxTime;
    public static void open(Player player) {
        MultiPouch pouch = new MultiPouch();
        pouch.multiplierAmount = BigDecimal.valueOf(Utils.randomInt(minAmount, maxAmount)).divide(BigDecimal.TEN);
        pouch.multiplierTime = Utils.randomInt(minTime, maxTime);
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou won {reward} from a Multiplier Pouch Tier 1"));
    }
}
