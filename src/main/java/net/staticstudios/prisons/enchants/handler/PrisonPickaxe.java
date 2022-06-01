package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;

public class PrisonPickaxe {
    private static Map<String, PrisonPickaxe> pickaxeUUIDToPrisonPickaxe = new HashMap<>();
    private static List<PrisonPickaxe> updateLoreQueue = new ArrayList<>();

    public static void loadPickaxeData() {
        pickaxeUUIDToPrisonPickaxe = new HashMap<>();
        File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
        dataFolder.mkdirs();
        File pickaxeData = new File(dataFolder, "pickaxeData.yml");
        if (!pickaxeData.exists()) return;
        FileConfiguration ymlData = YamlConfiguration.loadConfiguration(pickaxeData);
        for (String key : ymlData.getKeys(false)) {
            ConfigurationSection section = ymlData.getConfigurationSection(key);
            PrisonPickaxe pickaxe = new PrisonPickaxe(key);
            pickaxe.level = section.getLong("level");
            pickaxe.xp = section.getLong("xp");
            pickaxe.blocksBroken = section.getLong("blocksBroken");
            pickaxe.rawBlocksBroken = section.getLong("rawBlocksBroken");
            for (String _key : section.getKeys(false)) {
                switch (_key) { case "level", "xp", "blocksBroken", "rawBlocksBroken", "topLore", "bottomLore" -> { continue; }}
                pickaxe.setEnchantsLevel(_key, section.getInt(_key));
            }
        }

    }

    public static void savePickaxeData() {
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), PrisonPickaxe::savePickaxeDataNow);
    }

    public static void savePickaxeDataNow() {
        File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
        File oldData = new File(dataFolder, "old");
        oldData.mkdirs();
        File pickaxeData = new File(dataFolder, "pickaxeData.yml");
        if (pickaxeData.exists()) pickaxeData.renameTo(new File(oldData, Instant.now().toEpochMilli() + ".yml"));
        FileConfiguration ymlData = new YamlConfiguration();
        for (String key : pickaxeUUIDToPrisonPickaxe.keySet()) {
            ConfigurationSection section = ymlData.createSection(key);
            PrisonPickaxe pickaxe = pickaxeUUIDToPrisonPickaxe.get(key);
            for (BaseEnchant enchant : pickaxe.getEnchants()) section.set(enchant.ENCHANT_ID, pickaxe.getEnchantLevel(enchant.ENCHANT_ID));
            section.set("level", pickaxe.level);
            section.set("xp", pickaxe.xp);
            section.set("blocksBroken", pickaxe.blocksBroken);
            section.set("rawBlocksBroken", pickaxe.rawBlocksBroken);
            section.set("topLore", pickaxe.topLore);
            section.set("bottomLore", pickaxe.bottomLore);
        }
        try {
            ymlData.save(new File(dataFolder, "pickaxeData.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getLogger().log(Level.INFO, "Saved all pickaxe data data/pickaxeData.yml");
    }

    public static PrisonPickaxe fromItem(ItemStack item) {
        PrisonPickaxe pickaxe = PrisonPickaxe.fromID(item.getItemMeta().getPersistentDataContainer().get(Constants.UUID_NAMESPACEKEY, PersistentDataType.STRING));
        if (pickaxe != null) pickaxe.item = item;
        return pickaxe;
    }

    public static PrisonPickaxe fromID(String pickaxeUUID) {
        return pickaxeUUIDToPrisonPickaxe.get(pickaxeUUID);
    }

    public ItemStack item = null;
    private Map<String, Integer> enchantLevels = new HashMap<>();
    private List<String> topLore = new ArrayList<>();
    private List<String> bottomLore = new ArrayList<>();
    private long level = 0;
    private long xp = 0;
    private long blocksBroken = 0;
    private long rawBlocksBroken = 0;

    public PrisonPickaxe(String uuid) {
        pickaxeUUIDToPrisonPickaxe.put(uuid, this);
    }

    public PrisonPickaxe(ItemStack item) {
        String uuid = UUID.randomUUID().toString();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Constants.UUID_NAMESPACEKEY, PersistentDataType.STRING, uuid);
        item.setItemMeta(meta);
        pickaxeUUIDToPrisonPickaxe.put(uuid, this);
    }

    public List<BaseEnchant> getEnchants() {
        List<BaseEnchant> enchants = new ArrayList<>();
        for(String enchantID : enchantLevels.keySet()) if (getEnchantLevel(enchantID) > 0) enchants.add(PrisonEnchants.enchantIDToEnchant.get(enchantID));
        return enchants;
    }

    public int getEnchantLevel(BaseEnchant enchant) {
        return (getEnchantLevel(enchant.ENCHANT_ID));
    }
    public int getEnchantLevel(String enchantID) {
        if (enchantLevels.containsKey(enchantID)) return enchantLevels.get(enchantID);
        return 0;
    }
    //todo add a way to get and set if an enchant is enabled or not

    public void setEnchantsLevel(BaseEnchant enchant, int level) {
        updateLoreQueue.add(this);
        enchantLevels.put(enchant.ENCHANT_ID, level);
    }
    public void setEnchantsLevel(String enchant, int level) {
        updateLoreQueue.add(this);
        enchantLevels.put(enchant, level);
    }

    public void addEnchantLevel(BaseEnchant enchant, int level) {
        setEnchantsLevel(enchant, getEnchantLevel(enchant) + level);
    }
    public void addEnchantLevel(String enchant, int level) {
        setEnchantsLevel(enchant, getEnchantLevel(enchant) + level);
    }

    public void removeEnchantLevel(BaseEnchant enchant, int level) {
        setEnchantsLevel(enchant, Math.max(0, getEnchantLevel(enchant) - level));
    }

    public long getLevel() {
        return level;
    }
    public long getXp() {
        return xp;
    }
    public long getBlocksBroken() {
        return blocksBroken;
    }
    public long getRawBlocksBroken() {
        return rawBlocksBroken;
    }
    public void setLevel(long level) {
        if (this.level != level) updateLoreQueue.add(this);
        this.level = level;
    }
    public void setXp(long xp) {
        if (this.xp != xp) updateLoreQueue.add(this);
        this.xp = xp;
    }
    public void setBlocksBroken(long blocksBroken) {
        if (this.blocksBroken != blocksBroken) updateLoreQueue.add(this);
        this.blocksBroken = blocksBroken;
    }
    public void setRawBlocksBroken(long rawBlocksBroken) {
        if (this.rawBlocksBroken != rawBlocksBroken) updateLoreQueue.add(this);
        this.rawBlocksBroken = rawBlocksBroken;
    }
    public void addXp(long xp) {
        //todo recalc and apply level
        setXp(this.xp + xp);
    }
    public void addBlocksBroken(long blocksBroken) {
        setBlocksBroken(this.blocksBroken + blocksBroken);
    }
    public void addRawBlocksBroken(long rawBlocksBroken) {
        setRawBlocksBroken(this.rawBlocksBroken + rawBlocksBroken);
    }

    public static void dumpLoreToAllPickaxes() {
        for (PrisonPickaxe pickaxe : updateLoreQueue) {
            if (pickaxe.item == null) continue;
            ItemMeta meta = pickaxe.item.getItemMeta();
            meta.setLore(pickaxe.buildLore());
            pickaxe.item.setItemMeta(meta);
        }
        updateLoreQueue.clear();
    }

    public static void updateLore(ItemStack item) {
        PrisonPickaxe pickaxe = fromItem(item);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(pickaxe.buildLore());
        item.setItemMeta(meta);
        updateLoreQueue.remove(pickaxe);
    }

    public boolean tryToUpdateLore() {
        if (item == null) return false;
        updateLore(item);
        return true;
    }

    final String LORE_DIVIDER = ChatColor.translateAlternateColorCodes('&', "&7---------------");
    public List<String> buildLore() {
        List<String> lore = new ArrayList<>(topLore);
        lore.addAll(buildStatLore());
        lore.add(LORE_DIVIDER);
        lore.addAll(buildEnchantLore());
        if (!bottomLore.isEmpty()) {
            lore.add(LORE_DIVIDER);
            lore.addAll(bottomLore);
        }
        return lore;
    }

    private List<String> buildStatLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Level: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(level));
        lore.add(ChatColor.GREEN + "Experience: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(xp));
        lore.add(ChatColor.GREEN + "Blocks Mined: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(rawBlocksBroken));
        lore.add(ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(blocksBroken));
        return lore;
    }

    private List<String> buildEnchantLore() {
        List<String> lore = new ArrayList<>();
        for (String enchantID : enchantLevels.keySet()) lore.add(ChatColor.AQUA + PrisonEnchants.enchantIDToEnchant.get(enchantID).UNFORMATTED_DISPLAY_NAME + ": " + PrisonUtils.addCommasToNumber(getEnchantLevel(enchantID)));
        return lore;
    }
}
