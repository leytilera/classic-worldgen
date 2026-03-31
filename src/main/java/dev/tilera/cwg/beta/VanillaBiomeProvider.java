package dev.tilera.cwg.beta;

import net.minecraft.world.biome.BiomeGenBase;

public class VanillaBiomeProvider implements IBetaBiomeProvider { //TODO: Implement actual beta biomes
    
    @Override
    public BiomeGenBase getPlains() {
        return BiomeGenBase.plains;
    }

    @Override
    public BiomeGenBase getForest() {
        return BiomeGenBase.forest;
    }

    @Override
    public BiomeGenBase getTaiga() {
        return BiomeGenBase.taiga;
    }

    @Override
    public BiomeGenBase getDesert() {
        return BiomeGenBase.desert;
    }

    @Override
    public BiomeGenBase getSwampland() {
        return BiomeGenBase.swampland;
    }

    @Override
    public BiomeGenBase getTundra() {
        return BiomeGenBase.taiga;
    }

    @Override
    public BiomeGenBase getSavanna() {
        return BiomeGenBase.plains;
    }

    @Override
    public BiomeGenBase getShrubland() {
        return BiomeGenBase.plains;
    }

    @Override
    public BiomeGenBase getSeasonalForest() {
        return BiomeGenBase.forest;
    }

    @Override
    public BiomeGenBase getRainforest() {
        return BiomeGenBase.forest;
    }

    @Override
    public String getID() {
        return "cwg:vanilla_biomes";
    }

    @Override
    public String getDisplayName() {
        return "Vanilla Biome Provider";
    }

}
