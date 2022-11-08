package net.staticstudios.prisons.crates;

import net.kyori.adventure.text.Component;
import net.staticstudios.mines.utils.WeightedElement;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.handler.CustomItems;
import net.staticstudios.prisons.customitems.old.Vouchers;
import net.staticstudios.prisons.customitems.MineBombCustomItem;
import net.staticstudios.prisons.customitems.pickaxes.PickaxeTemplates;
import net.staticstudios.prisons.customitems.pouches.MoneyPouch;
import net.staticstudios.prisons.customitems.pouches.MultiPouch;
import net.staticstudios.prisons.customitems.pouches.TokenPouch;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.utils.StaticFileSystemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Crates { //todo: configuration file

    public static Crate RARE;
    public static Crate EPIC;
    public static Crate LEGENDARY;
    public static Crate STATIC;
    public static Crate STATICP;
    public static Crate PICKAXE;
    public static Crate VOTE;
    public static Crate KIT;

    public static void init() {
        Crate.init();

        ConfigurationSection config = StaticFileSystemManager.getYamlConfiguration("crates.yml");




        new Crate("common", "Common Crate", "common", new Location(Bukkit.getWorld("world"), -51, 80, -137), loadCrate(config, "common"));
        new Crate("rare", "Rare Crate", "rare", new Location(Bukkit.getWorld("world"), -42, 80, -137), loadCrate(config, "rare"));





//        RARE = new Crate("rare", "Rare Crate", "rare", new Location(Bukkit.getWorld("world"), -42, 80, -137),
//                new WeightedElements<CrateReward>()
//                        .add(new CrateReward(MoneyPouch.TIER_1.getItem(null)), 10)
//                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_1.getItem(null), 2)), 10)
//                        .add(new CrateReward(TokenPouch.TIER_1.getItem(null)), 10)
//                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_1.getItem(null), 2)), 15)
//                        .add(new CrateReward(MultiPouch.TIER_1.getItem(null)), 10)
//                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_1.getItem(null), 3)), 5)
//                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_2.getItem(null), 1)), 15)
//                        .add(new CrateReward(MineBombCustomItem.TIER_3.getItem(null)), 10)
//                        .add(new CrateReward(CustomItems.getRareCrateKey(2)), 4)
//                        .add(new CrateReward(CustomItems.getEpicCrateKey(1)), 2)
//                        .add(new CrateReward(CustomItems.getEpicCrateKey(2)), 1)
//                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(1)), 2)
//                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 4)
//                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2)
//        );

        EPIC = new Crate("epic", "Epic Crate", "epic", new Location(Bukkit.getWorld("world"), -33, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(MoneyPouch.TIER_1.getItem(null)), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_1.getItem(null), 2)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_1.getItem(null), 3)), 5)
                        .add(new CrateReward(TokenPouch.TIER_1.getItem(null)), 8)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_1.getItem(null), 2)), 10)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_1.getItem(null), 3)), 5)
                        .add(new CrateReward(MultiPouch.TIER_1.getItem(null)), 5)
                        .add(new CrateReward(MultiPouch.TIER_2.getItem(null)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_1.getItem(null), 4)), 10)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_2.getItem(null), 2)), 5)
                        .add(new CrateReward(MineBombCustomItem.TIER_3.getItem(null)), 8)
                        .add(new CrateReward(MineBombCustomItem.TIER_4.getItem(null)), 2)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(2)), 5)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(3)), 3)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(1)), 4.5)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(2)), 2)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(1)), 1)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 4)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(2)), 2.99)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2.5)
                        .add(new CrateReward(Vouchers.AUTO_SELL.item), 0.01)
        );

        LEGENDARY = new Crate("legendary", "Legendary Crate", "legendary", new Location(Bukkit.getWorld("world"), -24, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(MoneyPouch.TIER_1.getItem(null)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_1.getItem(null), 2)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_1.getItem(null), 3)), 5)
                        .add(new CrateReward(MoneyPouch.TIER_2.getItem(null)), 2.5)
                        .add(new CrateReward(TokenPouch.TIER_1.getItem(null)), 2.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_1.getItem(null), 2)), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_1.getItem(null), 3)), 7)
                        .add(new CrateReward(TokenPouch.TIER_2.getItem(null)), 5)
                        .add(new CrateReward(MultiPouch.TIER_1.getItem(null)), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(MultiPouch.TIER_1.getItem(null), 2)), 6)
                        .add(new CrateReward(MultiPouch.TIER_2.getItem(null)), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_2.getItem(null), 4)), 7.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_3.getItem(null), 2)), 7.5)
                        .add(new CrateReward(MineBombCustomItem.TIER_4.getItem(null)), 5)
                        .add(new CrateReward(CustomItems.getCommonCrateKey(8)), 1)
                        .add(new CrateReward(CustomItems.getRareCrateKey(3)), 5)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(2)), 3)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(1)), 2)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(2)), 1)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 4)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(2)), 2.975)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2)
                        .add(new CrateReward(Vouchers.AUTO_SELL.item), 0.015)
                        .add(new CrateReward(Vouchers.WARRIOR_RANK.item), 0.01)
        );

        STATIC = new Crate("static", "Static Crate", "static", new Location(Bukkit.getWorld("world"), -15, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(MoneyPouch.TIER_1.getItem(null)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_1.getItem(null), 2)), 2.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_1.getItem(null), 3)), 2)
                        .add(new CrateReward(MoneyPouch.TIER_2.getItem(null)), 7.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_2.getItem(null), 2)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_2.getItem(null), 3)), 3)
                        .add(new CrateReward(TokenPouch.TIER_1.getItem(null)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_1.getItem(null), 2)), 2.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_1.getItem(null), 3)), 2)
                        .add(new CrateReward(TokenPouch.TIER_2.getItem(null)), 3.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_2.getItem(null), 2)), 4)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_2.getItem(null), 3)), 5)
                        .add(new CrateReward(MultiPouch.TIER_1.getItem(null)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(MultiPouch.TIER_1.getItem(null), 2)), 4)
                        .add(new CrateReward(MultiPouch.TIER_2.getItem(null)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MultiPouch.TIER_2.getItem(null), 2)), 5)
                        .add(new CrateReward(MultiPouch.TIER_3.getItem(null)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_2.getItem(null), 5)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_3.getItem(null), 2)), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_3.getItem(null), 3)), 5)
                        .add(new CrateReward(MineBombCustomItem.TIER_4.getItem(null)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_4.getItem(null), 2)), 1)
                        .add(new CrateReward(CustomItems.getCommonCrateKey(15)), 3)
                        .add(new CrateReward(CustomItems.getRareCrateKey(7)), 2)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(4)), 2)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(2)), 1)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(1)), 2)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(2)), 0.5)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(3)), 0.5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 4)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(2)), 2.970)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2)
                        .add(new CrateReward(Vouchers.AUTO_SELL.item), 0.01)
                        .add(new CrateReward(Vouchers.WARRIOR_RANK.item), 0.01)
                        .add(new CrateReward(Vouchers.MASTER_RANK.item), 0.005)
                        .add(new CrateReward(Vouchers.MYTHIC_RANK.item), 0.005)
        );

        STATICP = new Crate("staticp", "Static+ Crate", "staticp", new Location(Bukkit.getWorld("world"), 3, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_1.getItem(null), 4)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_2.getItem(null), 3)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_2.getItem(null), 4)), 2.5)
                        .add(new CrateReward(MoneyPouch.TIER_3.getItem(null)), 6)
                        .add(new CrateReward(PrisonUtils.setItemCount(MoneyPouch.TIER_3.getItem(null), 2)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_1.getItem(null), 4)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_2.getItem(null), 3)), 4.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_2.getItem(null), 4)), 3)
                        .add(new CrateReward(TokenPouch.TIER_3.getItem(null)), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(TokenPouch.TIER_3.getItem(null), 2)), 2)
                        .add(new CrateReward(MultiPouch.TIER_1.getItem(null)), 1)
                        .add(new CrateReward(PrisonUtils.setItemCount(MultiPouch.TIER_1.getItem(null), 3)), 3)
                        .add(new CrateReward(MultiPouch.TIER_2.getItem(null)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MultiPouch.TIER_2.getItem(null), 2)), 2)
                        .add(new CrateReward(MultiPouch.TIER_3.getItem(null)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(MultiPouch.TIER_3.getItem(null), 2)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_1.getItem(null), 24)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_3.getItem(null), 4)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_3.getItem(null), 6)), 5)
                        .add(new CrateReward(MineBombCustomItem.TIER_4.getItem(null)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_4.getItem(null), 2)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBombCustomItem.TIER_4.getItem(null), 4)), 1)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(5)), 8.5)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(2)), 10)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(3)), 9)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(2)), 2.5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(3)), 3.3)
                        .add(new CrateReward(CustomItems.getKitCrateKey(4)), 1)
                        .add(new CrateReward(Vouchers.AUTO_SELL.item), 0.1)
                        .add(new CrateReward(Vouchers.WARRIOR_RANK.item), 0.1)
                        .add(new CrateReward(Vouchers.MASTER_RANK.item), 0.1)
                        .add(new CrateReward(Vouchers.MYTHIC_RANK.item), 0.1)
                        .add(new CrateReward(Vouchers.STATIC_RANK.item), 0.1)
                        .add(new CrateReward(Vouchers.STATICP_RANK.item), 0.2)
        );

        VOTE = new Crate("vote", "Vote Crate", "vote", new Location(Bukkit.getWorld("world"), -54, 80, -125),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(CustomItems.getCommonCrateKey(1)), 13.4)
                        .add(new CrateReward(CustomItems.getCommonCrateKey(2)), 25)
                        .add(new CrateReward(CustomItems.getRareCrateKey(1)), 20)
                        .add(new CrateReward(CustomItems.getRareCrateKey(2)), 10)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(1)), 7.5)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(2)), 2.5)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(1)), 5)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(2)), 2)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(1)), 1.5)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(2)), 2.5)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(3)), 0.5)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(1)), 0.075)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(3)), 0.025)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2.5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(2)), 2.5)
        );

        KIT = new Crate("kit", "Kit Crate", "kit", new Location(Bukkit.getWorld("world"), -54, 80, -134),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(Vouchers.KIT_TIER_1.item), 50)
                        .add(new CrateReward(Vouchers.KIT_TIER_2.item), 25)
                        .add(new CrateReward(Vouchers.KIT_TIER_3.item), 15)
                        .add(new CrateReward(Vouchers.KIT_TIER_4.item), 5)
                        .add(new CrateReward(Vouchers.KIT_TIER_5.item), 2.5)
                        .add(new CrateReward(Vouchers.KIT_TIER_6.item), 0.5)
                        .add(new CrateReward(Vouchers.KIT_POTIONS.item), 1)
                        .add(new CrateReward(Vouchers.KIT_WEAPONS.item), 1)
        );

        PICKAXE = new Crate("pickaxe", "Pickaxe Crate", "pickaxe", new Location(Bukkit.getWorld("world"), 12, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(PickaxeTemplates.TIER_1.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_1.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_1.getTemplateItem().getItemMeta().displayName())), 25)
                        .add(new CrateReward(PickaxeTemplates.TIER_2.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_2.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_2.getTemplateItem().getItemMeta().displayName())), 20)
                        .add(new CrateReward(PickaxeTemplates.TIER_3.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_3.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_3.getTemplateItem().getItemMeta().displayName())), 15)
                        .add(new CrateReward(PickaxeTemplates.TIER_4.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_4.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_4.getTemplateItem().getItemMeta().displayName())), 12.5)
                        .add(new CrateReward(PickaxeTemplates.TIER_5.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_5.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_5.getTemplateItem().getItemMeta().displayName())), 10)
                        .add(new CrateReward(PickaxeTemplates.TIER_6.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_6.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_6.getTemplateItem().getItemMeta().displayName())), 7.5)
                        .add(new CrateReward(PickaxeTemplates.TIER_7.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_7.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_7.getTemplateItem().getItemMeta().displayName())), 6)
                        .add(new CrateReward(PickaxeTemplates.TIER_8.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_8.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_8.getTemplateItem().getItemMeta().displayName())), 2.5)
                        .add(new CrateReward(PickaxeTemplates.TIER_9.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_9.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_9.getTemplateItem().getItemMeta().displayName())), 1)
                        .add(new CrateReward(PickaxeTemplates.TIER_10.getTemplateItem(), p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_10.getItem(p)), Component.text("1x ").append(PickaxeTemplates.TIER_10.getTemplateItem().getItemMeta().displayName())), 0.5)
        );

    }

    private static WeightedElements<CrateReward> loadCrate(ConfigurationSection config, String crate) {
        WeightedElements<CrateReward> rewards = new WeightedElements<>();

        ConfigurationSection crateConfig = config.getConfigurationSection(crate);
        if (crateConfig == null) {
            StaticPrisons.log("Crate " + crate + " not found in crates.yml! Giving it no rewards...");
            return new WeightedElements<>();
        }

        crateConfig.getKeys(false).forEach(key -> {
            ConfigurationSection reward = crateConfig.getConfigurationSection(key);
            String itemID = key.replaceAll("\\+", "");
            int amount = reward.getInt("amount", 1);
            String[] displayArgs = reward.getStringList("display_item_args").toArray(new String[0]);
            String[] args = reward.getStringList("args").toArray(new String[0]);
            rewards.add(new CrateReward(itemID, args, CustomItems.getItem(itemID, null, displayArgs)).setRewardItemAmount(amount), reward.getDouble("chance"));
        });
        if (rewards.getTotalWeight() != 100) {
            StaticPrisons.log("Crate " + crate + " has a total weight of " + rewards.getTotalWeight() + "! It should be 100!");
        }
        return rewards;
    }

}
