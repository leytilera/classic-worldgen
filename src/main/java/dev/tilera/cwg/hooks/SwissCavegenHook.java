package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.hooks.common.ICavegenHook;
import dev.tilera.cwg.caves.MapGenCavesSwiss;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenRavine;

public class SwissCavegenHook implements ICavegenHook {

    @Override
    public MapGenBase createCaveGenerator() {
        return new MapGenCavesSwiss();
    }

    @Override
    public MapGenBase createRavineGenerator() {
        return new MapGenRavine();
    }

    @Override
    public String getID() {
        return "cwg:swiss_cavegen";
    }

    @Override
    public String getDisplayName() {
        return "Default Cavegen";
    }
    
}
