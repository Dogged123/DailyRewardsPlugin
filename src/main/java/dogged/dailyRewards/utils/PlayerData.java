package dogged.dailyRewards.utils;

import dogged.dailyRewards.DailyRewards;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PlayerData {
    public static final Map<UUID, PlayerData> playersData = new HashMap<>();

    private String name;
    private int dayStreak;
    private boolean loggedOn;

    private List<Integer> claimedDays;

    public PlayerData(UUID uuid, String name) {
        init(uuid, name);
    }

    private void init(UUID uuid, String n) {
        if (n == null) n = "";
        name = n;
        dayStreak = 1;
        loggedOn = false;

        claimedDays = new ArrayList<>();

        playersData.put(uuid, this);
    }

    public String getName() {return name;}

    public int getDayStreak() {return dayStreak;}
    public void setDayStreak(int ds) {dayStreak = ds;}
    public void incrementDayStreak() {dayStreak++;}

    public boolean hasLoggedOn() {return loggedOn;}
    public void hasLoggedOn(boolean logOn) {loggedOn = logOn;}

    public boolean hasClaimedDaily() {return claimedDays.contains(DailyRewards.getDay());}
    public boolean hasClaimedDaily(int day) {return claimedDays.contains(day);}
    public void addClaimedDay(int day) {claimedDays.add(day);}
    public void setClaimedDays(List<Integer> cDays) {claimedDays = new ArrayList<>(cDays);}
    public List<Integer> getClaimedDays() {return new ArrayList<>(claimedDays);}

    public void resetClaimedDays() {
        claimedDays.clear();
        dayStreak = 1;
    }

    public static PlayerData getPlayerData(Player p) {
        if (!playersData.containsKey(p.getUniqueId())) return new PlayerData(p.getUniqueId(), p.getName());
        return playersData.get(p.getUniqueId());
    }
}
