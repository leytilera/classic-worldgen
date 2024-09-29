package dev.tilera.cwg.api.generator;

import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class AbstractChunkManager extends WorldChunkManager {

    protected AbstractChunkManager() {
        super();
    }

    public AbstractChunkManager(long p_i1975_1_, WorldType p_i1975_3_) {
        super(p_i1975_1_, p_i1975_3_);
    }

    public AbstractChunkManager(World p_i1976_1_) {
        super(p_i1976_1_);
    }

    public abstract IChunkProvider getGenerator(World world);

    public abstract IGeneratorOptionProvider getOptionProvider();

    public IGeneratorOptionProvider getLocalOption(int chunkX, int chunkZ) {
        return getOptionProvider();
    }

}
