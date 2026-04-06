package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class IntSerializer<T> implements IObjectSerializer<T, Integer> {

    private IObjectType<T> manipulator;

    public IntSerializer(IObjectType<T> manipulator) {
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(Integer object) {
        return manipulator.integers().serialize(object.longValue());
    }

    @Override
    public Integer deserialize(T encoded) throws IllegalArgumentException {
        if (!this.canDeserialize(encoded)) throw new IllegalArgumentException("Encoded object must be an integer");
        return manipulator.integers().deserialize(encoded).intValue();
    }

    @Override
    public boolean canDeserialize(T encoded) {
        return manipulator.integers().canDeserialize(encoded);   
    }
    
}
