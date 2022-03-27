package me.staticstudios.prisons.gameplay.customItems;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class TokenPouchTier1 {
    public static void open(Player player) {
        TokenPouch pouch = new TokenPouch();
        pouch.minReward = new BigInteger("250");
        pouch.maxReward = new BigInteger("2000");
        pouch.reward = Utils.randomBigInt(pouch.minReward, pouch.maxReward);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou won {reward} tokens from a Token Pouch Tier 1"));
    }
}