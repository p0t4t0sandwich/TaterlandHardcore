package dev.neuralnexus.taterlandhardcore.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Bukkit entity listener
 */
public class BukkitEntityListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        // If the entity is a player and the damage is from falling
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) event.getEntity();
            int damage = (int) (event.getDamage());
            int multiplier = 1;
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * damage * multiplier, 1));
        }
    }
}
