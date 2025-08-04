package dev.tilera.cwg.options;

import java.util.HashMap;
import java.util.Map;

import java.util.Set;

import com.google.gson.JsonElement;

import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.serialize.Base64Encoder;
import dev.tilera.cwg.serialize.CombinedSerializer;
import dev.tilera.cwg.serialize.GsonManipulator;
import dev.tilera.cwg.serialize.GsonSerializer;

public class OptionRegistry implements IGeneratorOptionRegistry {
    
    private Map<String, IOption<?>> registry = new HashMap<>();
    private IObjectSerializer<String, JsonElement> gsonSerializer = GsonSerializer.STRING;
    private IObjectSerializer<String, JsonElement> base64JsonSerializer = new CombinedSerializer<>(Base64Encoder.INSTANCE, gsonSerializer);
    private IObjectManipulator<JsonElement> manipulator = new GsonManipulator();
    private IObjectSerializer<JsonElement, IGeneratorOptionProvider> optionSerializer = new OptionSerializer<JsonElement>(manipulator, this, this);
    private IObjectSerializer<String, IGeneratorOptionProvider> base64OptionSerializer = new CombinedSerializer<>(base64JsonSerializer, optionSerializer);

    @Override
    public void registerOption(IOption<?> option) {
        registry.put(option.getID(), option);
    }

    @Override
    public Set<String> getRegisteredOptions() {
        return registry.keySet();
    }

    @Override
    public String encodeOptions(IGeneratorOptionProvider provider) {
        return base64OptionSerializer.serialize(provider);
    }

    @Override
    public IGeneratorOptionProvider decodeOptions(String options) {
        return base64OptionSerializer.deserialize(options);
    }

    @Override
    public <T> boolean isRegistered(String id, Class<T> type) {
        IOption<?> o = registry.get(id);
        return o != null && o.getType() == type;
    }

    @SuppressWarnings("unchecked")
    private <T> T getOption(String id, Class<T> type, T defaultValue) {
        IOption<?> o = registry.get(id);
        if (o == null) {
            return defaultValue;
        } else if (o.getType().isAssignableFrom(type)) {
            return (T) o.getDefault();
        }
        return defaultValue;
    }

    @Override
    public Integer getInt(String id) {
        return getOption(id, Integer.class, null);
    }

    @Override
    public String getString(String id) {
        return getOption(id, String.class, null);
    }

    @Override
    public Double getDouble(String id) {
        return getOption(id, Double.class, null);
    }

    @Override
    public Boolean getBoolean(String id) {
        return getOption(id, Boolean.class, false);
    }

    @Override
    public <T> T getValue(String id, Class<T> type) {
        return getOption(id, type, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> IOption<T> getOption(String id, Class<T> type) {
        IOption<?> opt = registry.get(id);
        if (opt != null && opt.getType() == type) {
            return (IOption<T>) registry.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Class<?> getOptionType(String id) {
        IOption<?> opt = registry.get(id);
        if (opt != null) {
            return opt.getType();
        } else {
            return null;
        }
    }

}
