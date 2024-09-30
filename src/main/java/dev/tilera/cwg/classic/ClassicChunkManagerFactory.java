package dev.tilera.cwg.classic;

import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import net.minecraft.world.World;

public class ClassicChunkManagerFactory implements IChunkManagerFactory {

    @Override
    public WorldChunkManagerClassic createChunkManager(IGeneratorOptionProvider options, World world) {
        return new WorldChunkManagerClassic(world, options);
    }

    @Override
    public String getID() {
        return "cwg:classic";
    }

    @Override
    public String getDisplayName() {
        return "Classic";
    }
    
    @Override
    public boolean hasSpecificOption(IOption<?> option) {
        return option.getID().startsWith("cwg:generator.classic");
    }

}
