package dev.tilera.cwg.api;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import net.minecraft.world.World;

public class CwgGlobals {

    private static IGeneratorOptionProvider defaultProvider = null;
    private static IGeneratorOptionManager manager = null;
    private static World currentWorld = null;

    public static boolean isCwgWorld(World world) {
        return world != null && world.provider.worldChunkMgr instanceof AbstractChunkManager;
    }

    public static IGeneratorOptionProvider getOptionProvider(World world) {
        if (!isCwgWorld(world)) return defaultProvider;
        AbstractChunkManager chunkManager = (AbstractChunkManager) world.provider.worldChunkMgr;
        return chunkManager.getOptionProvider();
    }

    public static IGeneratorOptionProvider getOptionProvider() {
        if (isCwgWorld(currentWorld)) return getOptionProvider(currentWorld); 
        return defaultProvider;
    }

    public static void setCurrentState(World world) {
        currentWorld = world;
    }

    public static World getCurrentState() {
        return currentWorld;
    }

    public static void setDefaultProvider(IGeneratorOptionProvider provider) {
        defaultProvider = provider;
    }

    public static void setOptionManager(IGeneratorOptionManager manager) {
        CwgGlobals.manager = manager;
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
