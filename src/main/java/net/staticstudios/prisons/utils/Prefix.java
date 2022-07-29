package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.utils.ComponentUtil;

public class Prefix {

    public static final Component PVP = Component.empty()
            .append(Component.text("PVP").color(ComponentUtil.RED).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component KOTH = Component.empty()
            .append(Component.text("KOTH").color(ComponentUtil.DARK_RED).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component PRESTIGE = Component.empty()
            .append(Component.text("Prestige").color(ComponentUtil.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component LOOT_BOX = Component.empty()
            .append(Component.text("Loot Box").color(ComponentUtil.AQUA).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));
}
