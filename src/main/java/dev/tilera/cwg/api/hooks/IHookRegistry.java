package dev.tilera.cwg.api.hooks;

public interface IHookRegistry {
    
    void registerHookProvider(IHookProvider hookProvider);

    IHookProvider getHookProvider(String id);

}
