package dev.tilera.cwg.hooks;

import java.util.HashMap;
import java.util.Map;

import dev.tilera.cwg.api.hooks.IHookOption;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.hooks.IHookType;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class HookOption implements IHookOption<IHookProvider> {

    private String id;
    private String displayName;
    private IHookProvider defaultProvider;
    private IHookRegistry registry;
    private IHookType<?> hookType;

    public HookOption(String id, String displayName, IHookProvider defaultProvider, IHookRegistry registry, IHookType<?> hookType) {
        this.id = id;
        this.defaultProvider = defaultProvider;
        this.registry = registry;
        this.hookType = hookType;
        this.displayName = displayName;
    }

    @Override
    public IHookProvider getDefault() {
        return defaultProvider;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getVisableName() {
        return displayName;
    }

    @Override
    public Class<IHookProvider> getType() {
        return IHookProvider.class;
    }

    @Override
    public Type getOptionType() {
        return Type.INTERNAL;
    }

    @Override
    public Map<IHookProvider, String> getPossibleValues() {
        Map<IHookProvider, String> map = new HashMap<>();
        registry.getHookProvidersFor(getHookType()).forEach((p) -> map.put(p, p.getDisplayName()));
        return map;
    }

    @Override
    public IHookProvider decodeString(String input) {
        return null;
    }

    public HookOption registerDefault() {
        registry.registerHookProvider(defaultProvider);
        return this;
    }

    @Override
    public IHookType<?> getHookType() {
        return hookType;
    }

    @Override
    public <E> IObjectSerializer<E, IHookProvider> getSerializer(IObjectManipulator<E> manipulator) {
        return new HookIdSerializer<E>(registry, manipulator);
    }
    
}
