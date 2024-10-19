package me.ShinyShadow_.Spooky.config;

import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.ShinyShadow_.Spooky.Spooky;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private static Spooky plugin;

    public Config(Spooky plugin) {
        Config.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = Spooky.plugin.getConfig();
        config.addDefault("Abilities.Spooky.Scarecrow", Integer.valueOf(5000));
        ConfigManager.languageConfig.save();
        config.options().copyDefaults(true);
        Spooky.plugin.saveConfig();
    }
}