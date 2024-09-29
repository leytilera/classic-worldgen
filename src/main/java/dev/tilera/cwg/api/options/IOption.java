package dev.tilera.cwg.api.options;

import java.util.Map;

public interface IOption<T> {
    
    T getDefault();

    String getID();

    String getVisableName();

    Class<T> getType();

    Type getOptionType();

    Map<T, String> getPossibleValues();

    T decodeString(String input);

    T fromRepresentation(String repr);

    String toRepresentation(T obj);

    default String toRepresentation(String id, IGeneratorOptionProvider provider) {
        T obj = provider.getValue(id, getType());
        return toRepresentation(obj);
    }

    default boolean isGeneratorSpecific() {
        return false;
    }

    public static enum Type {
        STRING,
        ENUM,
        INTERNAL;
    }

}
