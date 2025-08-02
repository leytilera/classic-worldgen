package dev.tilera.cwg.hooks;

import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;

public class DefaultStructureGenHook implements IStructureGenHook {

    @Override
    public String getID() {
        return "cwg:vanilla_structuregen";
    }

    @Override
    public MapGenStronghold createStrongholdGenerator() {
        return new MapGenStronghold();
    }

    @Override
    public MapGenVillage createVillageGenerator() {
        return new MapGenVillage();
    }

    @Override
    public MapGenMineshaft createMineshaftGenerator() {
        return new MapGenMineshaft();
    }

    @Override
    public MapGenScatteredFeature createScatteredFeatureGenerator() {
        return new MapGenScatteredFeature();
    }

    @Override
    public String getDisplayName() {
        return "Vanilla Structuregen";
    }
    
}
