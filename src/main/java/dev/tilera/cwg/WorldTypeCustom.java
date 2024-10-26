package dev.tilera.cwg;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.generator.IChunkManagerRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.options.OptionProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldTypeCustom extends WorldType implements IGeneratorOptionRegistry, IChunkManagerRegistry {

    private Map<String, IOption<?>> optionRegistry = new HashMap<>();
    private Map<String, IChunkManagerFactory> chunkManagerRegistry = new HashMap<>();
    private Gson gson = new GsonBuilder().create();

    public WorldTypeCustom() {
        super("cwg");
    }

    @Override
    public WorldChunkManager getChunkManager(World world) {
        String opts = world.getWorldInfo().getGeneratorOptions();
        IGeneratorOptionProvider options;
        if (opts.isEmpty()) {
            options = this;
        } else {
            try {
                options = decodeOptions(opts);
            } catch (Exception e) {
                e.printStackTrace();
                options = this;
            }
        }
        AbstractChunkManager manager = options.getValue("cwg:generator", IChunkManagerFactory.class).createChunkManager(options, world);
        CwgGlobals.setCurrentState(world);
        return manager;
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        if (world.provider.worldChunkMgr instanceof AbstractChunkManager) {
            return ((AbstractChunkManager)world.provider.worldChunkMgr).getGenerator(world);
        } else {
            throw new RuntimeException("Invalid WorldChunkManager");
        }
    }

    @Override
    public void registerOption(IOption<?> option) {
        optionRegistry.put(option.getID(), option);
    }

    @Override
    public Set<String> getRegisteredOptions() {
        return optionRegistry.keySet();
    }

    @Override
    public String encodeOptions(IGeneratorOptionProvider provider) {
        JsonObject json = new JsonObject();
        IChunkManagerFactory generator = provider.getValue("cwg:generator", IChunkManagerFactory.class);
        for (String id : this.getRegisteredOptions()) {
            IOption<?> o = optionRegistry.get(id);
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
            IOption<?> o = optionRegistry.get(e.getKey());
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
        IOption<?> o = optionRegistry.get(id);
        return o != null && o.getType() == type;
    }

    @SuppressWarnings("unchecked")
    private <T> T getOption(String id, Class<T> type, T defaultValue) {
        IOption<?> o = optionRegistry.get(id);
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

    @Override
    public void registerChunkManager(IChunkManagerFactory factory) {
        chunkManagerRegistry.put(factory.getID(), factory);
    }

    @Override
    public IChunkManagerFactory getFactory(String id) {
        return chunkManagerRegistry.get(id);
    }

    @Override
    public List<IChunkManagerFactory> getChunkManagers() {
        return chunkManagerRegistry.values().stream().collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> IOption<T> getOption(String id, Class<T> type) {
        IOption<?> opt = optionRegistry.get(id);
        if (opt != null && opt.getType() == type) {
            return (IOption<T>) optionRegistry.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Class<?> getOptionType(String id) {
        IOption<?> opt = optionRegistry.get(id);
        if (opt != null) {
            return opt.getType();
        } else {
            return null;
        }
    }

}
