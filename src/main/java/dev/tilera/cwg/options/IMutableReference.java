package dev.tilera.cwg.options;

public interface IMutableReference<T> extends IReference<T> {

    void set(T value);

}
