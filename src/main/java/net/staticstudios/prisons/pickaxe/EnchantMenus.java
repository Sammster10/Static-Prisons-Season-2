package net.staticstudios.prisons.pickaxe;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantMenus extends GUIUtils {

    public static void selectPickaxe(Player player) {
        GUICreator c = new GUICreator(36, "Select a pickaxe to enchant");
        for (ItemStack item : player.getInventory().getContents()) {
            if (PrisonPickaxe.checkIsPrisonPickaxe(item)) {
                c.addItem(c.createButton(item, (p, t) -> {
                    mainMenu(p, PrisonPickaxe.fromItem(item));
                }));
            }
        }
        c.fill(createGrayPlaceHolder());
        c.open(player);
    }

    public static void mainMenu(Player player, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(54, "Pickaxe Enchants");
        c.setItems(
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.FORTUNE, c, Material.DIAMOND, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.DOUBLE_FORTUNE, c, Material.AMETHYST_SHARD, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.TOKENATOR, c, Material.SUNFLOWER, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.KEY_FINDER, c, Material.TRIPWIRE_HOOK, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.METAL_DETECTOR, c, Material.FLINT_AND_STEEL, false),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.HASTE, c, Material.GOLDEN_PICKAXE, false),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.EXPLOSION, c, Material.TNT, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.JACK_HAMMER, c, Material.ANVIL, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.DOUBLE_JACK_HAMMER, c, Material.HOPPER, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.MULTI_DIRECTIONAL, c, Material.COMPARATOR, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.EGG_SHOOTER, c, Material.EGG, false),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.SPEED, c, Material.FEATHER, false),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.CONSISTENCY, c, Material.EMERALD, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.MERCHANT, c, Material.MAP, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.AUTO_SELL, c, Material.GOLD_NUGGET, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.XP_FINDER, c, Material.EXPERIENCE_BOTTLE, false),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.BACKPACK_FINDER, c, Material.CHEST_MINECART, false),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PickaxeEnchants.NIGHT_VISION, c, Material.LAPIS_LAZULI, false),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),

                c.createButton(Material.SUNFLOWER, "&e&lYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()), List.of()),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                c.createButton(Material.GUNPOWDER, "&c&lEnchant Settings", List.of("Enable/disable an enchant"), (p, t) -> {
                    openSettings(p, pickaxe);
                }),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                c.createButton(Material.AMETHYST_CLUSTER, "&d&lYour Prestige Tokens: &f" + PrisonUtils.prettyNum(playerData.getPrestigeTokens()), List.of())
        );
        c.open(player);
        c.setOnCloseRun((p, t) -> PickaxeMenus.open(p, pickaxe));
    }

    public static void openSettings(Player player, PrisonPickaxe pickaxe) {
        GUICreator c = new GUICreator(45, "Enchant Settings");
        c.setItems(
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantSettingsButton( pickaxe, PickaxeEnchants.FORTUNE, c, Material.DIAMOND, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.DOUBLE_FORTUNE, c, Material.AMETHYST_SHARD, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.TOKENATOR, c, Material.SUNFLOWER, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.KEY_FINDER, c, Material.TRIPWIRE_HOOK, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.METAL_DETECTOR, c, Material.FLINT_AND_STEEL, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.HASTE, c, Material.GOLDEN_PICKAXE, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.EXPLOSION, c, Material.TNT, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.JACK_HAMMER, c, Material.ANVIL, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.DOUBLE_JACK_HAMMER, c, Material.HOPPER, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.MULTI_DIRECTIONAL, c, Material.COMPARATOR, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.EGG_SHOOTER, c, Material.EGG, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.SPEED, c, Material.FEATHER, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.CONSISTENCY, c, Material.EMERALD, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.MERCHANT, c, Material.MAP, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.AUTO_SELL, c, Material.GOLD_NUGGET, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.XP_FINDER, c, Material.EXPERIENCE_BOTTLE, true),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.BACKPACK_FINDER, c, Material.CHEST_MINECART, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PickaxeEnchants.NIGHT_VISION, c, Material.LAPIS_LAZULI, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );
        c.open(player);
        c.setOnCloseRun((p, t) -> mainMenu(p, pickaxe));
    }


    //---------- vvv Util methods vvv ----------
    static ItemStack createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon) {
        List<String> desc = new ArrayList<>(enchant.DESCRIPTION);
        desc.add("");
        desc.add("&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)));
        desc.add("&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE) + " Tokens");
        desc.add("&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()));
        desc.add("");
        desc.add("&bMax Level: &f" + PrisonUtils.addCommasToNumber(enchant.getMaxLevel(pickaxe.getEnchantTier(enchant))));
        boolean locked = enchant.getPickaxeLevelRequirement() > pickaxe.getLevel() || enchant.getPlayerLevelRequirement() > playerData.getPlayerLevel();
        if (locked) {
            desc.add("");
            if (enchant.getPlayerLevelRequirement() > playerData.getPlayerLevel()) desc.add("&cMinimum Player Level: &f" + enchant.getPlayerLevelRequirement());
            if (enchant.getPickaxeLevelRequirement() > pickaxe.getLevel()) desc.add("&cMinimum Pickaxe Level: &f" + enchant.getPickaxeLevelRequirement());
            return c.createButton(icon, "&c&l[Locked] " + enchant.DISPLAY_NAME, desc, (p, t) -> {
                if (enchant.getPickaxeLevelRequirement() > pickaxe.getLevel()) p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour pickaxe is not high enough level to unlock this enchant!"));
                else p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not a high enough level to unlock this enchant!"));
            });
        }
        String name = enchant.DISPLAY_NAME;
        if (!pickaxe.getIsEnchantEnabled(enchant)) {
            desc.add("");
            name = "&c&l[Disabled] " + name;
        }
        return c.createButton(icon, name, desc, (p, t) -> {
            GUICreator _c = new GUICreator(9, enchant.DISPLAY_NAME);
            _c.setOnCloseRun((_p, _t) -> mainMenu(p, pickaxe));
            buildMenuContent(_c, p, enchant, pickaxe);
            _c.open(p);
        });
    }
    static ItemStack createEnchantSettingsButton(PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon, boolean enchantIcon) {
        ItemStack item = createEnchantSettingsButton(pickaxe, enchant, c, icon);
        if (enchantIcon) ench(item);
        return item;
    }
    static ItemStack createEnchantSettingsButton(PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon) {
        boolean isEnabled = pickaxe.getIsEnchantEnabled(enchant);
        List<String> desc = new ArrayList<>(enchant.DESCRIPTION);
        desc.add("");
        desc.add("&bLevel: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)) + " / " + PrisonUtils.addCommasToNumber(enchant.MAX_LEVEL));
        desc.add("&bCurrent State: &f" + (isEnabled ? "&aEnabled" : "&cDisabled"));
        desc.add("");
        desc.add("&7&oClick to " + (isEnabled ? "disable" : "enable"));
        ItemStack button = c.createButton(icon, enchant.DISPLAY_NAME, desc, (p, t) -> {
            pickaxe.setIsEnchantEnabled(p, enchant, !pickaxe.getIsEnchantEnabled(enchant));
            openSettings(p, pickaxe);
        });
        if (isEnabled) button = ench(button);
        return button;
    }
    static ItemStack createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon, boolean enchantIcon) {
        ItemStack item = createEnchantButton(playerData, pickaxe, enchant, c, icon);
        if (enchantIcon) {
            return ench(item);
        }
        return item;
    }

    static void buildMenuContent(GUICreator c, Player player, BaseEnchant enchant, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        c.setItems(
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1 level of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(enchant.getMaxLevel(pickaxe.getEnchantTier(enchant)))
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 1);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 10 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE * 10) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(enchant.getMaxLevel(pickaxe.getEnchantTier(enchant)))
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 10);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 100 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE * 100) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(enchant.getMaxLevel(pickaxe.getEnchantTier(enchant)))
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 100);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1,000 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE * 1000) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(enchant.getMaxLevel(pickaxe.getEnchantTier(enchant)))
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 1000);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy MAX (" + Math.min((long) enchant.getMaxLevel(pickaxe.getEnchantTier(enchant)) - pickaxe.getEnchantLevel(enchant), playerData.getTokens() / enchant.PRICE) + ") levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE * Math.min((long) enchant.getMaxLevel(pickaxe.getEnchantTier(enchant)) - pickaxe.getEnchantLevel(enchant), playerData.getTokens() / enchant.PRICE)) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(enchant.getMaxLevel(pickaxe.getEnchantTier(enchant)))
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, Math.min(enchant.getMaxLevel(pickaxe.getEnchantTier(enchant)) - pickaxe.getEnchantLevel(enchant), playerData.getTokens() / enchant.PRICE));
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );

        if (enchant.getTier(pickaxe.getEnchantTier(enchant) + 1) != null) {
            c.setItem(0, c.createButton(Material.NETHER_STAR, "&b&lIncrease Tier", List.of(
                    "Increasing the tier of this enchantment",
                    "will set its max level to " + PrisonUtils.addCommasToNumber(enchant.getMaxLevel(pickaxe.getEnchantTier(enchant) + 1)) + ". This will",
                    "cost " + PrisonUtils.addCommasToNumber(enchant.getTier(pickaxe.getEnchantTier(enchant) + 1).prestigeTokensRequired()) + " Prestige Token(s), which can",
                    "be obtained from prestiging.",
                    "",
                    "&eCurrent Tier: &f" + pickaxe.getEnchantTier(enchant),
                    "&eNext Tier: &f" + (pickaxe.getEnchantTier(enchant) + 1),
                    "&eUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.getTier(pickaxe.getEnchantTier(enchant) + 1).prestigeTokensRequired()) + " Prestige Tokens",
                    "&eYour Prestige Tokens: &f" + PrisonUtils.prettyNum(playerData.getPrestigeTokens())
            ), (p, t) -> {
                if (playerData.getPrestigeTokens() >= enchant.getTier(pickaxe.getEnchantTier(enchant) + 1).prestigeTokensRequired()) {
                    playerData.removePrestigeTokens(enchant.getTier(pickaxe.getEnchantTier(enchant) + 1).prestigeTokensRequired());
                    pickaxe.setEnchantTier(enchant, pickaxe.getEnchantTier(enchant) + 1);
                    p.sendMessage(org.bukkit.ChatColor.AQUA + "You successfully upgraded your pickaxe!");
                    buildMenuContent(c, p, enchant, pickaxe);
                } else {
                    p.sendMessage(ChatColor.RED + "You do not have enough Prestige Tokens to upgrade this!");
                }
            }));
        }

    }
}