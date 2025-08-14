package dev.tilera.cwg.api.hooks.common;

import dev.tilera.cwg.api.hooks.IHookProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;

public interface IStructureGenHook extends IHookProvider {

    MapGenStronghold createStrongholdGenerator();
    
    MapGenVillage createVillageGenerator();

    MapGenMineshaft createMineshaftGenerator();

    MapGenScatteredFeature createScatteredFeatureGenerator();

    default void setStructureGen(ChunkProviderGenerate generator) {
        generator.strongholdGenerator = createStrongholdGenerator();
        generator.villageGenerator = createVillageGenerator();
        generator.mineshaftGenerator = createMineshaftGenerator();
        generator.scatteredFeatureGenerator = createScatteredFeatureGenerator();
    }

}
