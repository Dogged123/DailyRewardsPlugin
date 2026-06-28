package dogged.dailyRewards;

import dogged.dailyRewards.commands.DailyCommand;
import dogged.dailyRewards.listeners.DailyMenuListener;
import dogged.dailyRewards.listeners.JoinListener;
import dogged.dailyRewards.utils.CustomConfig;
import dogged.dailyRewards.utils.PlayerData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public final class DailyRewards extends JavaPlugin {
    private static int day;

    private static DailyRewards instance;
    private static CustomConfig dayConfig;
    private static CustomConfig playerDataConfig;

    @Override
    public void onEnable() {
        instance = this;
        playerDataConfig = new CustomConfig(this, "player_data");
        dayConfig = new CustomConfig(this, "day");

        LocalDate today = LocalDate.now();
        day = dayConfig.getConfig().getInt("day", today.getDayOfMonth());
        detectInitialDayChange();

        getCommand("daily").setExecutor(new DailyCommand());

        getServer().getPluginManager().registerEvents(new DailyMenuListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);


        String name;
        int dayStreak;
        List<Integer> claimedDays;

        for (String uuid : playerDataConfig.getConfig().getKeys(false)) {
            name = playerDataConfig.getConfig().getString(uuid + ".name");
            dayStreak = playerDataConfig.getConfig().getInt(uuid + ".day_streak", 1);
            claimedDays = playerDataConfig.getConfig().getIntegerList(uuid + ".claimed_days");

            PlayerData playerData = new PlayerData(UUID.fromString(uuid), name);

            playerData.setClaimedDays(claimedDays);
            playerData.setDayStreak(dayStreak);
        }

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        dayConfig.getConfig().set("day", day);
        dayConfig.saveConfig();

        for (UUID uuid : PlayerData.playersData.keySet()) {
            playerDataConfig.getConfig().set(uuid + ".name", PlayerData.playersData.get(uuid).getName());
            playerDataConfig.getConfig().set(uuid + ".day_streak", PlayerData.playersData.get(uuid).getDayStreak());
            playerDataConfig.getConfig().set(uuid + ".claimed_days", PlayerData.playersData.get(uuid).getClaimedDays());
        }

        playerDataConfig.saveConfig();
    }


    private void detectInitialDayChange() {
        new BukkitRunnable() {
            @Override
            public void run() {
                LocalDate today = LocalDate.now();
                if (today.getDayOfMonth() != day) {
                    cancel();
                    detectFurtherDayChanges();
                    handleNewDay();
                }
            }
        }.runTaskTimer(this, 0, 20*60L);
    }

    private void detectFurtherDayChanges() {
        new BukkitRunnable() {
            @Override
            public void run() {
                LocalDate today = LocalDate.now();
                if (today.getDayOfMonth() != day) {
                    handleNewDay();
                }
            }
        }.runTaskTimer(this, 0, 20*60*60*24L);
    }

    private void handleNewDay() {
        LocalDate today = LocalDate.now();

        // new month just started
        boolean newMonth = (day == 30 || day == 31) && (today.getDayOfMonth() == 1);

        day = today.getDayOfMonth();

        for (PlayerData playerData : PlayerData.playersData.values()) {
            if (newMonth) {
                playerData.resetClaimedDays();
            } else {
                if (playerData.hasLoggedOn()) {
                    playerData.hasLoggedOn(false);
                    playerData.incrementDayStreak();
                } else playerData.setDayStreak(1);
            }
        }
    }

    public static DailyRewards getInstance() {return instance;}
    public static int getDay() {return day;}
}




