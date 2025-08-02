package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.modules.IModule;

public class HooksModule implements IModule {

    @Override
    public void registerGenerators(IHookRegistry registry) {}

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {
        registry.registerOption(
            new HookOption("cwg:temperature_hook", null, new DefaultTemperatureHook(), CwgGlobals.getHookRegistry(), HookTypes.TEMPERATURE).registerDefault()
            );
        registry.registerOption(
            new HookOption("cwg:cavegen_hook", null, new DefaultCavegenHook(), CwgGlobals.getHookRegistry(), HookTypes.CAVEGEN).registerDefault()
            );
        registry.registerOption(
            new HookOption("cwg:structuregen_hook", null, new DefaultStructureGenHook(), CwgGlobals.getHookRegistry(), HookTypes.STRUCTUREGEN).registerDefault()
        );
    }

    @Override
    public void registerHooks(IHookRegistry registry) {
        registry.registerHookProvider(new SwissCavegenHook());
    }
    
}
