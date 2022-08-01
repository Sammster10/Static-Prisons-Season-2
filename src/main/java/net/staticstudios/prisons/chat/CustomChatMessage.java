package net.staticstudios.prisons.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.staticstudios.prisons.UI.tablist.TeamPrefix;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomChatMessage {
    private final AsyncChatEvent event;

    private final PlayerData playerData;

    private final Player player;

    public CustomChatMessage(AsyncChatEvent event) {
        this.event = event;
        this.player = event.getPlayer();
        this.playerData = new PlayerData(player);
    }

    public void sendFormatted() {
        event.renderer((source, sourceDisplayName, message, viewer) -> {
            Component prestige = Component.text("P").append(Component.text(PrisonUtils.prettyNum(playerData.getPrestige()))).color(NamedTextColor.LIGHT_PURPLE);
            Component chatTag1 = ChatTags.getFromID(playerData.getChatTag1());
            Component chatTag2 = ChatTags.getFromID(playerData.getChatTag2());

            Component rankTag = TeamPrefix.getFromID(playerData.getTabListPrefixID());

            HoverEvent<Component> realNameHoverEvent = HoverEvent.showText(Component.text("Real Name: ").color(NamedTextColor.AQUA)
                    .append(source.name().color(NamedTextColor.WHITE))
                    .append(Component.newline())
                    .append(Component.text("Discord Name: ").color(NamedTextColor.AQUA))
                    .append(Component.text(playerData.getDiscordName()).color(NamedTextColor.WHITE))
                    .asComponent());


            Component prefix = Component.join(JoinConfiguration.noSeparators(),
                    Component.text("[").color(NamedTextColor.DARK_GRAY).append(prestige).append(Component.text("] ").color(NamedTextColor.DARK_GRAY)),
                    "".equals(playerData.getChatTag1()) ? Component.empty() : Component.text("[").color(NamedTextColor.DARK_GRAY).append(chatTag1).append(Component.text("] ").color(NamedTextColor.DARK_GRAY)),
                    "".equals(playerData.getChatTag2()) ? Component.empty() : Component.text("[").color(NamedTextColor.DARK_GRAY).append(chatTag2).append(Component.text("] ").color(NamedTextColor.DARK_GRAY)),
                    Component.text("[").color(NamedTextColor.DARK_GRAY).append(rankTag).append(Component.text("] ").color(NamedTextColor.DARK_GRAY)),
                    playerData.getIsChatNicknameEnabled() ? MiniMessage.miniMessage().deserialize(playerData.getChatNickname()) : sourceDisplayName.color(TextColor.fromHexString("#54f08a"))
            );

            //Component censoredMessage = Component.text(chatFilter(PlainTextComponentSerializer.plainText().serialize(message)));
            Component censoredMessage = message;

            if ((playerData.getPlayerRank().contains("warrior") || playerData.getIsNitroBoosting()) &&
                    (!Material.AIR.equals(source.getInventory().getItemInMainHand().getType()) && source.getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) {
                censoredMessage = censoredMessage.replaceText(builder -> builder.match("\\[item\\]").replacement(
                        (source.getInventory().getItemInMainHand().getAmount() > 1
                                ? Component.text("[").append(Component.text(source.getInventory().getItemInMainHand().getAmount())).append(Component.text("x] ")).color(NamedTextColor.AQUA)
                                : Component.empty())
                                .append(Objects.requireNonNull(source.getInventory().getItemInMainHand().getItemMeta().displayName()).hoverEvent(
                                        source.getInventory().getItemInMainHand().asHoverEvent()))
                ).once());

                censoredMessage = censoredMessage.replaceText(builder -> builder.match("\\[item\\]").replacement(Component.empty()));
            }



            return Component.join(JoinConfiguration.noSeparators(),
                    prefix.hoverEvent(realNameHoverEvent),
                    Component.text(": "),
                    censoredMessage.color(playerData.getChatColor()).decorations(playerData.getChatDecorations()));
        });
    }

    private static final List<String> bannedWords = new ArrayList<>();

    public static String chatFilter(String chatMessage) {
        for (String bannedWord : bannedWords){
            StringBuilder censor = new StringBuilder();
            censor.append("*".repeat(bannedWord.length()));
            chatMessage = chatMessage.replaceAll("(?i)" + bannedWord, censor.toString());
        }
        return chatMessage;
    }

    static {
        bannedWords.add("nigger");
        bannedWords.add("nigga");
        bannedWords.add("fuck");
        bannedWords.add("bitch");
        bannedWords.add("cunt");
        bannedWords.add("pussy");
        bannedWords.add("faggot");
        bannedWords.add("bald");
    }
}
