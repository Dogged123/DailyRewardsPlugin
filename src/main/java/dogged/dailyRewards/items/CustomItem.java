package dogged.dailyRewards.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CustomItem {

    private final String id;
    protected final JavaPlugin plugin;

    public CustomItem(String id, JavaPlugin plugin) {
        this.plugin = plugin;
        this.id = id;
    }

    protected abstract ItemStack buildBaseItem();

    public ItemStack create() {
        ItemStack item = buildBaseItem();
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            NamespacedKey key = new NamespacedKey(plugin, "custom_item_id");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, id);
            item.setItemMeta(meta);
        }

        return item;
    }

    public static String getCustomItemId(ItemStack item, JavaPlugin plugin) {
        if (item == null || item.getItemMeta() == null) return null;

        NamespacedKey key = new NamespacedKey(plugin, "custom_item_id");

        return item.getItemMeta().getPersistentDataContainer().get(key,  PersistentDataType.STRING);
    }
}
