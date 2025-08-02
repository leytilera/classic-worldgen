package dev.tilera.cwg.modules;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;

public interface IModule {
    
    void registerGenerators(IHookRegistry registry);

    void registerOptions(IGeneratorOptionRegistry registry);

    void registerHooks(IHookRegistry registry);

}
