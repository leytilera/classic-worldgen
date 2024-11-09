package dev.tilera.cwg;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldTypeCustom extends WorldType {

    public WorldTypeCustom() {
        super("cwg");
    }

    @Override
    public WorldChunkManager getChunkManager(World world) {
        IGeneratorOptionRegistry registry = CwgGlobals.getOptionRegistry();
        String opts = world.getWorldInfo().getGeneratorOptions();
        IGeneratorOptionProvider options;
        if (opts.isEmpty()) {
            options = registry;
        } else {
            try {
                options = registry.decodeOptions(opts);
            } catch (Exception e) {
                e.printStackTrace();
                options = registry;
            }
        }
        AbstractChunkManager manager = options.getValue("cwg:generator", IChunkManagerFactory.class).createChunkManager(options, world);
        CwgGlobals.setCurrentState(world);
        return manager;
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        if (world.provider.worldChunkMgr instanceof AbstractChunkManager) {
            return ((AbstractChunkManager)world.provider.worldChunkMgr).getGenerator(world);
        } else {
            throw new RuntimeException("Invalid WorldChunkManager");
        }
    }

}
