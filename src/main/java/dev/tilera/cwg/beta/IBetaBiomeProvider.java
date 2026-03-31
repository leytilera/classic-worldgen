package dev.tilera.cwg.beta;

import dev.tilera.cwg.api.hooks.IHookProvider;
import net.minecraft.world.biome.BiomeGenBase;

public interface IBetaBiomeProvider extends IHookProvider {
    
    BiomeGenBase getPlains();

    BiomeGenBase getForest();

    BiomeGenBase getTaiga();

    BiomeGenBase getDesert();

    BiomeGenBase getSwampland();

    BiomeGenBase getTundra();

    BiomeGenBase getSavanna();

    BiomeGenBase getShrubland();

    BiomeGenBase getSeasonalForest();

    BiomeGenBase getRainforest();

}
