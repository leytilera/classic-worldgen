package dev.tilera.cwg.options;

import java.util.HashMap;
import java.util.Map;

import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;

public class OptionProvider implements IGeneratorOptionProvider {

    private IGeneratorOptionProvider registry;
    private Map<String, Object> storage = new HashMap<>();

    public OptionProvider(IGeneratorOptionProvider registry) {
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
    private <T> T getAs(Class<T> type, String id) {
        Object o = storage.get(id);
        if (o == null) {
            return null;
        } else if (type.isAssignableFrom(o.getClass())) {
            return (T) o;
        }
        return null;
    }

    @Override
    public Integer getInt(String id) {
        return getValue(id, Integer.class);
    }

    @Override
    public String getString(String id) {
        return getValue(id, String.class);
    }

    @Override
    public Double getDouble(String id) {
        return getValue(id, Double.class);
    }

    @Override
    public Boolean getBoolean(String id) {
        return getValue(id, Boolean.class);
    }

    @Override
    public <T> T getValue(String id, Class<T> type) {
        T res = getAs(type, id);
        if (res == null) res = registry.getValue(id, type);
        return res;
    }

    public void putInt(String id, Integer value) {
        storage.put(id, value);
    }

    public void putDouble(String id, Double value) {
        storage.put(id, value);
    }

    public void putString(String id, String value) {
        storage.put(id, value);
    }

    public void putBoolean(String id, Boolean value) {
        storage.put(id, value);
    }

    public void putValue(String id, Object value) {
        storage.put(id, value);
    }

    @Override
    public IGeneratorOptionRegistry getRegistry() {
        return registry.getRegistry();
    }
    
}
