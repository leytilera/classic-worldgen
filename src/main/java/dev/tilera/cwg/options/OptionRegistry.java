package dev.tilera.cwg.options;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOption;

public class OptionRegistry implements IGeneratorOptionRegistry {
    
    private Map<String, IOption<?>> registry = new HashMap<>();
    private Gson gson = new GsonBuilder().create();

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
        JsonObject json = new JsonObject();
        IChunkManagerFactory generator = provider.getValue("cwg:generator", IHookProvider.class).getHook(HookTypes.GENERATOR);
        for (String id : this.getRegisteredOptions()) {
            IOption<?> o = registry.get(id);
            if (o == null) {
                continue;
            } else if (generator != null && o.isGeneratorSpecific() && !generator.hasSpecificOption(o)) {
                continue;
            } else if (o.getType() == Integer.class) {
                json.add(id, new JsonPrimitive(provider.getInt(id)));
            } else if (o.getType() == Double.class) {
                json.add(id, new JsonPrimitive(provider.getDouble(id)));
            } else if (o.getType() == Boolean.class) {
                json.add(id, new JsonPrimitive(provider.getBoolean(id)));
            } else if (o.getType() == String.class) {
                json.add(id, new JsonPrimitive(provider.getString(id)));
            } else {
                String enc = o.toRepresentation(id, provider);
                json.add(id, new JsonPrimitive(enc));
            }
        }
        String jsn = gson.toJson(json);
        return Base64.getEncoder().encodeToString(jsn.getBytes());
    }

    @Override
    public IGeneratorOptionProvider decodeOptions(String options) {
        String jsn = new String(Base64.getDecoder().decode(options));
        JsonObject json = gson.fromJson(jsn, JsonObject.class);
        OptionProvider provider = new OptionProvider(this);
        for (Entry<String, JsonElement> e : json.entrySet()) {
            if (!e.getValue().isJsonPrimitive()) continue;
            JsonPrimitive value = e.getValue().getAsJsonPrimitive();
            IOption<?> o = registry.get(e.getKey());
            if (o == null) {
                continue;
            } else if (o.getType() == Integer.class && value.isNumber()) {
                provider.putInt(o.getID(), value.getAsInt());
            } else if (o.getType() == Double.class && value.isNumber()) {
                provider.putDouble(o.getID(), value.getAsDouble());
            } else if (o.getType() == Boolean.class && value.isBoolean()) {
                provider.putBoolean(o.getID(), value.getAsBoolean());
            } else if (o.getType() == String.class && value.isString()) {
                provider.putString(o.getID(), value.getAsString());
            } else if (value.isString()) {
                provider.putValue(o.getID(), o.fromRepresentation(value.getAsString()));
            }
        }
        return provider;
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
