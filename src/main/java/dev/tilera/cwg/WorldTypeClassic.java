package dev.tilera.cwg;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;

public class WorldTypeClassic extends WorldType {

    public WorldTypeClassic(String name) {
        super(name);
    }
    
    @Override
    public WorldChunkManager getChunkManager(World world) {
        return new WorldChunkManagerClassic(world);
    }

}
