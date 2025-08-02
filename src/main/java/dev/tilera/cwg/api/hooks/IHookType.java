package dev.tilera.cwg.api.hooks;

public interface IHookType<T> {
    
    Class<T> getHookClass();

}
