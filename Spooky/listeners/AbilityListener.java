package me.ShinyShadow_.Spooky.listeners;


import com.projectkorra.projectkorra.BendingPlayer;

import com.projectkorra.projectkorra.ability.CoreAbility;
import me.ShinyShadow_.Spooky.Spooky;
import me.ShinyShadow_.Spooky.ability.spookyability.ScareCrow;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityListener implements Listener {
    private Player player;
    private Player hitPlayer;
    private Block scareCrowPos;
    //public int scareCrowCount = 0;
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if(event.getAction() == Action.LEFT_CLICK_BLOCK && !player.isSneaking()) {

            if(bPlayer.canBend(CoreAbility.getAbility(ScareCrow.class)) && bPlayer.getBoundAbilityName().equalsIgnoreCase("ScareCrow") && !ScareCrow.test) {
                    scareCrowPos = event.getClickedBlock();
                    new ScareCrow(player, scareCrowPos);
            }
        }
            //End the ability without it getting triggered so you can replace it
        else if(event.getAction() == Action.LEFT_CLICK_BLOCK && player.isSneaking()) {
                ScareCrow.stop()
        }
    }
    //Prevent pumpkin from being broken
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
            event.getPlayer()
                if(event.getBlock().getLocation().equals(ScareCrow.pumpkinspawn.getLocation())) {
                    event.setCancelled(true)
            }
    }
    
}
