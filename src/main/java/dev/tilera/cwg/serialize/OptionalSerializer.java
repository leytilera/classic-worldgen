package dev.tilera.cwg.serialize;

import java.util.Optional;

import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class OptionalSerializer<T, S> implements IObjectSerializer<T, Optional<S>> {

    IObjectSerializer<T, S> subSerializer;
    IObjectManipulator<T> manipulator;

    public OptionalSerializer(IObjectSerializer<T, S> subSerializer, IObjectManipulator<T> manipulator) {
        this.subSerializer = subSerializer;
        this.manipulator = manipulator;
    }

    @Override
    public T serialize(Optional<S> object) {
        if (object.isPresent()) {
            return subSerializer.serialize(object.get());
        } else {
            return manipulator.createNull();
        }
    }

    @Override
    public Optional<S> deserialize(T encoded) throws IllegalArgumentException {
        if (manipulator.isNull(encoded)) {
            return Optional.empty();
        } else {
            return Optional.of(subSerializer.deserialize(encoded));
        }
    }
    
}
