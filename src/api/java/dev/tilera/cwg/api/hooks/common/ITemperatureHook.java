package dev.tilera.cwg.api.hooks.common;

import dev.tilera.cwg.api.hooks.IHookProvider;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public interface ITemperatureHook extends IHookProvider {

    float getFloatTemperature(World world, int x, int y, int z, float baseTemperature, NoiseGeneratorPerlin temperatureNoise);
    
}
