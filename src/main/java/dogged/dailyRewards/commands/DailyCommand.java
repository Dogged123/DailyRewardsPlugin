package dogged.dailyRewards.commands;

import dogged.dailyRewards.DailyRewards;
import dogged.dailyRewards.items.DailyItem;
import dogged.dailyRewards.utils.ItemMaker;
import dogged.dailyRewards.utils.PlayerData;
import dogged.dailyRewards.utils.TextUtils;
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

import java.util.ArrayList;
import java.util.List;

public class DailyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Component.text("You must be a player to use this command", NamedTextColor.RED));
            return true;
        }

        DailyRewards plugin = DailyRewards.getInstance();
        ConfigurationSection rewardsSection = plugin.getConfig().getConfigurationSection("rewards");
        if (rewardsSection == null || rewardsSection.getKeys(false).isEmpty()) {
            p.sendMessage(Component.text("No Daily Rewards have been set up!", NamedTextColor.RED));
            return true;
        }

        Inventory dailyMenu = Bukkit.createInventory(
                p,
                (int) Math.max(9, Math.min(Math.ceil(rewardsSection.getKeys(false).size()/9.0)*9, 54)),
                TextUtils.deserializeAmpersand(plugin.getConfig().getString("title", "&fDaily Rewards"))
        );
        populateDailyMenu(p, dailyMenu);

        p.openInventory(dailyMenu);
        return true;
    }

    public static void populateDailyMenu(Player p, Inventory dailyMenu) {
        DailyRewards plugin = DailyRewards.getInstance();

        ConfigurationSection rewardsSection = plugin.getConfig().getConfigurationSection("rewards");
        if (rewardsSection == null || rewardsSection.getKeys(false).isEmpty()) return;

        PlayerData playerData = PlayerData.getPlayerData(p);

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
        int day = 1;
        for (String key : rewardsSection.getKeys(false)) {
            try {
                day = Integer.parseInt(key.substring(key.indexOf("day") + 3));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                plugin.getLogger().warning(e.getMessage());
                continue;
            }

            if (playerData.hasClaimedDaily(day)) material = claimed;
            else if (playerData.getDayStreak() >= day) material = toClaim;
            else material = cannotClaim;

            List<Component> lore = List.of(TextUtils.deserializeAmpersand("&7Log in for " + day + " days in a row to claim this reward!"));

            if (!plugin.getConfig().getStringList("day_lore").isEmpty()) {
                int finalDay = day;
                lore = plugin.getConfig().getStringList("day_lore").stream()
                        .map(loreLine -> loreLine.replaceAll("<day>", String.valueOf(finalDay)))
                        .map(TextUtils::deserializeAmpersand)
                        .toList();
            }

            String claim = "§a[CLICK TO CLAIM]";
            if (day > playerData.getDayStreak()) claim = "§c[CANNOT CLAIM]";
            if (playerData.hasClaimedDaily(day)) claim = "§a[CLAIMED]";

            lore = new ArrayList<>(lore); // make the list mutable
            lore.add(Component.text(""));
            lore.add(Component.text(claim));

            DailyItem dayItem = new DailyItem(plugin,
                    ItemMaker.buildItem(material,
                            TextUtils.deserializeAmpersand(plugin.getConfig().getString("day_name", "&fDay &c<day>")
                                    .replaceAll("<day>", String.valueOf(day))),
                            lore),
                    day);

            dailyMenu.setItem(day - 1, dayItem.create());
        }

        for (int i = day; i < dailyMenu.getSize(); i++) {
            dailyMenu.setItem(i, ItemMaker.buildItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
    }
}








