package dev.tilera.cwg.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import dev.tilera.cwg.ClassicWorldgen;
import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.hooks.ITemperatureHook;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import scala.collection.mutable.StringBuilder;

@Mixin(BiomeGenBase.class)
public abstract class MixinBiomeGenBase {

    @Shadow
    public float temperature;
    @Shadow
    @Final
    protected static NoiseGeneratorPerlin temperatureNoise;

    @Inject(method = "<init>(IZ)V", at = @At("TAIL"), remap = false)
    public void constructorHead(int id, boolean register, CallbackInfo ci) {
       if (register) {
          if (ClassicWorldgen.biomeCache.length > id) {
             BiomeGenBase self = (BiomeGenBase)(Object)this;
             if (ClassicWorldgen.biomeCache[id] != null && ClassicWorldgen.biomeCache[id] != self) {
               StringBuilder builder = new StringBuilder();
               builder.append("Biome ID conflict: " + id + " : " + self.biomeName + " : " + ClassicWorldgen.biomeCache[id].biomeName);
               builder.append("\nFree Biome IDs:");
               for (int i = 0; i < ClassicWorldgen.biomeCache.length; i++) {
                  if (ClassicWorldgen.biomeCache[i] == null) {
                     builder.append("\n");
                     builder.append(i);
                  }
               }
               throw new IllegalArgumentException(builder.toString());
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
    public float getFloatTemperature(int p_150564_1_, int p_150564_2_, int p_150564_3_) {
        ITemperatureHook hook = CwgGlobals.getOptionProvider().getValue("cwg:temperature_hook", IHookProvider.class).getHook(ITemperatureHook.class);
        return hook.getFloatTemperature(CwgGlobals.getCurrentState(), p_150564_1_, p_150564_2_, p_150564_3_, this.temperature, temperatureNoise);
     }
    
}
