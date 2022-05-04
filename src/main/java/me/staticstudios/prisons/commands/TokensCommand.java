package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TokensCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        player.sendMessage(ChatColor.AQUA + "Current token balance: " + Utils.addCommasToNumber(new PlayerData(player).getTokens()));
        return false;
    }
}
