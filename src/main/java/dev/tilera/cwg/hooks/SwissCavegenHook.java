package dev.tilera.cwg.hooks;

import dev.tilera.cwg.caves.MapGenCavesSwiss;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenRavine;

public class SwissCavegenHook implements ICavegenHook {

    @Override
    public MapGenBase createCaveGenerator() {
        System.out.println("ALECALECALEC");
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
    
}
