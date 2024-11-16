package dev.tilera.cwg.dimensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import dev.tilera.cwg.Config;
import dev.tilera.cwg.api.generator.IChunkManagerRegistry;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.utils.IntOption;
import dev.tilera.cwg.api.utils.StringOption;
import dev.tilera.cwg.modules.IModule;
import net.minecraftforge.common.DimensionManager;

public class CustomDimensions implements IModule {

    public static CustomDimensions INSTANCE;
    private Gson gson = new GsonBuilder().create();
    private IGeneratorOptionRegistry registry;
    private Map<Integer, IGeneratorOptionProvider> dimensions = new HashMap<>();

    public CustomDimensions(IGeneratorOptionRegistry registry) {
        this.registry = registry;
    }

    public IGeneratorOptionProvider getDimensionOptions(int id) {
        return dimensions.get(id);
    }

    public Integer[] getCustomDimensions() {
        return dimensions.keySet().toArray(new Integer[0]);
    }

    public void registerDimensions() {
        for (int id : getCustomDimensions()) {
            IGeneratorOptionProvider options = getDimensionOptions(id);
            int providerId = options.getInt("cwg:dimensions:provider");
            DimensionManager.registerDimension(id, providerId);
        }
    }

    public void readConfig(File file) {
        JsonObject[] dims = decodeConfig(file);
        for (JsonObject dim : dims) {
            Integer id = getDimID(dim);
            IGeneratorOptionProvider options = decodeOptions(dim);
            dimensions.put(id, options);
        }
    }

    private JsonObject[] decodeConfig(File file) {
        try {
            return gson.fromJson(new FileReader(file), JsonObject[].class);
        } catch (FileNotFoundException e) {
            return new JsonObject[0];
        } catch (JsonSyntaxException | JsonIOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getDimID(JsonObject def) throws IllegalStateException {
        JsonElement id = def.get("id");
        if (id == null || !id.isJsonPrimitive()) throw new IllegalStateException("Dimension definition does not contain a valid dimension ID: " + def);
        return id.getAsInt();
    }

    private IGeneratorOptionProvider decodeOptions(JsonObject def) throws IllegalStateException {
        JsonElement options = def.get("options");
        String encodedJson = null;
        if (options == null) {
            throw new IllegalStateException("Dimension definition does not contain valid options: " + def);
        } else if (options.isJsonObject()) {
            encodedJson = Base64.getEncoder().encodeToString(gson.toJson(options).getBytes());
        } else if (options.isJsonPrimitive() && options.getAsJsonPrimitive().isString()) {
            encodedJson = options.getAsJsonPrimitive().getAsString();
        } else {
            throw new IllegalStateException("Dimension definition does not contain valid options: " + def);
        }
        return registry.decodeOptions(encodedJson);
    }

    @Override
    public void registerGenerators(IChunkManagerRegistry registry) {}

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {
        registry.registerOption(new StringOption("cwg:dimensions:name", "Dimension Name", "Custom Dimension", true, false));
        registry.registerOption(new IntOption("cwg:dimensions:provider", "Provider ID", Config.dimensionProviderID, true, false));
    }

    @Override
    public void registerHooks(IHookRegistry registry) {}
    
}
