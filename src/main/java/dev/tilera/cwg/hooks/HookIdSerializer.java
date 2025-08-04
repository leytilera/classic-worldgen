package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.RepresentationType;

public class HookIdSerializer<T> implements IObjectSerializer<T, IHookProvider> {

    private IHookRegistry registry;
    private IObjectManipulator<T> manipulator;
    
    public HookIdSerializer(IHookRegistry registry, IObjectManipulator<T> manipulator) {
        this.registry = registry;
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(IHookProvider object) {
        return manipulator.createPrimitive(object.getID());
    }

    @Override
    public IHookProvider deserialize(T encoded) throws IllegalArgumentException {
        if (!manipulator.isOfType(encoded, RepresentationType.STRING)) throw new IllegalArgumentException("Object must be a string");
        String id = manipulator.asString(encoded);
        IHookProvider provider = registry.getHookProvider(id);
        if (provider == null) throw new IllegalArgumentException("No HookProvider with ID " + id);
        return provider;
    }
    
}
