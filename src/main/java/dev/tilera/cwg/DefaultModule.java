package dev.tilera.cwg;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.modules.Module;
import dev.tilera.cwg.options.common.BooleanOption;
import dev.tilera.cwg.options.common.IntOption;
import dev.tilera.cwg.options.common.StringOption;
import dev.tilera.cwg.modules.IModule;
import dev.tilera.cwg.options.ChunkManagerOption;
import dev.tilera.cwg.options.ParentOption;
import dev.tilera.cwg.vanilla.SingleBiomeChunkManagerFactory;
import dev.tilera.cwg.vanilla.VanillaChunkManagerFactory;

@Module
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
        registry.registerOption(ParentOption.INSTANCE);
        registry.registerOption(new StringOption("Name", "cwg:internal:name", "Options", true, false));
    }

    @Override
    public void registerHooks(IHookRegistry registry) {}
    
}
