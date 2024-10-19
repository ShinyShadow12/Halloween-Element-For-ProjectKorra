package me.ShinyShadow_.Spooky;

import com.projectkorra.projectkorra.Element;

import com.projectkorra.projectkorra.configuration.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;

import org.bukkit.plugin.Plugin;


public class SpookyElement extends Element{
    public static final SpookyElement SPOOKY = new SpookyElement("Spooky", ChatColor.DARK_GRAY , "Spooky", 5555555);

    private ChatColor defaultColor;

    private String configName;

    private int dust;

    private Color dustColor;

    public SpookyElement(String name, ChatColor defaultColor, String configName, int dustColor) {
        super(name, Element.ElementType.NO_SUFFIX, (Plugin)Spooky.getInstance());
        this.defaultColor = ChatColor.GOLD;
        this.configName = configName;
        this.dust = dustColor;
        this.dustColor = Color.fromRGB(dustColor);

    }

    public ChatColor getColor() {
        String color = ConfigManager.languageConfig.get().getString("Chat.Colors." + getName());
        return (color != null) ? ChatColor.of(color) : getDefaultColor();
    }

    public ChatColor getSubColor() {
        String color = ConfigManager.languageConfig.get().getString("Chat.Colors." + getColor() + "Sub");
        return (color != null) ? ChatColor.of(color) : ChatColor.WHITE;
    }

    public ChatColor getDefaultColor() {
        return this.defaultColor;
    }

    public int getDustHexColor() {
        return this.dust;
    }

    public String getConfigName() {
        return this.configName;
    }

    public Color getDustColor() {
        return this.dustColor;
    }
}
