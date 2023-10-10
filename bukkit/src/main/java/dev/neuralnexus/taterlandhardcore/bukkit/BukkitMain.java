package dev.neuralnexus.taterlandhardcore.bukkit;

import dev.neuralnexus.taterlandhardcore.bukkit.commands.UnbanCommand;
import dev.neuralnexus.taterlandhardcore.bukkit.listeners.BukkitEntityListener;
import dev.neuralnexus.taterlandhardcore.bukkit.listeners.BukkitPlayerListener;
import dev.neuralnexus.taterlandhardcore.common.TaterlandHardcore;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.neuralnexus.taterlandhardcore.common.Utils.*;

public final class BukkitMain extends JavaPlugin {
    public TaterlandHardcore taterlandHardcore;

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

        getLogger().info("TaterlandHardcore is running on " + getServerType() + ".");

        // Start TaterlandHardcore
        taterlandHardcore = new TaterlandHardcore("plugins", getLogger());
        taterlandHardcore.start();

        // Register event listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BukkitPlayerListener(), this);
        pm.registerEvents(new BukkitEntityListener(), this);

        // Register commands
        getCommand("untempban").setExecutor(new UnbanCommand());

        // Plugin enable message
        getLogger().info("TaterlandHardcore has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("TaterlandHardcore has been disabled!");
    }
}
