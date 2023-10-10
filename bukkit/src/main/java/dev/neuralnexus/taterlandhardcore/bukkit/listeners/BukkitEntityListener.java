package dev.neuralnexus.taterlandhardcore.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.google.common.math.IntMath.pow;

/**
 * Bukkit entity listener
 */
public class BukkitEntityListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        // If the damage is from falling
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) event.getEntity();
            int damage = (int) (event.getDamage());
            // TODO: Make this configurable
            int multiplier = 1;
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * damage * multiplier, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * damage * multiplier, 1));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        int damageHearts = (int) (event.getDamage())/2;
        // TODO: Make this configurable
        int threshold = 3;
        if (damageHearts >= threshold) {
            int duration = pow(2, damageHearts - threshold);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * duration, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 20 * duration, 1));
        }
    }
}
