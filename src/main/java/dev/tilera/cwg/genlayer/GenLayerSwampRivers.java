package dev.tilera.cwg.genlayer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerSwampRivers extends GenLayer {
    public GenLayerSwampRivers(long p_i2132_1_, GenLayer p_i2132_3_) {
       super(p_i2132_1_);
       this.parent = p_i2132_3_;
    }
 
    public int[] getInts(int var1, int var2, int var3, int var4) {
       int[] var5 = this.parent.getInts(var1 - 1, var2 - 1, var3 + 2, var4 + 2);
       int[] var6 = IntCache.getIntCache(var3 * var4);
 
       for(int var7 = 0; var7 < var4; ++var7) {
          for(int var8 = 0; var8 < var3; ++var8) {
             this.initChunkSeed((long)(var8 + var1), (long)(var7 + var2));
             int var9 = var5[var8 + 1 + (var7 + 1) * (var3 + 2)];
             if ((var9 != BiomeGenBase.swampland.biomeID || this.nextInt(6) != 0) && (var9 != BiomeGenBase.jungle.biomeID && var9 != BiomeGenBase.jungleHills.biomeID || this.nextInt(8) != 0)) {
                var6[var8 + var7 * var3] = var9;
             } else {
                var6[var8 + var7 * var3] = BiomeGenBase.river.biomeID;
             }
          }
       }
 
       return var6;
    }
 }
