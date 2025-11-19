package dev.tilera.cwg.options;

import java.util.Collection;
import java.util.Collections;

import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;

public class DefaultOptionProvider implements IGeneratorOptionProvider {

    private IGeneratorOptionProvider registry;

    public DefaultOptionProvider(IGeneratorOptionProvider registry) {
        this.registry = registry;
    }

    @Override
    public Integer getInt(String id) {
        return registry.getInt(id);
    }

    @Override
    public String getString(String id) {
        return registry.getString(id);
    }

    @Override
    public Double getDouble(String id) {
        return registry.getDouble(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        return registry.getBoolean(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue(String id, Class<T> type) {
        if ("cwg:internal:parent".equals(id)) {
            return (T) IGeneratorOptionManager.CONFIG;
        }
        return registry.getValue(id, type);
    }

    @Override
    public Collection<String> getOptions() {
        return Collections.singleton("cwg:internal:parent");
    }

    @Override
    public IGeneratorOptionProvider copy() {
        return this;
    }
    
}
