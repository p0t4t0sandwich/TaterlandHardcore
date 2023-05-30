package ca.sperrer.p0t4t0sandwich.tempbandeath.bukkit;

import ca.sperrer.p0t4t0sandwich.tempbandeath.bukkit.commands.UnbanCommand;
import ca.sperrer.p0t4t0sandwich.tempbandeath.bukkit.listeners.BukkitEventListener;
import ca.sperrer.p0t4t0sandwich.tempbandeath.common.TempBanDeath;
import org.bukkit.plugin.java.JavaPlugin;

import static ca.sperrer.p0t4t0sandwich.tempbandeath.common.Utils.*;

public final class BukkitMain extends JavaPlugin {
    public TempBanDeath tempBanDeath;

    // Singleton instance
    private static BukkitMain instance;
    public static BukkitMain getInstance() {
        return instance;
    }

    public String getServerType() {
        if (isMagma()) {
            return "Magma";
        } else if (isMohist()) {
            return "Mohist";
        } else if (isArclight()) {
            return "Arclight";
        } else if (isFolia()) {
            return "Folia";
        } else if (isPaper()) {
            return "Paper";
        } else if (isSpigot()) {
            return "Spigot";
        } else if (isCraftBukkit()) {
            return "CraftBukkit";
        } else {
            return "Unknown";
        }
    }

    @Override
    public void onEnable() {
        // Singleton instance
        instance = this;

        getLogger().info("TempBanDeath is running on " + getServerType() + ".");

        // Start TempBanDeath
        tempBanDeath = new TempBanDeath("plugins", getLogger());
        tempBanDeath.start();

        // Register event listener
        getServer().getPluginManager().registerEvents(new BukkitEventListener(), this);

        // Register commands
        getCommand("untempban").setExecutor(new UnbanCommand());

        // Plugin enable message
        getLogger().info("TempBanDeath has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("TempBanDeath has been disabled!");
    }
}
