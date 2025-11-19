package dev.tilera.cwg.api.reference;

import java.util.function.Consumer;

public interface IMutableReference<T> extends IReference<T> {

    void set(T value);

    void notify(Consumer<T> hook);

}
