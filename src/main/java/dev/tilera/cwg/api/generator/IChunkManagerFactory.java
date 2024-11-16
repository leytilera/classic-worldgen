package dev.tilera.cwg.api.generator;

import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import net.minecraft.world.World;

public interface IChunkManagerFactory {
    
    AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world);

    String getID();

    String getDisplayName();
    
    boolean hasSpecificOption(IOption<?> option);

}
