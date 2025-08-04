package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.RepresentationType;

public class IntSerializer<T> implements IObjectSerializer<T, Integer> {

    private IObjectManipulator<T> manipulator;

    public IntSerializer(IObjectManipulator<T> manipulator) {
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(Integer object) {
        return manipulator.createPrimitive(object);
    }

    @Override
    public Integer deserialize(T encoded) throws IllegalArgumentException {
        if (!manipulator.isOfType(encoded, RepresentationType.INTEGER)) throw new IllegalArgumentException("Encoded object must be an integer");
        return manipulator.asInteger(encoded);
    }
    
}
