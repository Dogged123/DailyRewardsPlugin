package dogged.dailyRewards.utils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class CommandUtils {

    public static PluginCommand registerCommand(JavaPlugin plugin, String name, CommandExecutor executor) {
        PluginCommand command = plugin.getCommand(name);
        Objects.requireNonNull(command, "Command '" + name + "' is missing from plugin.yml!");
        command.setExecutor(executor);
        return command;
    }

    public static void registerCommand(JavaPlugin plugin, String name, CommandExecutor executor, TabCompleter tabCompleter) {
        PluginCommand command = registerCommand(plugin, name, executor);
        command.setTabCompleter(tabCompleter);
    }
}
