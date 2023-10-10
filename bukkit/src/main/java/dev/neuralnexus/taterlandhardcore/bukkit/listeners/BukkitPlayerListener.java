package dev.neuralnexus.taterlandhardcore.bukkit.listeners;

import dev.neuralnexus.taterlandhardcore.bukkit.BukkitTaterlandHardcorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

/**
 * Bukkit player listener
 */
public class BukkitPlayerListener implements Listener {
    BukkitTaterlandHardcorePlugin plugin = BukkitTaterlandHardcorePlugin.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
//        runTaskAsync(() -> {
            try {
                // Check if the player is tempbanned
                String player_uuid = event.getPlayer().getUniqueId().toString();
                if (plugin.taterlandHardcore.isTempbanned(player_uuid)) {
                    // Kick the player
                    event.getPlayer().kickPlayer("§cYou are tempbanned!");
                }
            } catch (Exception e) {
                System.err.println(e);
            }
//        });
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
//        runTaskAsync(() -> {
            try {
                // Admin check
                if (event.getEntity().hasPermission("tempbandeath.admin")) {
                    plugin.getLogger().info("Player has admin permission, not tempbanning.");
                    return;
                }

                // Tempban the player
                String player_uuid = event.getEntity().getUniqueId().toString();
                plugin.taterlandHardcore.tempbanPlayer(player_uuid);

                event.getEntity().kickPlayer(plugin.taterlandHardcore.getBanMessage());
            } catch (Exception e) {
                System.err.println(e);
            }
//        });
    }


    private ArrayList<String> playersOnRoof = new ArrayList<>();
    /**
     * Nether roof protection
     * @param event The event
     */
    @EventHandler
    public void onPlayerMoveOnNetherRoof(PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        if (event.getPlayer().getWorld().getEnvironment().equals(World.Environment.NETHER) && event.getTo().getY() >= 127) {
            if (playersOnRoof.contains(event.getPlayer().getUniqueId().toString())) return;
            Player player = event.getPlayer();
            playersOnRoof.add(player.getUniqueId().toString());

            // Send warning title message
            player.sendTitle("§cWARNING", "§fYou are on the nether roof, you have §e45 seconds§f to leave!", 10, 70, 20);

            // Schedule a task to check if the player is still on the roof
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (player.getLocation().getY() >= 127) {
                    // Give debuffs
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 60 * 5, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 60 * 5, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 60 * 5, 1));
                }
            }, 45 * 20);
        } else {
            playersOnRoof.remove(event.getPlayer().getUniqueId().toString());
        }
    }

    /**
     * Milk bucket protection for the nether roof
     * @param event The event
     */
    @EventHandler
    public void onPlayerDrinkMilk(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.MILK_BUCKET) return;
        if (event.getPlayer().getWorld().getEnvironment().equals(World.Environment.NETHER) && event.getPlayer().getLocation().getY() >= 127) {
            event.getPlayer().sendMessage("§cYou cannot drink milk on the nether roof!");
            event.setCancelled(true);
        }
    }
}
