package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class HookIdSerializer<T> implements IObjectSerializer<T, IHookProvider> {

    private IHookRegistry registry;
    private IObjectType<T> manipulator;
    
    public HookIdSerializer(IHookRegistry registry, IObjectType<T> manipulator) {
        this.registry = registry;
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(IHookProvider object) {
        return manipulator.strings().serialize(object.getID());
    }

    @Override
    public IHookProvider deserialize(T encoded) throws IllegalArgumentException {
        if (!this.canDeserialize(encoded)) throw new IllegalArgumentException("Object must be a string");
        String id = manipulator.strings().deserialize(encoded);
        IHookProvider provider = registry.getHookProvider(id);
        if (provider == null) throw new IllegalArgumentException("No HookProvider with ID " + id);
        return provider;
    }

    @Override
    public boolean canDeserialize(T encoded) {
        return manipulator.strings().canDeserialize(encoded);
    }
    
}
