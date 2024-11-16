package dev.tilera.cwg.modules;

import dev.tilera.cwg.api.generator.IChunkManagerRegistry;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;

public interface IModule {
    
    void registerGenerators(IChunkManagerRegistry registry);

    void registerOptions(IGeneratorOptionRegistry registry);

    void registerHooks(IHookRegistry registry);

}
