package me.staticstudios.prisons.islands;


import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.papermc.lib.PaperLib;
import me.staticstudios.prisons.data.serverData.IslandData;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.islands.invites.IslandInvites;
import me.staticstudios.prisons.islands.invites.SkyblockIslandInviteManager;
import me.staticstudios.prisons.misc.Warps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class SkyBlockIsland extends IslandData {
    public static final int MAX_PLAYERS_PER_ISLAND = 5;
    String uuid; //Use random uuids, not player ids as ownership can be transferred
    public SkyBlockIsland(String uuid) {
        super(uuid);
        this.uuid = uuid;
    }
    public void teleportPlayerToMemberWarp(Player player) {
        PaperLib.teleportAsync(player, new Location(Bukkit.getWorld("islands"), getIslandMemberWarpX(), getIslandMemberWarpY(), getIslandMemberWarpZ()));
        IslandManager.playersInIslands.put(player.getUniqueId(), UUID.fromString(getUUID()));
    }
    public void teleportPlayerToVisitorWarp(Player player) {
        PaperLib.teleportAsync(player, new Location(Bukkit.getWorld("islands"), getIslandVisitorWarpX(), getIslandVisitorWarpY(), getIslandVisitorWarpZ()));
        IslandManager.playersInIslands.put(player.getUniqueId(), UUID.fromString(getUUID()));
    }
    public void playerJoined(Player player) {
        PlayerData playerData = new PlayerData(player);
        playerData.setIfPlayerHasIsland(true);
        playerData.setPlayerIslandUUID(getUUID());
        SkyBlockIsland island = playerData.getPlayerIsland();
        IslandInvites invites = SkyblockIslandInviteManager.getIslandInvites(player.getUniqueId().toString());
        for (String uuid : island.getIslandPlayerUUIDS()) {
            if (Bukkit.getPlayer(UUID.fromString(uuid)) == null) continue;
            Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.GREEN + " has just joined your cell! They were invited by: " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(invites.invites.get(getUUID()).inviterUUID));
        }
        island.addIslandPlayerUUID(player.getUniqueId().toString());
        island.addIslandMemberUUID(player.getUniqueId().toString());
        island.teleportPlayerToMemberWarp(player);
        SkyblockIslandInviteManager.getIslandInvites(player.getUniqueId().toString()).invites.remove(getUUID());
        ProtectedRegion region = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("islands"))).getRegion(getUUID() + "-island");
        DefaultDomain members = region.getMembers();
        members.addPlayer(player.getUniqueId());
        region.setMembers(members);

    }
    public void playerLeft(Player player) {
        playerRemoved(player.getUniqueId());
        for (String _uuid : getIslandPlayerUUIDS()) {
            if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + " has left your cell.");
        }
    }
    public void playerKicked(Player kicker, UUID uuid) {
       playerRemoved(uuid);
        for (String _uuid : getIslandPlayerUUIDS()) {
            if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid.toString()) + ChatColor.GREEN + " has been kicked from your cell by: " + ChatColor.LIGHT_PURPLE + new ServerData().getPlayerNameFromUUID(kicker.getUniqueId().toString()));
        }

    }
    public void playerBanned(Player banner, UUID uuid) {
        playerRemoved(uuid);
        addBannedPlayerUUID(uuid.toString());
        for (String _uuid : getIslandPlayerUUIDS()) {
            if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid.toString()) + ChatColor.GREEN + " has been banned from your island by: " + ChatColor.LIGHT_PURPLE + new ServerData().getPlayerNameFromUUID(banner.getUniqueId().toString()));
        }
    }
    public void delete() {
        for (String playerUUID : new ArrayList<>(getIslandPlayerUUIDS())) {
            playerRemoved(UUID.fromString(playerUUID));
            if (Bukkit.getPlayer(UUID.fromString(playerUUID)) != null) {
                Bukkit.getPlayer(UUID.fromString(playerUUID)).sendMessage(ChatColor.RED + "Your cell has been deleted by the cell owner.");
            }
        }
        //All players have been kicked from the island, delete the island from the DB
        new ServerData().removeSkyblockIslandNameToUUID(getIslandName());
        new ServerData().removeSkyblockIslandUUIDToName(getUUID());
        new ServerData().removeSkyblockIslandFromUUID(getUUID());
    }
    void playerRemoved(UUID uuid) {
        PlayerData _playerData = new PlayerData(uuid);
        _playerData.setIfPlayerHasIsland(false);
        _playerData.setPlayerIslandUUID("");
        if (getIslandManagerUUID().equals(uuid.toString())) {
            setIslandManagerUUID("");
        }
        if (getIslandAdminUUIDS().contains(uuid.toString())) {
            removeIslandAdminUUID(uuid.toString());
        }
        if (getIslandMemberUUIDS().contains(uuid.toString())) {
            removeIslandMemberUUID(uuid.toString());
        }
        getIslandPlayerUUIDS().remove(uuid.toString());
        ProtectedRegion region = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("islands"))).getRegion(getUUID() + "-island");
        DefaultDomain members = region.getMembers();
        members.removePlayer(uuid);
        region.setMembers(members);
        if (Bukkit.getPlayer(uuid) == null) return;
        if (IslandManager.playersInIslands.containsKey(uuid)) {
            if (IslandManager.playersInIslands.get(uuid).equals(UUID.fromString(getUUID()))) {
                Warps.warpToSpawn(Bukkit.getPlayer(uuid));
            }
        }
    }
    public static SkyBlockIsland createNewIsland(Player islandOwner) {
        SkyBlockIsland island = createNewIsland(islandOwner.getUniqueId().toString());
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(Bukkit.getWorld("islands")))) {
            Operation operation = new ClipboardHolder(IslandManager.islandTemplate)
                    .createPaste(editSession)
                    .to(BlockVector3.at(island.getCenterPosOfIslandOnGrid()[0], 100, island.getCenterPosOfIslandOnGrid()[1]))
                    .build();
            Operations.complete(operation);
        }
        island.teleportPlayerToMemberWarp(islandOwner);
        return island;
    }
    public static SkyBlockIsland createNewIsland(String islandOwnerUUID) {
        String uuid = UUID.randomUUID().toString();
        SkyBlockIsland island = new SkyBlockIsland(uuid);
        island.setGridNumber(IslandManager.createNewIslandOnGrid());
        int[] posOffset = island.getCenterPosOfIslandOnGrid();
        island.setIslandMemberWarpX(IslandData.DEFAULT_WARP_X + posOffset[0]);
        island.setIslandMemberWarpY(IslandData.DEFAULT_WARP_Y);
        island.setIslandMemberWarpZ(IslandData.DEFAULT_WARP_Z + posOffset[1]);
        island.setIslandVisitorWarpX(IslandData.DEFAULT_WARP_X + posOffset[0]);
        island.setIslandVisitorWarpY(IslandData.DEFAULT_WARP_Y);
        island.setIslandVisitorWarpZ(IslandData.DEFAULT_WARP_Z + posOffset[1]);
        island.setAllowInvites(true);
        island.setAllowVisitors(true);
        island.setIslandOwnerUUID(islandOwnerUUID);
        island.addIslandPlayerUUID(islandOwnerUUID);
        String originalName = new ServerData().getPlayerNameFromUUID(islandOwnerUUID) + "'s_Island";
        String name;
        int i = 1;
        while(true) {
            if (new ServerData().getSkyblockIslandNamesToUUIDsMap().containsKey(originalName)) {
                name = originalName + "(" + i + ")";
                i++;
                if (!new ServerData().getSkyblockIslandNamesToUUIDsMap().containsKey(name)) {
                    break;
                }
            } else {
                name = originalName;
                break;
            }
        }
        island.setIslandName(name);
        new ServerData().putSkyblockIslandNameToUUID(name, uuid);
        new ServerData().putSkyblockIslandUUIDToName(uuid, name);
        PlayerData playerData = new PlayerData(islandOwnerUUID);
        playerData.setIfPlayerHasIsland(true);
        playerData.setPlayerIslandUUID(island.getUUID());
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(uuid + "-island", BlockVector3.at(posOffset[0] - 250, 0, posOffset[1] - 250), BlockVector3.at(posOffset[0] + 250, 255, posOffset[1] + 250));
        DefaultDomain members = region.getMembers();
        members.addPlayer(UUID.fromString(islandOwnerUUID));
        region.setMembers(members);
        region.setPriority(1);
        WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("islands"))).addRegion(region);
        return island;
    }
}
