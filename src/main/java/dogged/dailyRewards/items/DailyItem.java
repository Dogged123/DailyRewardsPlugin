package dogged.dailyRewards.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DailyItem extends CustomItem {
    private final int id;

    private final ItemStack item;

    public DailyItem(JavaPlugin plugin, ItemStack item, int id) {
        super("day_" + id, plugin);
        this.id = id;
        this.item = item;
    }

    @Override
    protected ItemStack buildBaseItem() {
        return item;
    }

    public int getId() {
        return id;
    }
}
