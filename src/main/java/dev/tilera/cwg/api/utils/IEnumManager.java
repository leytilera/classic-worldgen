package dev.tilera.cwg.api.utils;

public interface IEnumManager<T extends Enum<T>> {
    
    String getDisplayName(T value);

    int toNumeric(T value);

    T fromNumeric(int value);

    @SuppressWarnings("unchecked")
    default int toNumeric(Object value) {
        return toNumeric((T)value);
    }

    Class<T> getEnumClass();

}