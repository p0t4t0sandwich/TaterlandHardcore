package dev.neuralnexus.taterlandhardcore.bukkit.listeners;

import dev.neuralnexus.taterlandhardcore.bukkit.BukkitMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Bukkit player listener
 */
public class BukkitPlayerListener implements Listener {
    BukkitMain plugin = BukkitMain.getInstance();

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
}
