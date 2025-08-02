package dev.tilera.cwg.infdev;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.modules.IModule;

public class InfdevModule implements IModule {

    @Override
    public void registerGenerators(IHookRegistry registry) {
        registry.registerHookProvider(new InfdevChunkManagerFactory());
    }

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {}

    @Override
    public void registerHooks(IHookRegistry registry) {}
    
}
