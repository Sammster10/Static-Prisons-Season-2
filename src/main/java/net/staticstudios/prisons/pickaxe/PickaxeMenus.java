package net.staticstudios.prisons.pickaxe;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PickaxeMenus extends GUIUtils {

    public static void mainMenu(Player player) {
        GUICreator c = new GUICreator(27, "Pickaxes");

        c.setItem(11, ench(c.createButton(Material.PRISMARINE_CRYSTALS, "&d&lUpgrade Your Pickaxe!", List.of(
                "- Upgrade enchants",
                "- Manage abilities"
        ), (p, t) -> {
            selectPickaxe(p);
        })));


        c.setItem(15, ench(c.createButton(Material.DIAMOND_PICKAXE, "&b&lCreate A Pickaxe", List.of(
                "&oLost your pickaxe and need a new one?",
                "",
                "&bClick here to create a new pickaxe!"
        ), (p, t) -> {
            PrisonUtils.Players.addToInventory(p, PrisonUtils.createNewPickaxe());
            p.closeInventory(); //Prevent spamming of this
        })));

        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }

    public static void open(Player player, PrisonPickaxe pickaxe) {
        GUICreator c = new GUICreator(27, "Manage Your Pickaxe");

        c.setItem(10, ench(c.createButton(Material.NETHER_STAR, "&a&lPickaxe Abilities", List.of(
                "&oUnlock, upgrade, and activate abilities for your pickaxe!"
        ), (p, t) -> {
            AbilityMenus.mainMenu(p, pickaxe);
        })));

        c.setItem(13, ench(c.createButton(Material.ENCHANTED_BOOK, "&d&lPickaxe Enchants", List.of(
                "&oUpgrade your pickaxe's enchants!"
        ), (p, t) -> EnchantMenus.mainMenu(p, pickaxe))));

        c.setItem(16, pickaxe.item);

        c.fill(createGrayPlaceHolder());
        c.open(player);
    }

    public static void selectPickaxe(Player player) {
        GUICreator c = new GUICreator(36, "Select A Pickaxe To Upgrade");
        for (ItemStack item : player.getInventory().getContents()) {
            if (PrisonUtils.checkIsPrisonPickaxe(item)) {
                c.addItem(c.createButton(item, (p, t) -> {
                    open(p, PrisonPickaxe.fromItem(item));
                }));
            }
        }
        c.fill(createGrayPlaceHolder());
        c.open(player);
    }
}
