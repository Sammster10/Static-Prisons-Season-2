package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetNewPickaxeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Utils.addItemToPlayersInventoryAndDropExtra(player, Utils.createNewPickaxe());
        return false;
    }
}
