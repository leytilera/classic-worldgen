package dev.tilera.cwg.api.hooks.common;

import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.hooks.IHookType;
import dev.tilera.cwg.api.hooks.IHookTypeManager;

public class HookTypes {

     public static IHookType<ICavegenHook> CAVEGEN;
    public static IHookType<IStructureGenHook> STRUCTUREGEN;
    public static IHookType<ITemperatureHook> TEMPERATURE;
    public static IHookType<IChunkManagerFactory> GENERATOR;

    public static void init(IHookRegistry registry) {
        IHookTypeManager manager = registry.getHookTypeManager();
        CAVEGEN = manager.createHookType(ICavegenHook.class);
        STRUCTUREGEN = manager.createHookType(IStructureGenHook.class);
        TEMPERATURE = manager.createHookType(ITemperatureHook.class);
        GENERATOR = manager.createHookType(IChunkManagerFactory.class);
    }
    
}
