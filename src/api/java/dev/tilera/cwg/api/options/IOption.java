package dev.tilera.cwg.api.options;

import java.util.Map;

import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

public interface IOption<T> {
    
    T getDefault();

    String getID();

    String getVisableName();

    Class<T> getType();

    Type getOptionType();

    Map<T, String> getPossibleValues();

    T decodeString(String input);

    <E> IObjectSerializer<E, T> getSerializer(IObjectType<E> manipulator);

    default boolean isGeneratorSpecific() {
        return false;
    }

    public static enum Type {
        STRING,
        ENUM,
        INTERNAL;
    }

}
