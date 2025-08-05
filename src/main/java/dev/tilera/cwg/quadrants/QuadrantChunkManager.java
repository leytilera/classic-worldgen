package dev.tilera.cwg.quadrants;

import java.util.List;
import java.util.Random;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class QuadrantChunkManager extends AbstractChunkManager {

    private QuadrantManager<AbstractChunkManager> quadrants;
    private IGeneratorOptionProvider options;    

    public QuadrantChunkManager(QuadrantManager<AbstractChunkManager> quadrants, IGeneratorOptionProvider options) {
        this.quadrants = quadrants;
        this.options = options;
    }

    @Override
    public IChunkProvider getGenerator(World world) {
        return new QuadrantChunkProvider(quadrants.map((cm) -> cm.getGenerator(world)));
    }

    @Override
    public IGeneratorOptionProvider getOptionProvider() {
        return options;
    }

    @Override
    public BiomeGenBase getBiomeGenAt(int x, int z) {
        return quadrants.getFor(x, z).getBiomeGenAt(x, z);
    }

    @Override
    public float[] getRainfall(float[] p_76936_1_, int x, int z, int p_76936_4_, int p_76936_5_) {
        return quadrants.getFor(x, z).getRainfall(p_76936_1_, x, z, p_76936_4_, p_76936_5_);
    }

    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] p_76933_1_, int x, int z,
            int p_76933_4_, int p_76933_5_) {
        return quadrants.getFor(x, z).loadBlockGeneratorData(p_76933_1_, x, z, p_76933_4_, p_76933_5_);
    }

    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] p_76931_1_, int x, int z, int p_76931_4_,
            int p_76931_5_, boolean p_76931_6_) {
        return quadrants.getFor(x, z).getBiomeGenAt(p_76931_1_, x, z, p_76931_4_, p_76931_5_, p_76931_6_);
    }

    @Override
    public boolean areBiomesViable(int x, int z, int p_76940_3_, List p_76940_4_) {
        return quadrants.getFor(x, z).areBiomesViable(x, z, p_76940_3_, p_76940_4_);
    }

    @Override
    public ChunkPosition findBiomePosition(int x, int z, int p_150795_3_, List p_150795_4_,
            Random p_150795_5_) {
        return quadrants.getFor(x, z).findBiomePosition(x, z, p_150795_3_, p_150795_4_, p_150795_5_);
    }

    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] p_76937_1_, int x, int z,
            int p_76937_4_, int p_76937_5_) {
        return quadrants.getFor(x, z).getBiomesForGeneration(p_76937_1_, x, z, p_76937_4_, p_76937_5_);
    }
    
}
