package dev.tilera.cwg.api.options;

import dev.tilera.cwg.api.CwgGlobals;

import java.util.Collection;

public interface IGeneratorOptionProvider {
    
    Integer getInt(String id);

    String getString(String id);

    Double getDouble(String id);

    Boolean getBoolean(String id);

    <T> T getValue(String id, Class<T> type);

    Collection<String> getOptions();

}
