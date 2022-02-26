package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.misc.Warps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CratesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Warps.warpToCrates(player);
        return false;
    }
}
