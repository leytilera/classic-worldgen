package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.hooks.common.ICavegenHook;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;

public class DefaultCavegenHook implements ICavegenHook {

    @Override
    public MapGenBase createCaveGenerator() {
        return new MapGenCaves();
    }

    @Override
    public MapGenBase createRavineGenerator() {
        return new MapGenRavine();
    }

    @Override
    public String getID() {
        return "cwg:vanilla_cavegen";
    }

    @Override
    public String getDisplayName() {
        return "Vanilla Cavegen";
    }
    
}
