package dev.tilera.cwg.api;

import java.util.function.Supplier;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import net.anvilcraft.anvillib.api.inject.Inject;
import net.minecraft.world.World;

public class CwgGlobals {

    @Inject(IGeneratorOptionManager.class)
    private static IGeneratorOptionManager manager = null;
    @Inject(Supplier.class)
    private static Supplier<World> currentWorld = null;

    public static boolean isCwgWorld(World world) {
        return world != null && world.provider.worldChunkMgr instanceof AbstractChunkManager;
    }

    public static IGeneratorOptionProvider getOptionProvider(World world) {
        if (!isCwgWorld(world)) return manager.getOptions(IGeneratorOptionManager.CONFIG).get();
        AbstractChunkManager chunkManager = (AbstractChunkManager) world.provider.worldChunkMgr;
        return chunkManager.getOptionProvider();
    }

    public static IGeneratorOptionProvider getOptionProvider() {
        return getOptionProvider(currentWorld.get());
    }

    public static World getCurrentState() {
        return currentWorld.get();
    }

    public static IGeneratorOptionManager getOptionManager() {
        return manager;
    }

    public static IHookRegistry getHookRegistry() {
        return manager.getHookRegistry();
    }

    public static IGeneratorOptionRegistry getOptionRegistry() {
        return manager.getOptionRegistry();
    }

}
