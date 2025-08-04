package dev.tilera.cwg.api.utils;

import dev.tilera.cwg.api.serialize.IObjectSerializer;

public interface IEnumManager<T extends Enum<T>> extends IObjectSerializer<Integer, T> {
    
    String getDisplayName(T value);

    @SuppressWarnings("unchecked")
    default int toNumeric(Object value) {
        return serialize((T)value);
    }

    Class<T> getEnumClass();
    

}