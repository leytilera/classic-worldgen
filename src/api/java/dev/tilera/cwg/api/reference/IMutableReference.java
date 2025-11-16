package dev.tilera.cwg.api.reference;

public interface IMutableReference<T> extends IReference<T> {

    void set(T value);

}
