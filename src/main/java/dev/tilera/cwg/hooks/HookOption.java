package dev.tilera.cwg.hooks;

import java.util.Map;

import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IOption;

public class HookOption implements IOption<IHookProvider> {

    private String id;
    private IHookProvider defaultProvider;
    private IHookRegistry registry;

    public HookOption(String id, IHookProvider defaultProvider, IHookRegistry registry) {
        this.id = id;
        this.defaultProvider = defaultProvider;
        this.registry = registry;
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
        return null;
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
        return null;
    }

    @Override
    public IHookProvider decodeString(String input) {
        return null;
    }

    @Override
    public IHookProvider fromRepresentation(String repr) {
        return registry.getHookProvider(repr);
    }

    @Override
    public String toRepresentation(IHookProvider obj) {
        return obj.getID();
    }

    public HookOption registerDefault() {
        registry.registerHookProvider(defaultProvider);
        return this;
    }
    
}
