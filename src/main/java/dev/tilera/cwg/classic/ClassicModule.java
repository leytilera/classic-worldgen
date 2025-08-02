package dev.tilera.cwg.classic;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.utils.BooleanOption;
import dev.tilera.cwg.modules.IModule;

public class ClassicModule implements IModule {

    @Override
    public void registerGenerators(IHookRegistry registry) {
        registry.registerHookProvider(new ClassicChunkManagerFactory());
    }

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {
        registry.registerOption(new BooleanOption("cwg:generator.classic:disableJungle", "Disable Jungle", false, true));
        registry.registerOption(new BooleanOption("cwg:generator.classic:newVanillaBiomes", "New Vanilla Biomes", false, true));
        registry.registerOption(new BooleanOption("cwg:generator.classic:disableModdedBiomes", "No Modded Biomes", false, true));
        registry.registerOption(new BooleanOption("cwg:generator.classic:enableModdedWorldgen", "Modded Worldgen", true, true));
    }

    @Override
    public void registerHooks(IHookRegistry registry) {}
    
}
