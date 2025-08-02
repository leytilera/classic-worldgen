package dev.tilera.cwg.api.hooks;

import dev.tilera.cwg.api.options.IOption;

public interface IHookOption<T extends IHookProvider> extends IOption<T> {
    
    IHookType<?> getHookType();

}
