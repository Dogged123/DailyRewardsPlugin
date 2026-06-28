package dogged.dailyRewards.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class CustomConfig {
    private final JavaPlugin plugin;
    private final String fileName;
    private File configFile;
    private FileConfiguration dataConfig;

    public CustomConfig(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;

        if (!fileName.contains(".")) fileName += ".yml";
        this.fileName = fileName;

        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (configFile == null) configFile = new File(plugin.getDataFolder(), fileName);

        dataConfig = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultStream = plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null) {
            reloadConfig();
        }
        return dataConfig;
    }

    public void saveConfig() {
        if (dataConfig == null || this.configFile == null) return;

        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) plugin.saveResource(fileName, false);
    }
}