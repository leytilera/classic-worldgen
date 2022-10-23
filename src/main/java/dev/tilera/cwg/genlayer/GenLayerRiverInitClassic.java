package dev.tilera.cwg.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRiverInitClassic extends GenLayer {
    public GenLayerRiverInitClassic(long p_i2127_1_, GenLayer p_i2127_3_) {
       super(p_i2127_1_);
       this.parent = p_i2127_3_;
    }
 
    public int[] getInts(int var1, int var2, int var3, int var4) {
       int[] var5 = this.parent.getInts(var1, var2, var3, var4);
       int[] var6 = IntCache.getIntCache(var3 * var4);
 
       for(int var7 = 0; var7 < var4; ++var7) {
          for(int var8 = 0; var8 < var3; ++var8) {
             this.initChunkSeed((long)(var8 + var1), (long)(var7 + var2));
             var6[var8 + var7 * var3] = var5[var8 + var7 * var3] > 0 ? this.nextInt(2) + 2 : 0;
          }
       }
 
       return var6;
    }
 }
