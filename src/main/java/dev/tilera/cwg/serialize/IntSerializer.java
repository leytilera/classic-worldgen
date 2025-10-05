package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.RepresentationType;

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
        if (!manipulator.isOfType(encoded, RepresentationType.INTEGER)) throw new IllegalArgumentException("Encoded object must be an integer");
        return manipulator.integers().deserialize(encoded).intValue();
    }
    
}
