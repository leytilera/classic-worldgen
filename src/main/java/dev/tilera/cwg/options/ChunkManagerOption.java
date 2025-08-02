package dev.tilera.cwg.options;

import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.hooks.HookOption;

public class ChunkManagerOption extends HookOption {

    public ChunkManagerOption(String id, String displayName, IChunkManagerFactory defaultValue,
            IHookRegistry registry) {
        super(id, displayName, defaultValue, registry, HookTypes.GENERATOR);
    }

    @Override
    public Type getOptionType() {
        return Type.ENUM;
    }
    
}
