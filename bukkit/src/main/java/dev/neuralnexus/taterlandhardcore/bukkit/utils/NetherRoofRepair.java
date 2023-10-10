package dev.neuralnexus.taterlandhardcore.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

public class NetherRoofRepair {
    /**
     * Repair the nether roof.
     */
    public static void repairNetherRoof() {
        List<World> netherWorlds = Bukkit.getWorlds().stream().filter(world -> world.getEnvironment().equals(World.Environment.NETHER)).toList();
        netherWorlds.forEach(world -> {
            // Get loaded chunks
            Chunk[] chunks = world.getLoadedChunks();
            for (Chunk chunk : chunks) {
                // Check for non-bedrock blocks at y=127
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        if (chunk.getBlock(x, 127, z).getType() != Material.BEDROCK) {
                            // Set the block to bedrock
                            chunk.getBlock(x, 127, z).setType(Material.BEDROCK);
                        }
                    }
                }
            }
        });
    }
}
