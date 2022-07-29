package net.staticstudios.prisons.lootboxes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenLootBox extends LootBox {

    record TokenLootBoxOutline(int tier, long requires, long minReward, long maxReward) {}

    static Map<Integer, TokenLootBoxOutline> TIERS = new HashMap<>();

    public static void loadTiers(ConfigurationSection section) {
        TIERS.clear();
        for (String key : section.getKeys(false)) {
            int tier = Integer.parseInt(key);
            long requires = section.getLong(key + ".requires");
            long minReward = section.getLong(key + ".reward.min");
            long maxReward = section.getLong(key + ".reward.max");
            TIERS.put(tier, new TokenLootBoxOutline(tier, requires, minReward, maxReward));
        }
    }

    public static TokenLootBoxOutline getTier(int tier) {
        return TIERS.get(tier);
    }



    public TokenLootBox(int tier) {
        super("&6&lToken Loot Box", LootBoxType.TOKEN, true);
        this.blocksRequired = getTier(tier).requires;
        this.tier = tier;
        updateItem();
    }

    /**
     * Constructor that gets called when loading a loot box from a configuration section
     */
    public TokenLootBox(long blocksRequired, int tier) {
        super("&6&lToken Loot Box", LootBoxType.TOKEN, true);
        this.blocksRequired = blocksRequired;
        this.tier = tier;
        updateItem();
    }

    private long blocksMined;
    private long blocksRequired;
    private int tier;

    @Override
    void onBlockBreak(BlockBreak blockBreak) {
        if (blocksMined >= blocksRequired) return;
        blockBreak.addAfterProcess(bb -> {
            blocksMined += bb.getStats().getRawBlockBroken();
            if (blocksMined >= blocksRequired) {
                Player player = bb.getPlayer();
                player.sendMessage(Prefix.LOOT_BOX.append(Component.text("One of your loot boxes is ready to be claimed!")));
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 2, 2, 2);
            }
            updateItem();
        });
    }

    @Override
    Component getDisplayName() {
        double percentage = (double) blocksMined / blocksRequired * 100;
        Component percent;
        if (percentage >= 100d) {
            percent = Component.text("100.00%").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD);
        } else percent = Component.text(BigDecimal.valueOf(percentage).setScale(2, RoundingMode.FLOOR) + "%").color(ComponentUtil.LIGHT_GRAY);
        return getName().append(Component.text(" [" + PrisonUtils.prettyNum(blocksMined) + "/" + PrisonUtils.prettyNum(blocksRequired) + " Blocks Mined | ").color(ComponentUtil.LIGHT_GRAY)).append(percent).append(Component.text("]").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false);
    }

    static final Component LORE_PREFIX = Component.empty().append(Component.text("| ").color(ComponentUtil.DARK_GRAY).decorate(TextDecoration.BOLD));
    @Override
    List<Component> getLore() {
        return List.of(
                Component.empty(),
                LORE_PREFIX.append(Component.text("Open this loot box to receive a random").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("amount of tokens! This loot box's challenge").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("requires you to mine " + PrisonUtils.addCommasToNumber(blocksRequired) + " blocks").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("You are currently ").color(ComponentUtil.LIGHT_GRAY)).append(Component.text(BigDecimal.valueOf((double) blocksMined / blocksRequired * 100).setScale(2, RoundingMode.FLOOR) + "%").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD)).append(Component.text(" done with this!").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("Tier: ").color(ComponentUtil.GOLD)).append(Component.text(tier + "").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("Reward: ").color(ComponentUtil.GOLD)).append(Component.text(PrisonUtils.addCommasToNumber(getTier(tier).minReward) + " - " + PrisonUtils.addCommasToNumber(getTier(tier).maxReward) + " Tokens").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false)
        );
    }

    @Override
    boolean checkCondition() {
        return blocksMined >= blocksRequired;
    }

    @Override
    void onClaim(Player player) {
        BigInteger tokensToAdd = BigInteger.valueOf(PrisonUtils.randomLong(getTier(tier).minReward, getTier(tier).maxReward));
        new PlayerData(player).addTokens(tokensToAdd);
        player.sendMessage(Prefix.LOOT_BOX.append(Component.text("You've been given " + PrisonUtils.addCommasToNumber(tokensToAdd) + " tokens!")));
    }

    @Override
    public ConfigurationSection toConfigurationSection() {
        ConfigurationSection section = new YamlConfiguration();
        section.set("blocksMined", blocksMined);
        section.set("blocksRequired", blocksRequired);
        section.set("tier", tier);
        return section;
    }

    public static TokenLootBox fromConfigurationSection(ConfigurationSection section) {
        TokenLootBox lootBox = new TokenLootBox(section.getLong("blocksRequired"), section.getInt("tier"));
        lootBox.blocksMined = section.getLong("blocksMined");
        lootBox.load(section);
        return lootBox;
    }
}
