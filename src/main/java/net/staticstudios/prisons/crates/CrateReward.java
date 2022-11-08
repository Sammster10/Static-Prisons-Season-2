package net.staticstudios.prisons.crates;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class CrateReward {
    public Component rewardName;
    public ItemStack itemReward = null;
    public ItemStack icon;
    public Consumer<Player> runnableReward = null;


    private String rewardItemID;
    private String[] rewardArgs;
    private int rewardItemAmount = 1;

    public CrateReward(String rewardItemID, String[] args, ItemStack displayItem) {
        this.rewardItemID = rewardItemID;
        this.rewardArgs = args;
        this.icon = displayItem;
        if (displayItem == null) {
            System.out.println(rewardItemID);
        }
    }
    public CrateReward setRewardItemAmount(int rewardItemAmount) {
        this.rewardItemAmount = rewardItemAmount;
        this.icon.setAmount(rewardItemAmount);
        return this;
    }

    public CrateReward(ItemStack itemReward) {
        this.itemReward = itemReward.clone();
        icon = itemReward.clone();
        rewardName = itemReward.getItemMeta().displayName();
    }
    public CrateReward(ItemStack icon, Consumer<Player> runnableReward, Component rewardName) {
        this.icon = icon.clone();
        this.runnableReward = runnableReward;
        this.rewardName = rewardName;
    }
}
