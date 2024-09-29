package dev.tilera.cwg;

import java.util.List;
import java.util.Random;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;

public class DelegateChunkManager extends AbstractChunkManager {

    private IGeneratorOptionProvider provider;
    private IChunkProvider chunkProvider;
    private WorldChunkManager chunkManager;

    public DelegateChunkManager(IGeneratorOptionProvider provider, IChunkProvider chunkProvider,
            WorldChunkManager chunkManager) {
        this.provider = provider;
        this.chunkProvider = chunkProvider;
        this.chunkManager = chunkManager;
    }

    @Override
    public IChunkProvider getGenerator(World world) {
        return this.chunkProvider;
    }

    @Override
    public IGeneratorOptionProvider getOptionProvider() {
        return this.provider;
    }

    @Override
    public BiomeGenBase getBiomeGenAt(int x, int z) {
        return this.chunkManager.getBiomeGenAt(x, z);
    }

    @Override
    public float[] getRainfall(float[] p_76936_1_, int x, int z, int p_76936_4_, int p_76936_5_) {
        return this.chunkManager.getRainfall(p_76936_1_, x, z, p_76936_4_, p_76936_5_);
    }

    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] p_76933_1_, int x, int z,
            int p_76933_4_, int p_76933_5_) {
        return this.chunkManager.loadBlockGeneratorData(p_76933_1_, x, z, p_76933_4_, p_76933_5_);
    }

    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] p_76931_1_, int x, int z, int p_76931_4_,
            int p_76931_5_, boolean p_76931_6_) {
        return this.chunkManager.getBiomeGenAt(p_76931_1_, x, z, p_76931_4_, p_76931_5_, p_76931_6_);
    }

    @Override
    public boolean areBiomesViable(int x, int z, int p_76940_3_, List p_76940_4_) {
        return this.chunkManager.areBiomesViable(x, z, p_76940_3_, p_76940_4_);
    }

    @Override
    public ChunkPosition findBiomePosition(int x, int z, int p_150795_3_, List p_150795_4_,
            Random p_150795_5_) {
        return this.chunkManager.findBiomePosition(x, z, p_150795_3_, p_150795_4_, p_150795_5_);
    }

    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] p_76937_1_, int x, int z,
            int p_76937_4_, int p_76937_5_) {
        return this.chunkManager.getBiomesForGeneration(p_76937_1_, x, z, p_76937_4_, p_76937_5_);
    }

    @Override
    public void cleanupCache() {
        this.chunkManager.cleanupCache();
    }

    @Override
    public List getBiomesToSpawnIn() {
        return this.chunkManager.getBiomesToSpawnIn();
    }

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType arg0, long arg1, GenLayer[] arg2) {
        return this.chunkManager.getModdedBiomeGenerators(arg0, arg1, arg2);
    }

    @Override
    public float getTemperatureAtHeight(float arg0, int arg1) {
        return this.chunkManager.getTemperatureAtHeight(arg0, arg1);
    }
    
}
