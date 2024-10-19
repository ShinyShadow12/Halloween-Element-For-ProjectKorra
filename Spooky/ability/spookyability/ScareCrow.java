package me.ShinyShadow_.Spooky.ability.spookyability;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.MovementHandler;
import me.ShinyShadow_.Spooky.SpookyElement;
import me.ShinyShadow_.Spooky.ability.api.SpookyAbility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ScareCrow extends SpookyAbility {
    public Block scareCrowPosition;
    public Block pumpkinSpawn;
    public Boolean pumpkinExists = false;
    public Boolean jumpscare = false;
    public static Boolean test = false;
    public Location playerLoc;
    public double radius = 0.8;

    public ScareCrow(Player player, Block scareCrowPos) {
        super(player);
       test = true;
        scareCrowPosition = scareCrowPos;

        start();
    }

    @Override
    public void progress() {
        playerLoc = player.getLocation();

        if(player.getWorld() != scareCrowPosition.getWorld()) {
            stop();
        }

        if (bPlayer.canBend(this) && !pumpkinExists && !jumpscare) {
            pumpkinSpawn = player.getWorld().getBlockAt(scareCrowPosition.getLocation().add(0, 1, 0));
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, pumpkinSpawn.getLocation().add(0.5, 0.1,0.5), 1, 0, 0.2, 0, 2);

            new BukkitRunnable() {
                @Override
                public void run() {
                    pumpkinSpawn.setType(Material.PUMPKIN);
                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, pumpkinSpawn.getLocation().add(0.5, 0.1,0.5), 1, 0.5, 0, 0.5, 2);
                    pumpkinExists = true;
                }
            }.runTaskLater(SpookyElement.SPOOKY.getPlugin(), 30L); // 5 seconds delay
        }
        if (bPlayer.canBendIgnoreBindsCooldowns(this)) {
            if(pumpkinExists && !jumpscare) {
                for (Entity target : GeneralMethods.getEntitiesAroundPoint(pumpkinSpawn.getLocation(), 3)) {
                    if (target instanceof LivingEntity && !target.getUniqueId().equals(this.player.getUniqueId())) {
                        pumpkinSpawn.getLocation().add(0, 0, 0).getBlock().setType(Material.AIR);
                        pumpkinSpawn.getLocation().add(0, 4, 0).getBlock().setType(Material.CARVED_PUMPKIN);
                        for (int i = 0; i <= 6; i++) {
                            player.getWorld().playSound(pumpkinSpawn.getLocation(), Sound.ENTITY_PHANTOM_BITE, 2.0F, 1.0F);
                            player.getWorld().playSound(pumpkinSpawn.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 2.0F, 0.6F);
                            player.getWorld().playSound(pumpkinSpawn.getLocation(), Sound.ENTITY_ENDERMAN_DEATH, 2.0F, 0.25F);
                        }
                        player.getWorld().playSound(pumpkinSpawn.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 0.65F);
                        player.getWorld().spawnParticle(Particle.REDSTONE, pumpkinSpawn.getLocation().add(0.5, 2, 0.5),
                                300, 2, 3, 2, 0, new Particle.DustOptions(org.bukkit.Color.fromRGB(62, 8, 75), 1));
                        player.getWorld().spawnParticle(Particle.REDSTONE, pumpkinSpawn.getLocation().add(0.5, 2, 0.5),
                                300, 2, 3, 2, 0, new Particle.DustOptions(org.bukkit.Color.fromRGB(84, 70, 87), 1));
                        jumpscare = true;

                        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0));
                    final MovementHandler mh = new MovementHandler((LivingEntity) target, this);

                     DamageHandler.damageEntity(target, 4, this);
                    mh.stopWithDuration(15, "**SCARED**");
                    }

                }
            }

            if (jumpscare) {
                //Arms
                player.getWorld().spawnParticle(Particle.REDSTONE, pumpkinSpawn.getLocation().add(0.5, 3.1, 0.5),
                        45, 1.2, 0.2, 0, 0, new Particle.DustOptions(org.bukkit.Color.fromRGB(183, 119, 1), 1));
                //Body
                player.getWorld().spawnParticle(Particle.REDSTONE, pumpkinSpawn.getLocation().add(0.5, 2.2, 0.5),
                        45, 0.22, 0.9, 0.22, 0, new Particle.DustOptions(org.bukkit.Color.fromRGB(75, 134, 0), 1));

                //Pants
                player.getWorld().spawnParticle(Particle.REDSTONE, pumpkinSpawn.getLocation().add(0.5, 1, 0.5),
                        45, 0.25, 0.5, 0.25, 0, new Particle.DustOptions(org.bukkit.Color.fromRGB(1, 128, 178), 1));
               //Hat
                for (int d = 0; d <= 30 + (30*radius); d += 1) {
                Location particleLoc = new Location(player.getWorld(), pumpkinSpawn.getLocation().getX(),pumpkinSpawn.getLocation().getY(),pumpkinSpawn.getLocation().getZ());
                particleLoc.setX(pumpkinSpawn.getLocation().getX() + Math.cos(d) * radius);
                particleLoc.setZ(pumpkinSpawn.getLocation().getZ() + Math.sin(d) * radius);
                player.getWorld().spawnParticle(Particle.REDSTONE, particleLoc.add(0.5, 5, 0.5),
                        1, 0.2, 0, 0.2, 0.1, new Particle.DustOptions(org.bukkit.Color.fromRGB(115, 65, 6), 1));
                }
                player.getWorld().spawnParticle(Particle.REDSTONE, pumpkinSpawn.getLocation().add(0.5, 6, 0.5),
                        20, 0.35, 0.65, 0.35, 0.1, new Particle.DustOptions(org.bukkit.Color.fromRGB(115, 65, 6), 1));

               //area effect
                player.getWorld().spawnParticle(Particle.REDSTONE, pumpkinSpawn.getLocation().add(0.5, 2, 0.5),
                        20, 2, 3, 2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(62, 8, 75), 1));
                player.getWorld().spawnParticle(Particle.REDSTONE, pumpkinSpawn.getLocation().add(0.5, 2, 0.5),
                        20, 2, 3, 2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(84, 70, 87), 1));
                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, pumpkinSpawn.getLocation().add(0.5, 2, 0.5),
                        20, 2, 3, 2, 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {

                        stop();
                    }
                }.runTaskLater(SpookyElement.SPOOKY.getPlugin(), 40L);
            }
        }
        }

    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 5000;
    }

    @Override
    public String getName() {
        return "ScareCrow";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {


    }

    @Override
    public void stop() {
        pumpkinSpawn.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);
        pumpkinSpawn.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
        pumpkinSpawn.getLocation().add(0, 0, 0).getBlock().setType(Material.AIR);
        getCooldown();
        bPlayer.addCooldown(this);
        test = false;
        remove();
    }

    @Override
    public String getAuthor() {
        return "ShinyShadow_";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
    @Override
    public String getDescription() {

        return  ChatColor.BOLD + " Left click a block to grow an apparently normal pumpkin " +
                "when an entity gets close enough to it, a scarecrow will be summoned in place of " +
                "the pumpkin jumpscaring everyone in the radius. "  + ChatColor.GOLD;
    }

}







