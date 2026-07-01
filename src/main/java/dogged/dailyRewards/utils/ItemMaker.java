package dogged.dailyRewards.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ItemMaker {
    public static ItemStack buildItem(Material type, String displayName) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) resultMeta.displayName(Component.text(displayName));
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, Component displayName) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) resultMeta.displayName(displayName);
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, Component displayName, int amount) {
        ItemStack result = new ItemStack(type, amount);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) resultMeta.displayName(displayName);
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, boolean unbreakable) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.setUnbreakable(unbreakable);
        }
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String displayName, String lore) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(Component.text(displayName));
            resultMeta.lore(Collections.singletonList(Component.text(lore)));
        }
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, Component displayName, Component lore) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(displayName);
            resultMeta.lore(Collections.singletonList(lore));
        }
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String lore, int amount) {
        ItemStack result = new ItemStack(type, amount);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.lore(Collections.singletonList(Component.text(lore)));
        }
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String displayName, List<Component> lore) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(Component.text(displayName));
            resultMeta.lore(lore);
        }
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String displayName, String lore, boolean unbreakable) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(Component.text(displayName));
            resultMeta.lore(Collections.singletonList(Component.text(lore)));
            resultMeta.setUnbreakable(unbreakable);
        }
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, Component displayName, List<Component> lore) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(displayName);
            resultMeta.lore(lore);
        }
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, Component displayName, List<Component> lore, boolean unbreakable) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(displayName);
            resultMeta.lore(lore);
            resultMeta.setUnbreakable(unbreakable);
        }
        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, boolean unbreakable, Map<Enchantment, Integer> enchants) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();

        if (resultMeta != null) {
            resultMeta.setUnbreakable(true);

            for (Enchantment enchantment : enchants.keySet()) {
                resultMeta.addEnchant(enchantment, enchants.get(enchantment), true);
            }
        }

        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String displayName, Map<Enchantment, Integer> enchants) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(Component.text(displayName));

            for (Enchantment enchantment : enchants.keySet()) {
                resultMeta.addEnchant(enchantment, enchants.get(enchantment), true);
            }
        }

        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String displayName, Map<Enchantment, Integer> enchants, boolean unbreakable) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(Component.text(displayName));

            for (Enchantment enchantment : enchants.keySet()) {
                resultMeta.addEnchant(enchantment, enchants.get(enchantment), true);
            }

            resultMeta.setUnbreakable(unbreakable);
        }

        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String displayName, List<Component> lore, Map<Enchantment, Integer> enchants, boolean unbreakable) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(Component.text(displayName));

            for (Enchantment enchantment : enchants.keySet()) {
                resultMeta.addEnchant(enchantment, enchants.get(enchantment), true);
            }

            resultMeta.lore(lore);
            resultMeta.setUnbreakable(unbreakable);
        }

        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String displayName, int amount, Map<Enchantment, Integer> enchants, boolean hideEnchants) {
        ItemStack result = new ItemStack(type, amount);
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta != null) {
            resultMeta.displayName(Component.text(displayName));

            for (Enchantment enchantment : enchants.keySet()) {
                resultMeta.addEnchant(enchantment, enchants.get(enchantment), true);
            }
        }

        if (hideEnchants && resultMeta != null) {
            resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        result.setItemMeta(resultMeta);

        return result;
    }

    public static ItemStack buildItem(Material type, String displayName, List<Component> lore, Map<Enchantment, Integer> enchants) {
        ItemStack result = new ItemStack(type);
        ItemMeta resultMeta = result.getItemMeta();

        if (resultMeta != null) {
            resultMeta.displayName(Component.text(displayName));
            resultMeta.lore(lore);

            for (Enchantment enchantment : enchants.keySet()) {
                resultMeta.addEnchant(enchantment, enchants.get(enchantment), true);
            }
        }

        result.setItemMeta(resultMeta);

        return result;
    }
}
