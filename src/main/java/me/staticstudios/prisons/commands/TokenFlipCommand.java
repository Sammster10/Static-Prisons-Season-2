package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.gambling.TokenFlip;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.utils.CommandUtils;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class TokenFlipCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            GUI.getGUIPage("tf").open(player);
            return false;
        } else if (args.length == 1) {
            player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/tokenflip <amount> <heads|tails>"));
            return false;
        }
        BigInteger amount;
        boolean isHeads;
        try {
            amount = new BigInteger(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/tokenflip <amount> <heads|tails>"));
            return false;
        }
        if (args[1].equalsIgnoreCase("heads")) {
            isHeads = true;
        } else if (args[1].equalsIgnoreCase("tails")) {
            isHeads = false;
        } else {
            player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/tokenflip <amount> <heads|tails>"));
            return false;
        }
        if (TokenFlip.checkIfThereAreTooManyActiveFlips()) {
            player.sendMessage(ChatColor.RED + "There are too many active TokenFlips for you to create a new one.");
            return false;
        }
        if (amount.compareTo(new PlayerData(player).getTokens()) > 0) {
            player.sendMessage(ChatColor.RED + "You do not have enough tokens to create this bet!");
            return false;
        }
        if (amount.compareTo(BigInteger.ZERO) < 1) {
            player.sendMessage(ChatColor.RED + "You cannot create a TokenFlip for less than 1 token!");
            return false;
        }
        TokenFlip.createTokenFlip(player, amount, isHeads);
        player.sendMessage(ChatColor.AQUA + "You have successfully created a TokenFlip for " + ChatColor.GREEN + Utils.prettyNum(amount) + " Tokens");

        return true;
    }
}
