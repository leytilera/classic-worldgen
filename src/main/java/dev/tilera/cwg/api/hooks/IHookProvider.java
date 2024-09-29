package dev.tilera.cwg.api.hooks;

public interface IHookProvider {

    @SuppressWarnings("unchecked")
    default <T> T getHook(Class<T> clazz) {
        return (T) this;
    }

    default String getID() {
        return this.getClass().getCanonicalName();
    }
    
}
