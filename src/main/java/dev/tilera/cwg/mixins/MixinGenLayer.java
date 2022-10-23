package dev.tilera.cwg.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import dev.tilera.cwg.Config;
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
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.GenLayerEdge.Mode;


@Mixin(GenLayer.class)
public abstract class MixinGenLayer {
    
    /**
     * @author tilera
     * @reason legacy gen layers
     */
    @Overwrite
    public static GenLayer[] initializeAllBiomeGenerators(long p_75901_0_, WorldType p_75901_2_) {
      if (Config.classicWorldGen) {
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
      GenLayerBiomeClassic genlayerbiome = new GenLayerBiomeClassic(200L, genlayer1, p_75901_2_);
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
   } else {
      boolean flag = false;
      GenLayerIsland genlayerisland = new GenLayerIsland(1L);
      GenLayerFuzzyZoom genlayerfuzzyzoom = new GenLayerFuzzyZoom(2000L, genlayerisland);
      GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayerfuzzyzoom);
      GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
      genlayeraddisland = new GenLayerAddIsland(2L, genlayerzoom);
      genlayeraddisland = new GenLayerAddIsland(50L, genlayeraddisland);
      genlayeraddisland = new GenLayerAddIsland(70L, genlayeraddisland);
      GenLayerRemoveTooMuchOcean genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland);
      GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, genlayerremovetoomuchocean);
      genlayeraddisland = new GenLayerAddIsland(3L, genlayeraddsnow);
      GenLayerEdge genlayeredge = new GenLayerEdge(2L, genlayeraddisland, Mode.COOL_WARM);
      genlayeredge = new GenLayerEdge(2L, genlayeredge, Mode.HEAT_ICE);
      genlayeredge = new GenLayerEdge(3L, genlayeredge, Mode.SPECIAL);
      genlayerzoom = new GenLayerZoom(2002L, genlayeredge);
      genlayerzoom = new GenLayerZoom(2003L, genlayerzoom);
      genlayeraddisland = new GenLayerAddIsland(4L, genlayerzoom);
      GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland);
      GenLayerDeepOcean genlayerdeepocean = new GenLayerDeepOcean(4L, genlayeraddmushroomisland);
      GenLayer genlayer2 = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
      byte b0 = 4;
      if (p_75901_2_ == WorldType.LARGE_BIOMES) {
         b0 = 6;
      }

      if (flag) {
         b0 = 4;
      }

      b0 = GenLayer.getModdedBiomeSize(p_75901_2_, b0);
      GenLayer genlayer = GenLayerZoom.magnify(1000L, genlayer2, 0);
      GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, genlayer);
      Object object = p_75901_2_.getBiomeLayer(p_75901_0_, genlayer2);
      GenLayer genlayer1 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
      GenLayerHills genlayerhills = new GenLayerHills(1000L, (GenLayer)object, genlayer1);
      genlayer = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
      genlayer = GenLayerZoom.magnify(1000L, genlayer, b0);
      GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer);
      GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
      object = new GenLayerRareBiome(1001L, genlayerhills);

      for(int j = 0; j < b0; ++j) {
         object = new GenLayerZoom((long)(1000 + j), (GenLayer)object);
         if (j == 0) {
            object = new GenLayerAddIsland(3L, (GenLayer)object);
         }

         if (j == 1) {
            object = new GenLayerShore(1000L, (GenLayer)object);
         }
      }

      GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, (GenLayer)object);
      GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
      GenLayerVoronoiZoom genlayervoronoizoom = new GenLayerVoronoiZoom(10L, genlayerrivermix);
      genlayerrivermix.initWorldGenSeed(p_75901_0_);
      genlayervoronoizoom.initWorldGenSeed(p_75901_0_);
      return new GenLayer[]{genlayerrivermix, genlayervoronoizoom, genlayerrivermix};
   }
   }

}
