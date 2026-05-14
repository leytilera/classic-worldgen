package dev.tilera.cwg.beta;

import dev.tilera.cwg.biome.Biomes;
import net.minecraft.world.biome.BiomeGenBase;

public class BetaBiomeProvider implements IBetaBiomeProvider {
    
    @Override
    public BiomeGenBase getPlains() {
        return BiomeGenBase.plains;
    }

    @Override
    public BiomeGenBase getForest() {
        return Biomes.betaForest;
    }

    @Override
    public BiomeGenBase getTaiga() {
        return Biomes.betaTaiga;
    }

    @Override
    public BiomeGenBase getDesert() {
        return BiomeGenBase.desert;
    }

    @Override
    public BiomeGenBase getSwampland() {
        return Biomes.betaSwampland;
    }

    @Override
    public BiomeGenBase getTundra() {
        return Biomes.tundra;
    }

    @Override
    public BiomeGenBase getSavanna() {
        return Biomes.betaSavanna;
    }

    @Override
    public BiomeGenBase getShrubland() {
        return Biomes.shrubland;
    }

    @Override
    public BiomeGenBase getSeasonalForest() {
        return Biomes.seasonalForest;
    }

    @Override
    public BiomeGenBase getRainforest() {
        return Biomes.rainforest;
    }

    @Override
    public String getID() {
        return "cwg:beta_biomes";
    }

    @Override
    public String getDisplayName() {
        return "Beta Biome Provider";
    }

}
