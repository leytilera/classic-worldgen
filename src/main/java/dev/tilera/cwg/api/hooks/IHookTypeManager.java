package dev.tilera.cwg.api.hooks;

public interface IHookTypeManager {
    
    void addHookType(IHookType<?> type);

    <T> IHookType<T> getHookType(Class<T> clazz);

    <T> IHookType<T> createHookType(Class<T> clazz);

    boolean hasHookType(Class<?> clazz);

}
