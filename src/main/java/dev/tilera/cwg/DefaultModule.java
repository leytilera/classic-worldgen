package dev.tilera.cwg;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.utils.BooleanOption;
import dev.tilera.cwg.api.utils.IntOption;
import dev.tilera.cwg.modules.IModule;
import dev.tilera.cwg.options.ChunkManagerOption;
import dev.tilera.cwg.vanilla.SingleBiomeChunkManagerFactory;
import dev.tilera.cwg.vanilla.VanillaChunkManagerFactory;

public class DefaultModule implements IModule {

    @Override
    public void registerGenerators(IHookRegistry registry) {
        registry.registerHookProvider(new SingleBiomeChunkManagerFactory());
    }

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {
        registry.registerOption(new BooleanOption("cwg:classic_extreme_hills", "Classic Extreme Hills", false));
        registry.registerOption(new BooleanOption("cwg:farlands", "Enable Farlands", false));
        registry.registerOption(new BooleanOption("cwg:desert_lakes", "Enable Desert Lakes", false));
        registry.registerOption(new BooleanOption("cwg:disable_jungle_melons", "Disable Jungle Melons", false));
        registry.registerOption(new BooleanOption("cwg:disable_new_flowers", "Disable new Flowers", false));
        registry.registerOption(new BooleanOption("cwg:disable_tall_flowers", "Disable Tall Flowers", false));
        registry.registerOption(new IntOption("cwg:generator.singleBiome:biomeID", "Biome ID", 0, false, true));
        registry.registerOption(new ChunkManagerOption(
            "cwg:generator", 
            "Generator", 
            new VanillaChunkManagerFactory(),
            CwgGlobals.getHookRegistry()
        ).registerDefault());
    }

    @Override
    public void registerHooks(IHookRegistry registry) {}
    
}
