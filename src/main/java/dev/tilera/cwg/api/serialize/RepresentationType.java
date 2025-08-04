package dev.tilera.cwg.api.serialize;

public enum RepresentationType {
    STRING(String.class),
    INTEGER(Integer.class),
    BOOLEAN(Boolean.class),
    DOUBLE(Double.class),
    OBJECT(Object.class),
    ARRAY(Object[].class);

    public final Class<?> type;

    RepresentationType(Class<?> clazz) {
        type = clazz;
    }
}
