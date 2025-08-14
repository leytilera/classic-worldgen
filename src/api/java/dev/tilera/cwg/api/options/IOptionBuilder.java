package dev.tilera.cwg.api.options;

import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.IHookType;

public interface IOptionBuilder<T> {

    IOptionBuilder<T> setDefault(T def);

    IOptionBuilder<T> setID(String id);

    IOptionBuilder<T> setDisplayName(String displayName);

    IOptionBuilder<T> setInternal();

    IOptionBuilder<T> setGeneratorSpecific();

    IOptionBuilder<IHookProvider> setHookType(IHookType<?> hookType) throws IllegalStateException;

    IOption<T> build() throws IllegalStateException;

}
