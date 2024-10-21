package me.ShinyShadow_.Spooky.listeners;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.PKListener;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import com.projectkorra.projectkorra.storage.DBConnection;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import me.ShinyShadow_.Spooky.SpookyElement;
import me.ShinyShadow_.Spooky.Spooky;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public class PKEvents implements Listener {
    private final Spooky plugin = Spooky.plugin;
    private BendingPlayer bPlayer;
    public PKEvents() {

        byte b;
        int i;
        RegisteredListener[] arrayOfRegisteredListener;
        for (i = (arrayOfRegisteredListener = AsyncPlayerChatEvent.getHandlerList().getRegisteredListeners()).length, b = 0; b < i; ) {
            RegisteredListener listener = arrayOfRegisteredListener[b];
            if (listener.getListener().getClass().equals(PKListener.class))
                AsyncPlayerChatEvent.getHandlerList().unregister(listener.getListener());
            b++;
        }
    }
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        // saveElements(bPlayer);
        if (!ConfigManager.languageConfig.get().getBoolean("Chat.Enable") || bPlayer == null)
            return;
        ArrayList<Element> elements = new ArrayList<>(bPlayer.getElements());
        elements.remove(SpookyElement.SPOOKY);



        String name = "Nonbender";
        ChatColor c = ChatColor.WHITE;
        if (bPlayer.hasElement(Element.AVATAR)) {
            c = Element.AVATAR.getColor();
            name = Element.AVATAR.getName();
        }
        if (elements.size() > 0) {
            c = ((Element)elements.get(0)).getColor();
            name = ((Element)elements.get(0)).getName();
        }
        String element = ConfigManager.languageConfig.get().getString("Chat.Prefixes." + name);
        event.setFormat(event.getFormat().replace("{element}", c + element + ChatColor.RESET)
                .replace("{ELEMENT}", c + element + ChatColor.RESET)
                .replace("{elementcolor}", (CharSequence)c.toString()).replace("{ELEMENTCOLOR}", (CharSequence)c.toString()));
        if (bPlayer.hasElement(Element.AVATAR)){
            c = ChatColor.of(ConfigManager.languageConfig.get().getString("Chat.Colors.Avatar"));
            String format = ConfigManager.languageConfig.get().getString("Chat.Format");
            format = format.replace("<message>", "%2$s");
            format = format.replace("<name>", c + player.getDisplayName() + ChatColor.RESET);
            event.setFormat(format);
        }
    }
    @EventHandler
    public void onPKReload(BendingReloadEvent event) {
        final CommandSender sender = event.getSender();

        Bukkit.getScheduler().runTaskLater((Plugin)Spooky.plugin, () -> {

            try {

                Spooky.plugin.getConfig().load(new File(Spooky.plugin.getDataFolder(), "config.yml"));
                HandlerList.unregisterAll((Plugin)Spooky.plugin);
                Bukkit.getPluginManager().registerEvents(new AbilityListener(), (Plugin)Spooky.plugin);
                Bukkit.getPluginManager().registerEvents(new PKEvents(), (Plugin)Spooky.plugin);

                plugin.getLogger().info(ChatColor.BLUE + "Reloaded Spooky v" + Spooky.plugin.getDescription().getVersion() + "!");
                sender.sendMessage(ChatColor.BLACK +  "Spooky config reloaded." + ChatColor.BOLD);


            } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.BLACK +  "Spooky failed config reloaded." + ChatColor.BOLD);
                plugin.getLogger().info((ChatColor.RED + "Failed to load the Spooky config: " + e.getLocalizedMessage()));
            }
        },1L);

    }
    private void saveElements(BendingPlayer bPlayer, CommandSender sender) {
        sender.sendMessage(ChatColor.BLACK +  "Saved." + ChatColor.BOLD);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Spooky.plugin, () -> {
            try {
                sender.sendMessage(ChatColor.BLACK +  "Saved." + ChatColor.BOLD);
                DBConnection.sql.getConnection().setAutoCommit(false);
                DBConnection.sql.getConnection().commit();
                bPlayer.saveElements();
                DBConnection.sql.getConnection().commit();
                DBConnection.sql.getConnection().setAutoCommit(true);

                sender.sendMessage(ChatColor.BLACK +  "Saved." + ChatColor.BOLD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
