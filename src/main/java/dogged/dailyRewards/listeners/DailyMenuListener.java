package dogged.dailyRewards.listeners;

import dogged.dailyRewards.DailyRewards;
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
import org.bukkit.inventory.meta.ItemMeta;

public class DailyMenuListener implements Listener {

    @EventHandler
    public void onDailyClick(InventoryClickEvent e) {
        if (!PlainTextComponentSerializer.plainText().serialize(e.getView().title()).equals("Daily Rewards")) return;

        e.setCancelled(true);

        if (e.getCurrentItem() == null) return;

        ItemMeta meta = e.getCurrentItem().getItemMeta();
        if (meta == null) return;

        Component displayName = meta.displayName();
        if (displayName == null) return;

        String name = PlainTextComponentSerializer.plainText().serialize(displayName);
        if (!name.contains("Day")) return;

        Player p = (Player) e.getWhoClicked();
        PlayerData playerData = PlayerData.getPlayerData(p);

        int day;
        try {
            day = Integer.parseInt(name.substring(name.indexOf("Day ") + 4));
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
    }
}







