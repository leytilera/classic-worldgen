package dev.tilera.cwg.infdev;

import dev.tilera.cwg.DelegateChunkManager;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.biome.Biomes;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class InfdevChunkManagerFactory implements IChunkManagerFactory {

    @Override
    public AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world) {
        return new DelegateChunkManager(
            options,
            new ChunkGeneratorInfdev(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), options),
            new WorldChunkManagerHell(Biomes.classic, 0.5f)
        );
    }

    @Override
    public String getID() {
        return "cwg:infdev";
    }

    @Override
    public String getDisplayName() {
        return "Infdev";
    }

    @Override
    public boolean hasSpecificOption(IOption<?> option) {
        return option.getID().equals("cwg:generator.classic:enableModdedWorldgen");
    }
    
}
