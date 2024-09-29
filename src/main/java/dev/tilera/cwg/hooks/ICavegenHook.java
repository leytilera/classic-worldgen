package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.hooks.IHookProvider;
import net.minecraft.world.gen.MapGenBase;

public interface ICavegenHook extends IHookProvider {
    
    MapGenBase createCaveGenerator();

    MapGenBase createRavineGenerator();

}
