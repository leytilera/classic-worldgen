package dev.tilera.cwg.api.serialize;

public interface IObjectManipulator<T, I> {
    
    T get(T object, I index) throws IllegalArgumentException, IndexOutOfBoundsException;

    T set(T object, I index, T value) throws IllegalArgumentException;

    int getSize(T object);

    I[] getIndices(T object);

    T create();

    boolean isInstance(T object);

}
