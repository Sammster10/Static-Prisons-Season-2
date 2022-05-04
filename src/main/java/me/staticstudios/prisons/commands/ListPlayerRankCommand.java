package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.newData.dataHandling.serverData.ServerData;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListPlayerRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 0) {
            sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/listplayerrank <rank>"));
            return false;
        }
        List<String> names = new ArrayList<>();
        for (String uuid : ServerData.PLAYERS.getAllUUIDsAsStrings()) if (new PlayerData(uuid).getPlayerRank().equals(args[0])) names.add(ServerData.PLAYERS.getName(UUID.fromString(uuid)));
        sender.sendMessage("The following players have this rank: " + names);
        return false;
    }
}
