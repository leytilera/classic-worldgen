package dev.tilera.cwg.beta;

import dev.tilera.cwg.api.hooks.common.ICavegenHook;
import net.minecraft.world.gen.MapGenBase;

public class BetaCavegenHook implements ICavegenHook {

    @Override
    public String getID() {
        return "cwg:beta_caves";
    }

    @Override
    public String getDisplayName() {
        return "Beta Caves";
    }

    @Override
    public MapGenBase createCaveGenerator() {
        return new BetaCaveGenerator();
    }

    @Override
    public MapGenBase createRavineGenerator() {
        return new MapGenBase();
    }
    
}
