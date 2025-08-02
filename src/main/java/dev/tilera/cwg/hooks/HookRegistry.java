package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.hooks.IHookType;
import dev.tilera.cwg.api.hooks.IHookTypeManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import dev.tilera.cwg.api.hooks.IHookProvider;

public class HookRegistry implements IHookRegistry, IHookTypeManager {

    private Map<String, IHookProvider> registry = new HashMap<>();
    private Map<Class<?>, IHookType<?>> hookTypes = new HashMap<>();
    private Map<IHookType<?>, Map<String, IHookProvider>> typedRegistry = new HashMap<>();

    @Override
    public void registerHookProvider(IHookProvider hookProvider) {
        registry.put(hookProvider.getID(), hookProvider);
        typedRegistry.entrySet()
            .stream()
            .filter((e) -> hookProvider.isSupported(e.getKey()))
            .forEach((e) -> e.getValue().put(hookProvider.getID(), hookProvider));
    }

    @Override
    public IHookProvider getHookProvider(String id) {
        return registry.get(id);
    }

    @Override
    public Stream<IHookProvider> getHookProviders() {
        return registry.values().stream();
    }

    @Override
    public IHookProvider getHookProvider(String id, IHookType<?> type) {
        Map<String, IHookProvider> reg = typedRegistry.get(type);
        if (reg == null) {
            return null;
        } else {
            return reg.get(id);
        }
    }

    @Override
    public Stream<IHookProvider> getHookProvidersFor(IHookType<?> type) {
        Map<String, IHookProvider> reg = typedRegistry.get(type);
        if (reg == null) {
            return Stream.empty();
        } else {
            return reg.values().stream();
        }
    }

    @Override
    public void addHookType(IHookType<?> type) {
        hookTypes.put(type.getHookClass(), type);
        Map<String, IHookProvider> reg = new HashMap<>();
        typedRegistry.put(type, reg);
        getHookProviders().filter((p) -> p.isSupported(type)).forEach((p) -> reg.put(p.getID(), p));

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> IHookType<T> getHookType(Class<T> clazz) {
        return (IHookType<T>) hookTypes.get(clazz);
    }

    @Override
    public <T> IHookType<T> createHookType(Class<T> clazz) {
        IHookType<T> type = getHookType(clazz);
        if (type == null) {
            type = new HookType<>(clazz);
            addHookType(type);
        }
        return type;
    }

    @Override
    public boolean hasHookType(Class<?> clazz) {
        return hookTypes.containsKey(clazz);
    }

    @Override
    public IHookTypeManager getHookTypeManager() {
        return this;
    }
    
}
