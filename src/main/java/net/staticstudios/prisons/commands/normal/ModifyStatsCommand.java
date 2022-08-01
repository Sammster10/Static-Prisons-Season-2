package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifyStatsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            if (!(args.length == 3 && "reset".equals(args[2]))) {
                sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats <stat> <who> <modify> <value>"));
                return false;
            }

            args = Arrays.copyOf(args, 4);
            args[3] = "1";
        }
        if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[1].toLowerCase()) && !args[1].equalsIgnoreCase("self")) {
            sender.sendMessage(ChatColor.RED + "Could not find the player specified!");
            return false;
        }
        PlayerData playerData;
        if (args[1].equalsIgnoreCase("self")) {
            if (sender instanceof Player) {
                playerData = new PlayerData(((Player) sender).getUniqueId());
            } else {
                sender.sendMessage(ChatColor.RED + "You cannot use self from console!");
                return false;
            }
        } else playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[1].toLowerCase()));
        switch (args[0].toLowerCase()) {
            case "minerank" -> {
                int amount;
                try {
                    amount = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats minerank <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addMineRank(amount);
                    case "remove" -> playerData.removeMineRank(amount);
                    case "set" -> playerData.setMineRank(amount);
                    case "reset" -> playerData.setMineRank(0);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats minerank <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "prestige" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats prestige <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addPrestige(amount);
                    case "remove" -> playerData.removePrestige(amount);
                    case "set" -> playerData.setPrestige(amount);
                    case "reset" -> playerData.setPrestige(BigInteger.ZERO);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats prestige <who> <add|remove|set|reset> <amount>"));
                }
            }
//            case "backpack" -> {
//                long amount;
//                try {
//                    amount = Long.parseLong(args[3]);
//                } catch (NumberFormatException e) {
//                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats backpack <who> <add|remove|set|reset> <amount>"));
//                    return false;
//                }
//                switch (args[2].toLowerCase()) {
//                    case "add" -> playerData.setBackpackSize(playerData.getBackpackSize() + amount);
//                    case "remove" -> playerData.setBackpackSize(playerData.getBackpackSize() - amount);
//                    case "set" -> playerData.setBackpackSize(amount);
//                    case "reset" -> playerData.setBackpackSize(0);
//                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats backpack <who> <add|remove|set|reset> <amount>"));
//                }
//            }
            case "blocksmined" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats blocksmined <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addBlocksMined(amount);
                    case "remove" -> playerData.removeBlocksMined(amount);
                    case "set" -> playerData.setBlocksMined(amount);
                    case "reset" -> playerData.setBlocksMined(BigInteger.ZERO);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats blocksmined <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "rawblocks", "rawblocksmined" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats rawblocks <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addRawBlocksMined(amount);
                    case "remove" -> playerData.removeRawBlocksMined(amount);
                    case "set" -> playerData.setRawBlocksMined(amount);
                    case "reset" -> playerData.setRawBlocksMined(BigInteger.ZERO);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats rawblocks <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "pmine", "privatemine" -> {
                switch (args[2].toLowerCase()) {
                    case "set" -> {
                        int amount;
                        try {
                            amount = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats pmine <who> <set> <amount>"));
                            return false;
                        }
                        playerData.setPrivateMineSquareSize(amount);
                        playerData.setHasPrivateMine(true);
                        playerData.setPrivateMineMat(Material.STONE);
                    }
                    case "reset" -> playerData.setHasPrivateMine(false);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats pmine <who> <set|reset>"));
                }
            }
            case "votes", "vote" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats votes <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addVotes(amount);
                    case "remove" -> playerData.removeVotes(amount);
                    case "set" -> playerData.setVotes(amount);
                    case "reset" -> playerData.setVotes(BigInteger.ZERO);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats votes <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "timeplayed" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats timeplayed <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addTimePlayed(amount);
                    case "remove" -> playerData.removeTimePlayed(amount);
                    case "set" -> playerData.setTimePlayed(amount);
                    case "reset" -> playerData.setTimePlayed(BigInteger.ZERO);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats timeplayed <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "money" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats money <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addMoney(amount);
                    case "remove" -> playerData.removeMoney(amount);
                    case "set" -> playerData.setMoney(amount);
                    case "reset" -> playerData.setMoney(BigInteger.ZERO);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats money <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "xp" -> {
                long amount;
                try {
                    amount = Long.parseLong(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats xp <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addPlayerXP(amount);
                    case "remove" -> playerData.removePlayerXP(amount);
                    case "set" -> playerData.setPlayerXP(amount);
                    case "reset" -> playerData.setPlayerXP(0);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats xp <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "level" -> {
                int amount;
                try {
                    amount = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats xp <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addPlayerLevel(amount);
                    case "remove" -> playerData.removePlayerLevel(amount);
                    case "set" -> playerData.setPlayerLevel(amount);
                    case "reset" -> playerData.setPlayerLevel(0);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats level <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "tokens" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats tokens <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addTokens(amount);
                    case "remove" -> playerData.removeTokens(amount);
                    case "set" -> playerData.setTokens(amount);
                    case "reset" -> playerData.setTokens(BigInteger.ZERO);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats tokens <who> <add|remove|set|reset> <amount>"));
                }
            }
            //prestige tokens
            case "prestigetokens" -> {
                long amount;
                try {
                    amount = Long.parseLong(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats prestigetokens <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addPrestigeTokens(amount);
                    case "remove" -> playerData.removePrestigeTokens(amount);
                    case "set" -> playerData.setPrestigeTokens(amount);
                    case "reset" -> playerData.setPrestigeTokens(0);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats prestigetokens <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "shards" -> {
                BigInteger amount;

                try {
                    amount = new BigInteger(args[3]);

                } catch (NumberFormatException e) {
                    sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats shards <who> <add|remove|set|reset> <amount>"));
                    return false;
                }

                switch (args[2]) {
                    case "add" -> playerData.addShards(amount);
                    case "remove" -> playerData.removeShards(amount);
                    case "set" -> playerData.setShards(amount);
                    case "reset" -> playerData.setShards(BigInteger.ZERO);
                    default -> sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/modstats tokens <who> <add|remove|set|reset> <amount>"));
                }
            }
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("pmine");
            list.add("minerank");
            list.add("prestige");
            list.add("backpack");
            list.add("blocksmined");
            list.add("rawblocks");
            list.add("votes");
            list.add("timeplayed");
            list.add("money");
            list.add("xp");
            list.add("level");
            list.add("tokens");
            list.add("prestigetokens");
            list.add("shards");
        } else if (args.length == 2) {
            list.addAll(StaticMineUtils.filterStringList(ServerData.PLAYERS.getAllNames(), args[1]));
            list.add("self");
        } else if (args.length == 3) {
            list.add("add");
            list.add("remove");
            list.add("set");
            list.add("reset");
        } else if (args.length == 4) list.add("<amount>");
        return StaticMineUtils.filterStringList(list, args[args.length - 1]);
    }
}
