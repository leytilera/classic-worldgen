package dev.tilera.cwg.genlayer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerAddSnowClassic extends GenLayer {
   public GenLayerAddSnowClassic(long p_i2121_1_, GenLayer p_i2121_3_) {
      super(p_i2121_1_);
      this.parent = p_i2121_3_;
   }

   public int[] getInts(int var1, int var2, int var3, int var4) {
      int var5 = var1 - 1;
      int var6 = var2 - 1;
      int var7 = var3 + 2;
      int var8 = var4 + 2;
      int[] var9 = this.parent.getInts(var5, var6, var7, var8);
      int[] var10 = IntCache.getIntCache(var3 * var4);

      for(int var11 = 0; var11 < var4; ++var11) {
         for(int var12 = 0; var12 < var3; ++var12) {
            int var13 = var9[var12 + 1 + (var11 + 1) * var7];
            this.initChunkSeed((long)(var12 + var1), (long)(var11 + var2));
            if (var13 == 0) {
               var10[var12 + var11 * var3] = 0;
            } else {
               int var14 = this.nextInt(5);
               if (var14 == 0) {
                  var14 = BiomeGenBase.icePlains.biomeID;
               } else {
                  var14 = 1;
               }

               var10[var12 + var11 * var3] = var14;
            }
         }
      }

      return var10;
   }
}