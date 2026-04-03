package dev.tilera.cwg.worldtypes;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.options.common.StringOption;
import net.anvilcraft.anvillib.api.inject.Implementation;
import dev.tilera.cwg.modules.IModule;

@Implementation(IModule.class)
public class WorldtypeModule implements IModule {

    @Override
    public void registerGenerators(IHookRegistry registry) {
        registry.registerHookProvider(new WorldtypeChunkManagerFactory());
    }

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {
        registry.registerOption(new WorldTypeOption());
        registry.registerOption(new StringOption("cwg:generator.worldtype:options", "Worldtype Generator Options", "", false, true));
    }

    @Override
    public void registerHooks(IHookRegistry registry) {}
    
}
