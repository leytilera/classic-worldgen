package dev.tilera.cwg.vanilla;

import dev.tilera.cwg.DelegateChunkManager;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.hooks.ICavegenHook;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.ChunkProviderGenerate;

public class VanillaChunkManagerFactory implements IChunkManagerFactory {

    @Override
    public AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world) {
        ICavegenHook hook = options.getValue("cwg:cavegen_hook", IHookProvider.class).getHook(ICavegenHook.class);
        ChunkProviderGenerate generator = new ChunkProviderGenerate(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
        hook.setCavegen(generator);
        return new DelegateChunkManager(
            options, 
            generator, 
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
