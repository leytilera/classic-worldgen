package dev.tilera.cwg.classic;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.genlayer.GenLayerAddIslandClassic;
import dev.tilera.cwg.genlayer.GenLayerAddSnowClassic;
import dev.tilera.cwg.genlayer.GenLayerBiomeClassic;
import dev.tilera.cwg.genlayer.GenLayerFuzzyZoomClassic;
import dev.tilera.cwg.genlayer.GenLayerHillsClassic;
import dev.tilera.cwg.genlayer.GenLayerRiverClassic;
import dev.tilera.cwg.genlayer.GenLayerRiverInitClassic;
import dev.tilera.cwg.genlayer.GenLayerRiverMixClassic;
import dev.tilera.cwg.genlayer.GenLayerShoreClassic;
import dev.tilera.cwg.genlayer.GenLayerSwampRivers;
import dev.tilera.cwg.genlayer.GenLayerVoronoiZoomClassic;
import dev.tilera.cwg.genlayer.GenLayerZoomClassic;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerSmooth;

public class WorldChunkManagerClassic extends AbstractChunkManager{

   private IGeneratorOptionProvider provider = null;
    
    public WorldChunkManagerClassic(World world, IGeneratorOptionProvider provider) {
        super();
        this.provider = provider;
        GenLayer[] agenlayer = initializeAllBiomeGenerators(world.getSeed(), world.getWorldInfo().getTerrainType());
        agenlayer = getModdedBiomeGenerators(world.getWorldInfo().getTerrainType(), world.getSeed(), agenlayer);
        ObfuscationReflectionHelper.setPrivateValue(WorldChunkManager.class, this, agenlayer[0], "genBiomes", "field_76944_d");
        ObfuscationReflectionHelper.setPrivateValue(WorldChunkManager.class, this, agenlayer[1], "biomeIndexLayer", "field_76945_e");
    }

    public GenLayer[] initializeAllBiomeGenerators(long p_75901_0_, WorldType p_75901_2_) {
      assert this.provider != null;
        GenLayerIsland genlayerisland = new GenLayerIsland(1L);
        GenLayerFuzzyZoomClassic genlayerfuzzyzoom = new GenLayerFuzzyZoomClassic(2000L, genlayerisland);
        GenLayerAddIslandClassic genlayeraddisland = new GenLayerAddIslandClassic(1L, genlayerfuzzyzoom);
        GenLayerZoomClassic genlayerzoom = new GenLayerZoomClassic(2001L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIslandClassic(2L, genlayerzoom);
        GenLayerAddSnowClassic genlayeraddsnow = new GenLayerAddSnowClassic(2L, genlayeraddisland);
        genlayerzoom = new GenLayerZoomClassic(2002L, genlayeraddsnow);
        genlayeraddisland = new GenLayerAddIslandClassic(3L, genlayerzoom);
        genlayerzoom = new GenLayerZoomClassic(2003L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIslandClassic(4L, genlayerzoom);
        GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland);
        byte b0 = 4;
        if (p_75901_2_ == WorldType.LARGE_BIOMES) {
           b0 = 6;
        }
  
        b0 = GenLayer.getModdedBiomeSize(p_75901_2_, b0);
        GenLayer genlayer = GenLayerZoomClassic.magnify(1000L, genlayeraddmushroomisland, 0);
        GenLayerRiverInitClassic genlayerriverinit = new GenLayerRiverInitClassic(100L, genlayer);
        genlayer = GenLayerZoomClassic.magnify(1000L, genlayerriverinit, b0 + 2);
        GenLayerRiverClassic genlayerriver = new GenLayerRiverClassic(1L, genlayer);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
        GenLayer genlayer1 = GenLayerZoomClassic.magnify(1000L, genlayeraddmushroomisland, 0);
        GenLayerBiomeClassic genlayerbiome = new GenLayerBiomeClassic(200L, genlayer1, this.provider);
        genlayer1 = GenLayerZoomClassic.magnify(1000L, genlayerbiome, 2);
        GenLayer object = new GenLayerHillsClassic(1000L, genlayer1);
  
        for(int j = 0; j < b0; ++j) {
           object = new GenLayerZoomClassic((long)(1000 + j), object);
           if (j == 0) {
              object = new GenLayerAddIslandClassic(3L, object);
           }
  
           if (j == 1) {
              object = new GenLayerShoreClassic(1000L, object);
           }
  
           if (j == 1) {
              object = new GenLayerSwampRivers(1000L, object);
           }
        }
  
        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, (GenLayer)object);
        GenLayerRiverMixClassic genlayerrivermix = new GenLayerRiverMixClassic(100L, genlayersmooth1, genlayersmooth);
        GenLayerVoronoiZoomClassic genlayervoronoizoom = new GenLayerVoronoiZoomClassic(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(p_75901_0_);
        genlayervoronoizoom.initWorldGenSeed(p_75901_0_);
        return new GenLayer[]{genlayerrivermix, genlayervoronoizoom, genlayerrivermix};
    }

   @Override
   public IChunkProvider getGenerator(World world) {
      return new ChunkProviderClassic(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
   }

   @Override
   public IGeneratorOptionProvider getOptionProvider() {
      return this.provider;
   }   

}
