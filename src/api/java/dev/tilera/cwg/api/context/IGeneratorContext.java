package dev.tilera.cwg.api.context;

import java.util.Optional;

import dev.tilera.cwg.api.options.IGeneratorOptionProvider;

public interface IGeneratorContext {
    
    IGeneratorOptionProvider getOptions();

    <T> Optional<T> getObject(Class<T> type);

}
