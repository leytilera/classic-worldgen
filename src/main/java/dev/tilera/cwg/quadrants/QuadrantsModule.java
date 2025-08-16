package dev.tilera.cwg.quadrants;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.modules.IModule;
import dev.tilera.cwg.modules.Module;

@Module
public class QuadrantsModule implements IModule {

    @Override
    public void registerGenerators(IHookRegistry registry) {
        registry.registerHookProvider(new QuadrantGeneratorFactory());
    }

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {
        registry.registerOption(new QuadrantOptionsOption("cwg:generator.quadrants:nw", "North-West Options"));
        registry.registerOption(new QuadrantOptionsOption("cwg:generator.quadrants:sw", "South-West Options"));
        registry.registerOption(new QuadrantOptionsOption("cwg:generator.quadrants:ne", "North-East Options"));
        registry.registerOption(new QuadrantOptionsOption("cwg:generator.quadrants:se", "South-East Options"));
    }

    @Override
    public void registerHooks(IHookRegistry registry) {}
    
}
