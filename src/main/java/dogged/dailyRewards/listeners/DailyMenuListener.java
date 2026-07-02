package dogged.dailyRewards.listeners;

import dogged.dailyRewards.DailyRewards;
import dogged.dailyRewards.commands.DailyCommand;
import dogged.dailyRewards.items.CustomItem;
import dogged.dailyRewards.utils.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class DailyMenuListener implements Listener {

    @EventHandler
    public void onDailyClick(InventoryClickEvent e) {
        if (!PlainTextComponentSerializer.plainText().serialize(e.getView().title()).equals("Daily Rewards")) return;

        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();
        if (item == null) return;

        String id = CustomItem.getCustomItemId(item, DailyRewards.getInstance());
        if (id == null || !id.contains("day")) return;

        Player p = (Player) e.getWhoClicked();
        PlayerData playerData = PlayerData.getPlayerData(p);

        int day;

        try {
            day = Integer.parseInt(id.replaceAll("\\D+", ""));
        } catch (NumberFormatException exception) {
            return;
        }

        if (day > playerData.getDayStreak()) {
            p.sendMessage(Component.text("You cannot claim this reward yet", NamedTextColor.RED));
            p.playSound(p, Sound.ENTITY_ENDERMAN_HURT, 1, 1);
            return;
        }

        if (playerData.hasClaimedDaily(day)) {
            p.sendMessage(Component.text("You have already claimed this reward", NamedTextColor.RED));
            p.playSound(p, Sound.ENTITY_ENDERMAN_HURT, 1, 1);
            return;
        }

        playerData.addClaimedDay(day);
        p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        Material material;
        int amount;
        for (String s : DailyRewards.getInstance().getConfig().getStringList("rewards.day" + day + ".items")) {
            if (!s.contains(":")) continue;

            material = Material.getMaterial(s.substring(0, s.indexOf(":")).toUpperCase());
            if (material == null) continue;

            try {
                amount = Integer.parseInt(s.substring(s.indexOf(":") + 1));
            } catch (NumberFormatException exception) {
                continue;
            }

            p.getInventory().addItem(new ItemStack(material, amount));
        }

        for (String cmd : DailyRewards.getInstance().getConfig().getStringList("rewards.day" + day + ".commands")) {
            cmd = cmd.replaceAll("<p>", p.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }

        DailyCommand.populateDailyMenu(p, e.getClickedInventory());
    }
}







