package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.RepresentationType;

public class StringSerializer<T> implements IObjectSerializer<T, String> {

    private IObjectType<T> manipulator;

    public StringSerializer(IObjectType<T> manipulator) {
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(String object) {
        return manipulator.strings().serialize(object);
    }

    @Override
    public String deserialize(T encoded) throws IllegalArgumentException {
        if (!manipulator.isOfType(encoded, RepresentationType.STRING)) throw new IllegalArgumentException("Encoded object must be a string");
        return manipulator.strings().deserialize(encoded);
    }
    
}
