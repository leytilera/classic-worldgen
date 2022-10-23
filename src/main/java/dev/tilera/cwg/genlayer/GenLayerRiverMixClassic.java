package dev.tilera.cwg.genlayer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRiverMixClassic extends GenLayer {
    private GenLayer biomePatternGeneratorChain;
    private GenLayer riverPatternGeneratorChain;
 
    public GenLayerRiverMixClassic(long p_i2129_1_, GenLayer p_i2129_3_, GenLayer p_i2129_4_) {
       super(p_i2129_1_);
       this.biomePatternGeneratorChain = p_i2129_3_;
       this.riverPatternGeneratorChain = p_i2129_4_;
    }
 
    public void initWorldGenSeed(long var1) {
       this.biomePatternGeneratorChain.initWorldGenSeed(var1);
       this.riverPatternGeneratorChain.initWorldGenSeed(var1);
       super.initWorldGenSeed(var1);
    }
 
    public int[] getInts(int var1, int var2, int var3, int var4) {
       int[] var5 = this.biomePatternGeneratorChain.getInts(var1, var2, var3, var4);
       int[] var6 = this.riverPatternGeneratorChain.getInts(var1, var2, var3, var4);
       int[] var7 = IntCache.getIntCache(var3 * var4);
 
       for(int var8 = 0; var8 < var3 * var4; ++var8) {
          if (var5[var8] == BiomeGenBase.ocean.biomeID) {
             var7[var8] = var5[var8];
          } else if (var6[var8] >= 0) {
             if (var5[var8] == BiomeGenBase.icePlains.biomeID) {
                var7[var8] = BiomeGenBase.frozenRiver.biomeID;
             } else if (var5[var8] != BiomeGenBase.mushroomIsland.biomeID && var5[var8] != BiomeGenBase.mushroomIslandShore.biomeID) {
                var7[var8] = var6[var8];
             } else {
                var7[var8] = BiomeGenBase.mushroomIslandShore.biomeID;
             }
          } else {
             var7[var8] = var5[var8];
          }
       }
 
       return var7;
    }
 }
