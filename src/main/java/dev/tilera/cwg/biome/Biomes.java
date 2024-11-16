package dev.tilera.cwg.biome;

import dev.tilera.cwg.Config;
import net.minecraft.world.biome.BiomeGenBase;

public class Biomes {
    public static BiomeGenBase classic;

    public static void init() {
        classic = new BiomeClassic(Config.classicBiomeID).setColor(353825).setBiomeName("Classic");
    }
}
