package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.RepresentationType;

public class BooleanSerializer<T> implements IObjectSerializer<T, Boolean> {

    private IObjectManipulator<T> manipulator;

    public BooleanSerializer(IObjectManipulator<T> manipulator) {
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(Boolean object) {
        return manipulator.createPrimitive(object);
    }

    @Override
    public Boolean deserialize(T encoded) throws IllegalArgumentException {
        if (!manipulator.isOfType(encoded, RepresentationType.BOOLEAN)) throw new IllegalArgumentException("Encoded object must be a boolean");
        return manipulator.asBoolean(encoded);
    }
    
}
