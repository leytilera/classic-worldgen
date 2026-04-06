package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class DoubleSerializer<T> implements IObjectSerializer<T, Double> {

    private IObjectType<T> manipulator;

    public DoubleSerializer(IObjectType<T> manipulator) {
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(Double object) {
        return manipulator.floats().serialize(object);
    }

    @Override
    public Double deserialize(T encoded) throws IllegalArgumentException {
        if (!this.canDeserialize(encoded)) throw new IllegalArgumentException("Encoded object must be a double");
        return manipulator.floats().deserialize(encoded);
    }

    @Override
    public boolean canDeserialize(T encoded) {
        return manipulator.floats().canDeserialize(encoded);
    }
    
}
