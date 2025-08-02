package dev.tilera.cwg.api.generator;

import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import net.minecraft.world.World;

public interface IChunkManagerFactory extends IHookProvider {
    
    AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world);
    
    boolean hasSpecificOption(IOption<?> option);

}
