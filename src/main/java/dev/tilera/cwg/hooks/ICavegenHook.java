package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.hooks.IHookProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.MapGenBase;

public interface ICavegenHook extends IHookProvider {
    
    MapGenBase createCaveGenerator();

    MapGenBase createRavineGenerator();

    default void setCavegen(ChunkProviderGenerate generator) {
        generator.caveGenerator = createCaveGenerator();
        generator.ravineGenerator = createRavineGenerator();
    }

}
