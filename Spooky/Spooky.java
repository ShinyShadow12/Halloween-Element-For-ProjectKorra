package me.ShinyShadow_.Spooky;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.util.CollisionInitializer;
import com.sun.security.auth.login.ConfigFile;
import me.ShinyShadow_.Spooky.listeners.AbilityListener;
//import me.ShinyShadow_.Spooky.utilities.SpookyPlaceholder;
import me.ShinyShadow_.Spooky.listeners.PKEvents;
import me.ShinyShadow_.Spooky.utilities.SpookyPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.ShinyShadow_.Spooky.config.Config;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;

public class Spooky extends JavaPlugin {
    public Element SPOOKY;
    public static Spooky plugin;

    public void onEnable() {
        plugin = this;

        SPOOKY = SpookyElement.SPOOKY;;
        CoreAbility.registerPluginAbilities(this, "me.ShinyShadow_.Spooky.ability");
        getServer().getPluginManager().registerEvents((Listener)new PKEvents(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new AbilityListener(), (Plugin)this);


        new Config(this.plugin);

        new ConfigFile();;
        CollisionInitializer collisionInitializer = ProjectKorra.getCollisionInitializer();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            (new SpookyPlaceholder()).register();
            plugin.getLogger().info("Successfully enabled Spooky bending.");
        }
    }

    public void onDisable() {

    }

    public static Spooky getInstance() {
        return plugin;
    }


}
