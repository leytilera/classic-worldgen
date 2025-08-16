package dev.tilera.cwg.modules;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;

public interface IModule {
    
    default void registerGenerators(IHookRegistry registry) {}

    default void registerOptions(IGeneratorOptionRegistry registry) {}

    default void registerHooks(IHookRegistry registry) {}

    default void init(IGeneratorOptionManager manager) {
        registerGenerators(manager.getHookRegistry());
        registerHooks(manager.getHookRegistry());
        registerOptions(manager.getOptionRegistry());
    }

}
