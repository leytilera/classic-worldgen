package dev.tilera.cwg.api.serialize;

public interface IObjectSerializer<E, D> {
    
    E serialize(D object);

    D deserialize(E encoded) throws IllegalArgumentException;

}
