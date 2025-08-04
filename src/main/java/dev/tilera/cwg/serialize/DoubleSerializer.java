package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.RepresentationType;

public class DoubleSerializer<T> implements IObjectSerializer<T, Double> {

    private IObjectManipulator<T> manipulator;

    public DoubleSerializer(IObjectManipulator<T> manipulator) {
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(Double object) {
        return manipulator.createPrimitive(object);
    }

    @Override
    public Double deserialize(T encoded) throws IllegalArgumentException {
        if (!manipulator.isOfType(encoded, RepresentationType.DOUBLE)) throw new IllegalArgumentException("Encoded object must be a double");
        return manipulator.asDouble(encoded);
    }
    
}
