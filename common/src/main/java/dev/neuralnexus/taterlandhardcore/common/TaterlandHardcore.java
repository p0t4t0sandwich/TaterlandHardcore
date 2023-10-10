package dev.neuralnexus.taterlandhardcore.common;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TaterlandHardcore {
    /**
     * Properties of the TaterlandHardcore class.
     * config: The config file
     * logger: The logger
     * singleton: The singleton instance of the TaterlandHardcore class
     * STARTED: Whether the TaterlandHardcore has been started
     */
    private static YamlDocument config;
    private static YamlDocument tempbans;
    private final Object logger;
    private static TaterlandHardcore singleton = null;
    private boolean STARTED = false;

    /**
     * Constructor for the TaterlandHardcore class.
     * @param configPath The path to the config file
     * @param logger The logger
     */
    public TaterlandHardcore(String configPath, Object logger) {
        singleton = this;
        this.logger = logger;

        // Config
        try {
            config = YamlDocument.create(new File("./" + configPath + "/TaterlandHardcore", "config.yml"),
                    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("config.yml"))
            );
            config.reload();
        } catch (IOException | NullPointerException e) {
            useLogger("Failed to load config.yml!\n" + e.getMessage());
            e.printStackTrace();
        }

        // Tempbans
        // TODO: Convert to serializable GSON object
        try {
            tempbans = YamlDocument.create(new File("./" + configPath + "/TaterlandHardcore", "tempbans.yml"),
                    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("tempbans.yml"))
            );
            tempbans.reload();
        } catch (IOException | NullPointerException e) {
            useLogger("Failed to load tempbans.yml!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Getter for the singleton instance of the TaterlandHardcore class.
     * @return The singleton instance
     */
    public static TaterlandHardcore getInstance() {
        return singleton;
    }

    /**
     * Use whatever logger is being used.
     * @param message The message to log
     */
    public void useLogger(String message) {
        if (logger instanceof java.util.logging.Logger) {
            ((java.util.logging.Logger) logger).info(message);
        } else if (logger instanceof org.slf4j.Logger) {
            ((org.slf4j.Logger) logger).info(message);
        } else {
            System.out.println(message);
        }
    }

    /**
     * Start TaterSync
     */
    public void start() {
        if (STARTED) {
            useLogger("TaterlandHardcore has already started!");
            return;
        }
        STARTED = true;

        useLogger("TaterlandHardcore has been started!");
    }

    /**
     * Getter for the ban time.
     * @return The ban time
     */
    public int getBanTime() {
        return (int) config.get("tempban.time");
    }

    /**
     * Getter for the ban message.
     * @return The ban message
     */
    public String getBanMessage() {
        return (String) config.get("tempban.message");
    }

    /**
     * Tempban a player.
     * @param player_uuid The UUID of the player to tempban
     */
    public void tempbanPlayer(String player_uuid) {
        tempbans.set("tempbans." + player_uuid, System.currentTimeMillis() + getBanTime() * 1000L);
        try {
            tempbans.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Unban a player.
     * @param player_uuid The UUID of the player to unban
     */
    public void unbanPlayer(String player_uuid) {
        tempbans.remove("tempbans." + player_uuid);
        try {
            tempbans.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if a player is tempbanned.
     * @param player_uuid The UUID of the player to check
     * @return Whether the player is tempbanned
     */
    public boolean isTempbanned(String player_uuid) {
        // Check player time against current time
        if (tempbans.get("tempbans." + player_uuid) != null) {
            // If the player's time is less than the current time, unban them
            if ((long) tempbans.get("tempbans." + player_uuid) < System.currentTimeMillis()) {
                tempbans.set("tempbans." + player_uuid, null);
                return false;
            }
            return true;
        }
        return false;
    }
}
