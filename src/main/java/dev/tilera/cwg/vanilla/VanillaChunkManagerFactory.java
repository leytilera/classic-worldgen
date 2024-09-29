package dev.tilera.cwg.vanilla;

import dev.tilera.cwg.DelegateChunkManager;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.ChunkProviderGenerate;

public class VanillaChunkManagerFactory implements IChunkManagerFactory {

    @Override
    public AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world) {
        return new DelegateChunkManager(
            options, 
            new ChunkProviderGenerate(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled()), 
            new WorldChunkManager(world)
        );
    }

    @Override
    public String getID() {
        return "cwg:vanilla";
    }

    @Override
    public String getDisplayName() {
        return "Vanilla";
    }

    @Override
    public boolean hasSpecificOption(IOption<?> option) {
        return false;
    }
    
}
