package dogged.dailyRewards.commands;

import dogged.dailyRewards.DailyRewards;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.hasPermission("easy_prisons.reload")) {
            DailyRewards.getInstance().reloadConfig();

            sender.sendMessage(Component.text("Config file reloaded!", NamedTextColor.GREEN));
            return true;
        }

        sender.sendMessage(Component.text("You do not have the required permissions to execute this command", NamedTextColor.RED));
        return true;
    }
}