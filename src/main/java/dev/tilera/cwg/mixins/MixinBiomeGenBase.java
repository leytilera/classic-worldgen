package dev.tilera.cwg.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import dev.tilera.cwg.ClassicWorldgen;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

@Mixin(BiomeGenBase.class)
public abstract class MixinBiomeGenBase {

    @Shadow
    public float temperature;
    @Shadow
    @Final
    protected static NoiseGeneratorPerlin temperatureNoise;

    /**
     * @author tilera
     * @reason No snow on hills
     */
    @Overwrite
    public final float getFloatTemperature(int p_150564_1_, int p_150564_2_, int p_150564_3_) {
        if (p_150564_2_ > 64 && ClassicWorldgen.USED != ClassicWorldgen.CLASSIC) {
           float f = (float)temperatureNoise.func_151601_a((double)p_150564_1_ * 1.0D / 8.0D, (double)p_150564_3_ * 1.0D / 8.0D) * 4.0F;
           return this.temperature - (f + (float)p_150564_2_ - 64.0F) * 0.05F / 30.0F;
        } else {
           return this.temperature;
        }
     }
    
}
