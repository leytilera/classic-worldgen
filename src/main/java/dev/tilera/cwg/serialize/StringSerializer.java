package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.RepresentationType;

public class StringSerializer<T> implements IObjectSerializer<T, String> {

    private IObjectManipulator<T> manipulator;

    public StringSerializer(IObjectManipulator<T> manipulator) {
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(String object) {
        return manipulator.createPrimitive(object);
    }

    @Override
    public String deserialize(T encoded) throws IllegalArgumentException {
        if (!manipulator.isOfType(encoded, RepresentationType.STRING)) throw new IllegalArgumentException("Encoded object must be a string");
        return manipulator.asString(encoded);
    }
    
}
