package dev.tilera.cwg.api.serialize;

public interface IObjectManipulator<T> {

    T createPrimitive(String string);

    T createPrimitive(boolean bool);

    T createPrimitive(int integer);

    T createPrimitive(double d);

    T createNull();

    T createObject();

    T createArray();

    boolean isNull(T object);

    boolean isOfType(T object, RepresentationType type);

    String asString(T object) throws IllegalArgumentException;

    boolean asBoolean(T object) throws IllegalArgumentException;

    int asInteger(T object) throws IllegalArgumentException;

    double asDouble(T object) throws IllegalArgumentException;

    T getProperty(T object, String name) throws IllegalArgumentException;

    T getIndex(T object, int index) throws IllegalArgumentException, IndexOutOfBoundsException;

    T setProperty(T object, String name, T property) throws IllegalArgumentException;

    T setIndex(T object, int index, T value) throws IllegalArgumentException;

    String[] getProperties(T object) throws IllegalArgumentException;
    
    int getSize(T object) throws IllegalArgumentException;
}
