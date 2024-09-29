package dev.tilera.cwg.api;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerRegistry;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import net.minecraft.world.World;

public class CwgGlobals {

    private static IGeneratorOptionProvider defaultProvider = null;
    private static IHookRegistry hookRegistry = null;
    private static IChunkManagerRegistry generatorRegistry = null;
    private static IGeneratorOptionRegistry optionRegistry = null;
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

    public static void setHookRegistry(IHookRegistry registry) {
        hookRegistry = registry;
    }

    public static IHookRegistry getHookRegistry() {
        return hookRegistry;
    }

    public static void setGeneratorRegistry(IChunkManagerRegistry registry) {
        generatorRegistry = registry;
    }

    public static IChunkManagerRegistry getGeneratorRegistry() {
        return generatorRegistry;
    }

    public static void setOptionRegistry(IGeneratorOptionRegistry registry) {
        optionRegistry = registry;
    }

    public static IGeneratorOptionRegistry getOptionRegistry() {
        return optionRegistry;
    }

}
