package dev.tilera.cwg.options;

import java.util.function.Function;

public interface IReference<T> {

    T get();

    default boolean isNull() {
        return get() == null;
    }

    default <E> E apply(Function<T, E> function) {
        if (isNull()) return null;
        return function.apply(get());
    }

}
