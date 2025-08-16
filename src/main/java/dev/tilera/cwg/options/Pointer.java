package dev.tilera.cwg.options;

public class Pointer<T> implements IMutableReference<T> {

    private T value;

    public Pointer() {
        value = null;
    }

    public Pointer(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

}
