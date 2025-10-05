package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.RepresentationType;

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
        if (!manipulator.isOfType(encoded, RepresentationType.FLOAT)) throw new IllegalArgumentException("Encoded object must be a double");
        return manipulator.floats().deserialize(encoded);
    }
    
}
