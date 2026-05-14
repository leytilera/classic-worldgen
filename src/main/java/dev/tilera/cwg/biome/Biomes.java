package dev.tilera.cwg.biome;

import dev.tilera.cwg.Config;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class Biomes {
    public static BiomeGenBase classic;
    public static BiomeGenBase betaForest;
    public static BiomeGenBase betaTaiga;
    public static BiomeGenBase tundra;
    public static BiomeGenBase seasonalForest;
    public static BiomeGenBase rainforest;
    public static BiomeGenBase betaSavanna;
    public static BiomeGenBase betaSwampland;
    public static BiomeGenBase shrubland;

    public static void init() {
        classic = new BiomeClassic(Config.classicBiomeID).setColor(353825).setBiomeName("Classic");
        betaForest = new BiomeGenBeta(Config.betaForestBiomeID, BetaBiomeType.FOREST).setColor(353825).setBiomeName("Beta Forest").setTemperatureRainfall(0.8F, 0.6F);
        betaTaiga = new BiomeGenBeta(Config.betaTaigaBiomeID, BetaBiomeType.TAIGA).setColor(3060051).setBiomeName("Beta Taiga").setTemperatureRainfall(0.1F, 0.35F).setEnableSnow();
        tundra = new BiomeGenBeta(Config.tundraBiomeID, BetaBiomeType.TUNDRA).setColor(16777215).setBiomeName("Tundra").setTemperatureRainfall(0.1F, 0.1F).setEnableSnow();
        seasonalForest = new BiomeGenBeta(Config.seasonalForestBiomeID, BetaBiomeType.SEASONAL_FOREST).setColor(10215459).setBiomeName("Seasonal Forest").setTemperatureRainfall(0.95F, 0.7F);
        rainforest = new BiomeGenBeta(Config.rainforestBiomeID, BetaBiomeType.RAINFOREST).setColor(588342).setBiomeName("Rainforest").setTemperatureRainfall(0.95F, 0.95F);
        betaSavanna = new BiomeGenBeta(Config.betaSavannaBiomeID, BetaBiomeType.SAVANNA).setColor(14278691).setBiomeName("Savanna").setTemperatureRainfall(0.7F, 0.1F);
        betaSwampland = new BiomeGenBeta(Config.betaSwamplandBiomeID, BetaBiomeType.SWAMPLAND).setColor(522674).setBiomeName("Beta Swampland").setTemperatureRainfall(0.55F, 0.65F);
        shrubland = new BiomeGenBeta(Config.shrublandBiomeID, BetaBiomeType.SHRUBLAND).setColor(10595616).setBiomeName("Shrubland").setTemperatureRainfall(0.7F, 0.3F);

        BiomeDictionary.registerBiomeType(classic, Type.PLAINS);
        BiomeDictionary.registerBiomeType(betaForest, Type.FOREST);
        BiomeDictionary.registerBiomeType(betaTaiga, Type.COLD);
        BiomeDictionary.registerBiomeType(tundra, Type.COLD);
        BiomeDictionary.registerBiomeType(seasonalForest, Type.FOREST);
        BiomeDictionary.registerBiomeType(rainforest, Type.FOREST);
        BiomeDictionary.registerBiomeType(betaSavanna, Type.HOT, Type.SAVANNA);
        BiomeDictionary.registerBiomeType(betaSwampland, Type.SWAMP);
        BiomeDictionary.registerBiomeType(shrubland, Type.HOT);
    }
}
