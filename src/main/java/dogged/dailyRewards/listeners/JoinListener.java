package dogged.dailyRewards.listeners;

import dogged.dailyRewards.DailyRewards;
import dogged.dailyRewards.utils.PlayerData;
import dogged.dailyRewards.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerData playerData = PlayerData.getPlayerData(p);

        playerData.hasLoggedOn(true);

        if (!playerData.hasClaimedDaily(playerData.getDayStreak())) {
            p.sendMessage(TextUtils.deserializeAmpersand(
                    DailyRewards.getInstance().getConfig().getString("alert_message", "&7[&eDaily Rewards&7] &aYou have rewards waiting, click to claim!"))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/daily"))
            );
        }
    }
}
