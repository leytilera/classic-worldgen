package dev.tilera.cwg.vanilla;

import dev.tilera.cwg.DelegateChunkManager;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.gen.ChunkProviderGenerate;

public class SingleBiomeChunkManagerFactory implements IChunkManagerFactory {

    @Override
    public AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world) {
        int biomeID = options.getInt("cwg:generator.singleBiome:biomeID");
        return new DelegateChunkManager(
            options, 
            new ChunkProviderGenerate(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled()),  
            new WorldChunkManagerHell(BiomeGenBase.getBiome(biomeID), 1.0f)
            );
    }

    @Override
    public String getID() {
        return "cwg:singleBiome";
    }

    @Override
    public String getDisplayName() {
        return "Single Biome";
    }

    @Override
    public boolean hasSpecificOption(IOption<?> option) {
        return option.getID().startsWith("cwg:generator.singleBiome");
    }
    
}
