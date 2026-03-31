package dev.tilera.cwg.beta;

import dev.tilera.cwg.api.hooks.IHookType;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.hooks.HookOption;
import dev.tilera.cwg.modules.IModule;
import dev.tilera.cwg.modules.Module;

@Module
public class BetaModule implements IModule {

    public static IHookType<IBetaBiomeProvider> BIOMES;

    @Override
    public void init(IGeneratorOptionManager manager) {
        manager.getHookRegistry().registerHookProvider(new BetaChunkManagerFactory());
        manager.getHookRegistry().registerHookProvider(new BetaCavegenHook());
        BIOMES = manager.getHookRegistry().getHookTypeManager().createHookType(IBetaBiomeProvider.class);
        manager.getOptionRegistry().registerOption(new HookOption(
            "cwg:beta_biomes",
            null,
            new VanillaBiomeProvider(), 
            manager.getHookRegistry(), 
            BIOMES).registerDefault()
        );
        IModule.super.init(manager);
    }

}
