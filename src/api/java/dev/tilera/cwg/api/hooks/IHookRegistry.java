package dev.tilera.cwg.api.hooks;

import java.util.stream.Stream;

public interface IHookRegistry {
    
    void registerHookProvider(IHookProvider hookProvider);

    IHookProvider getHookProvider(String id);

    default IHookProvider getHookProvider(String id, IHookType<?> type) {
        IHookProvider provider = getHookProvider(id);
        return provider.isSupported(type) ? provider : null;
    }

    Stream<IHookProvider> getHookProviders();

    default Stream<IHookProvider> getHookProvidersFor(IHookType<?> type) {
        return getHookProviders().filter((provider) -> provider.isSupported(type));
    }

    IHookTypeManager getHookTypeManager();

}
