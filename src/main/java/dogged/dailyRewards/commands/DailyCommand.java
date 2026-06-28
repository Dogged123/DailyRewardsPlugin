package dogged.dailyRewards.commands;

import dogged.dailyRewards.DailyRewards;
import dogged.dailyRewards.utils.ItemMaker;
import dogged.dailyRewards.utils.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class DailyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Component.text("You must be a player to use this command", NamedTextColor.RED));
            return true;
        }

        DailyRewards plugin = DailyRewards.getInstance();
        PlayerData playerData = PlayerData.getPlayerData(p);

        Inventory dailyMenu = Bukkit.createInventory(p, 36, Component.text("Daily Rewards", NamedTextColor.YELLOW));

        ConfigurationSection rewardsSection = plugin.getConfig().getConfigurationSection("rewards");
        if (rewardsSection == null || rewardsSection.getKeys(false).isEmpty()) {
            p.sendMessage(Component.text("No Daily Rewards have been set up!", NamedTextColor.RED));
            return true;
        }

        Material claimed = Material.getMaterial(plugin.getConfig().getString("claimed_material", "emerald_block").toUpperCase());
        Material toClaim = Material.getMaterial(plugin.getConfig().getString("to_claim_material", "gold_block").toUpperCase());
        Material cannotClaim = Material.getMaterial(plugin.getConfig().getString("cannot_claim_material", "redstone_block").toUpperCase());

        if (claimed == null) {
            claimed = Material.EMERALD_BLOCK;
            plugin.getLogger().warning("[WARN] Claimed Material Is Invalid");
        }
        if (toClaim == null) {
            toClaim = Material.GOLD_BLOCK;
            plugin.getLogger().warning("[WARN] To Claim Material Is Invalid");
        }
        if (cannotClaim == null) {
            cannotClaim = Material.REDSTONE_BLOCK;
            plugin.getLogger().warning("[WARN] Cannot Claim Material Is Invalid");
        }

        Material material;
        int day;
        for (String key : rewardsSection.getKeys(false)) {
            try {
                day = Integer.parseInt(key.substring(key.indexOf("day") + 3));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                plugin.getLogger().warning(e.getMessage());
                continue;
            }

            if (playerData.hasClaimedDaily(day)) material = claimed;
            else if (playerData.getDayStreak() == day) material = toClaim;
            else material = cannotClaim;

            dailyMenu.setItem(day - 1, ItemMaker.buildItem(material, Component.text("Day " + day, NamedTextColor.GOLD)));
        }

        p.openInventory(dailyMenu);

        return true;
    }
}








