package dev.neuralnexus.taterlandhardcore.bukkit.commands;

import dev.neuralnexus.taterlandhardcore.bukkit.BukkitMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.atomic.AtomicBoolean;

import static dev.neuralnexus.taterlandhardcore.common.Utils.runTaskAsync;

public class UnbanCommand implements CommandExecutor {
    BukkitMain plugin = BukkitMain.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AtomicBoolean success = new AtomicBoolean(false);
        runTaskAsync(() -> {
            try {
                // Admin check
                if (!sender.hasPermission("tempbandeath.admin")) {
                    sender.sendMessage("§cYou do not have permission to use this command!");
                    success.set(true);
                    return;
                }

                // Check if the player is tempbanned
                String player_uuid = args[0];
                if (!plugin.taterlandHardcore.isTempbanned(player_uuid)) {
                    sender.sendMessage("§cThis player is not tempbanned!");
                    success.set(true);
                    return;
                }

                // Unban the player
                plugin.taterlandHardcore.unbanPlayer(player_uuid);
                sender.sendMessage("§aSuccessfully unbanned player!");
                success.set(true);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        return success.get();
    }
}
