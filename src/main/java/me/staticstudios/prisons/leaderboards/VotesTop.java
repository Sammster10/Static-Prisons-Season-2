package me.staticstudios.prisons.leaderboards;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.newData.dataHandling.serverData.ServerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VotesTop {
    static final String npc1Name = "leaderboardsVotes1";
    static final String npc2Name = "leaderboardsVotes2";
    static final String npc3Name = "leaderboardsVotes3";
    public static List<UUID> top100UUIDs = new ArrayList<>();
    public static void calculateLeaderBoard() {
        List<UUID> topUUIDs = new ArrayList<>();
        List<BigInteger> topValues = new ArrayList<>();
        for (String uuid : ServerData.PLAYERS.getAllUUIDsAsStrings()) {
            boolean ranked = false;
            PlayerData playerData = new PlayerData(uuid);
            if (playerData.getIsExemptFromLeaderboards()) continue;
            for (int i = 0; i < topUUIDs.size(); i++) {
                if (topValues.get(i).compareTo(playerData.getVotes()) < 0) {
                    ranked = true;
                    topValues.add(i, playerData.getVotes());
                    topUUIDs.add(i, playerData.getUUID());
                    break;
                }
            }
            if (!ranked && topUUIDs.size() < 100) {
                topValues.add(playerData.getVotes());
                topUUIDs.add(playerData.getUUID());
            }
        }
        top100UUIDs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (topUUIDs.size() >= i + 1) top100UUIDs.add(topUUIDs.get(i));
        }
        updateNPC();
    }
    static void updateNPC() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc1Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getUUID(top100UUIDs.get(0).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getUUID(top100UUIDs.get(0).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(0)).getVotes()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getUUID(top100UUIDs.get(0).toString()) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc2Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getUUID(top100UUIDs.get(1).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getUUID(top100UUIDs.get(1).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(1)).getVotes()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getUUID(top100UUIDs.get(1).toString()) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc3Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getUUID(top100UUIDs.get(2).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getUUID(top100UUIDs.get(2).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(2)).getVotes()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getUUID(top100UUIDs.get(2).toString()) + " -r -l -p");
    }
}
