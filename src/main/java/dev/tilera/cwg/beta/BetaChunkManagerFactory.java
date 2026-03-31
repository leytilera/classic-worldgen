package dev.tilera.cwg.beta;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import net.minecraft.world.World;

public class BetaChunkManagerFactory implements IChunkManagerFactory {

    @Override
    public String getID() {
        return "cwg:beta";
    }

    @Override
    public String getDisplayName() {
        return "Beta 1.7.3";
    }

    @Override
    public AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world) {
        return new BetaChunkManager(world, options);
    }

    @Override
    public boolean hasSpecificOption(IOption<?> option) {
        return false;
    }
    
}
