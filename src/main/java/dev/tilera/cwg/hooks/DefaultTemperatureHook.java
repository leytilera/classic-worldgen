package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.CwgGlobals;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class DefaultTemperatureHook implements ITemperatureHook {

    @Override
    public float getFloatTemperature(World world, int x, int y, int z, float baseTemperature, NoiseGeneratorPerlin temperatureNoise) {
        if (y > 64 && !CwgGlobals.getOptionProvider(world).getBoolean("cwg:classic_extreme_hills")) {
            float f = (float)temperatureNoise.func_151601_a((double)x * 1.0D / 8.0D, (double)z * 1.0D / 8.0D) * 4.0F;
            return baseTemperature - (f + (float)y - 64.0F) * 0.05F / 30.0F;
         } else {
            return baseTemperature;
         }
    }
    
}
