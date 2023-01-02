package dev.tilera.cwg.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import dev.tilera.cwg.ClassicWorldgen;
import dev.tilera.cwg.Config;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

@Mixin(BiomeGenBase.class)
public abstract class MixinBiomeGenBase {

    @Shadow
    public float temperature;
    @Shadow
    @Final
    protected static NoiseGeneratorPerlin temperatureNoise;

    @Inject(method = "<init>(IZ)V", at = @At("TAIL"))
    public void constructorHead(int id, boolean register, CallbackInfo ci) {
       if (register) {
          if (ClassicWorldgen.biomeCache.length > id) {
             BiomeGenBase self = (BiomeGenBase)(Object)this;
             if (ClassicWorldgen.biomeCache[id] != null && ClassicWorldgen.biomeCache[id] != self) {
               throw new RuntimeException("Biome ID conflict: " + id + " : " + self.biomeName + " : " + ClassicWorldgen.biomeCache[id].biomeName);
             } else {
                ClassicWorldgen.biomeCache[id] = self;
             }
          }
       }
    }

    /**
     * @author tilera
     * @reason No snow on hills
     */
    @Overwrite
    public final float getFloatTemperature(int p_150564_1_, int p_150564_2_, int p_150564_3_) {
        if (p_150564_2_ > 64 && !Config.disableHeightTemperature) {
           float f = (float)temperatureNoise.func_151601_a((double)p_150564_1_ * 1.0D / 8.0D, (double)p_150564_3_ * 1.0D / 8.0D) * 4.0F;
           return this.temperature - (f + (float)p_150564_2_ - 64.0F) * 0.05F / 30.0F;
        } else {
           return this.temperature;
        }
     }
    
}
