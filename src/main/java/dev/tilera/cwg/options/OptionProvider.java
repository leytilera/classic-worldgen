package dev.tilera.cwg.options;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;

public class OptionProvider implements IGeneratorOptionProvider {

    private IReference<IGeneratorOptionProvider> parent;
    private Map<String, Object> storage = new HashMap<>();

    public OptionProvider(IReference<IGeneratorOptionProvider> parent) {
        this.parent = parent;
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
        if (res == null) res = parent.apply(p -> p.getValue(id, type));
        return res;
    }

    @Override
    public Set<String> getOptions() {
        return storage.keySet();
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
    
}
