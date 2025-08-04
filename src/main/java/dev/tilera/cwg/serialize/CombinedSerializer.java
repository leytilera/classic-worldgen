package dev.tilera.cwg.serialize;

import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class CombinedSerializer<E, I, D> implements IObjectSerializer<E, D> {

    private IObjectSerializer<E, I> backend;
    private IObjectSerializer<I, D> frontend;

    public CombinedSerializer(IObjectSerializer<E, I> backend, IObjectSerializer<I, D> frontend) {
        this.backend = backend;
        this.frontend = frontend;
    }

    @Override
    public E serialize(D object) {
        return backend.serialize(frontend.serialize(object));
    }

    @Override
    public D deserialize(E encoded) throws IllegalArgumentException {
        return frontend.deserialize(backend.deserialize(encoded));
    }
    
}
