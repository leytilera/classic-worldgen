package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class NullSerializer<T> implements IObjectSerializer<T, T> {

    private static NullSerializer<?> instance = new NullSerializer<>();

    @SuppressWarnings("unchecked")
    public static <T> IObjectSerializer<T, T> instance() {
        return (IObjectSerializer<T, T>) instance;
    }

    @Override
    public T serialize(T object) {
        return object;
    }

    @Override
    public T deserialize(T encoded) throws IllegalArgumentException {
        return encoded;
    }
    
}
