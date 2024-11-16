package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.generator.IChunkManagerRegistry;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.modules.IModule;

public class HooksModule implements IModule {

    @Override
    public void registerGenerators(IChunkManagerRegistry registry) {}

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {
        registry.registerOption(
            new HookOption("cwg:temperature_hook", new DefaultTemperatureHook(), CwgGlobals.getHookRegistry()).registerDefault()
            );
        registry.registerOption(
            new HookOption("cwg:cavegen_hook", new DefaultCavegenHook(), CwgGlobals.getHookRegistry()).registerDefault()
            );
    }

    @Override
    public void registerHooks(IHookRegistry registry) {
        registry.registerHookProvider(new SwissCavegenHook());
    }
    
}
