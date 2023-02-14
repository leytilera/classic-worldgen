package dev.tilera.cwg;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldTypeClassic extends WorldType {

    public WorldTypeClassic(String name) {
        super(name);
    }
    
    @Override
    public WorldChunkManager getChunkManager(World world) {
        return new WorldChunkManagerClassic(world);
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new ChunkProviderClassic(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
    }

}
