package dev.tilera.cwg.hooks;

import java.util.HashMap;
import java.util.Map;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.hooks.IHookProvider;

public class HookRegistry implements IHookRegistry {

    private Map<String, IHookProvider> registry = new HashMap<>();

    @Override
    public void registerHookProvider(IHookProvider hookProvider) {
        registry.put(hookProvider.getID(), hookProvider);
    }

    @Override
    public IHookProvider getHookProvider(String id) {
        return registry.get(id);
    }
    
}
