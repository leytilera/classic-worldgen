package dev.tilera.cwg.dimensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dev.tilera.cwg.Config;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;
import dev.tilera.cwg.api.utils.IntOption;
import dev.tilera.cwg.api.utils.StringOption;
import dev.tilera.cwg.modules.IModule;
import dev.tilera.cwg.options.OptionSerializer;
import dev.tilera.cwg.serialize.Base64Encoder;
import dev.tilera.cwg.serialize.CombinedSerializer;
import dev.tilera.cwg.serialize.GsonManipulator;
import dev.tilera.cwg.serialize.GsonSerializer;
import net.minecraftforge.common.DimensionManager;

public class CustomDimensions implements IModule {

    public static CustomDimensions INSTANCE;
    private Map<Integer, IGeneratorOptionProvider> dimensions = new HashMap<>();
    private IObjectManipulator<JsonElement> manipulator = new GsonManipulator();
    private IObjectSerializer<JsonElement, IGeneratorOptionProvider> optionSerializer;
    private IObjectSerializer<String, JsonElement> base64JsonSerializer = new CombinedSerializer<>(Base64Encoder.INSTANCE, GsonSerializer.STRING);
    private IObjectSerializer<String, IGeneratorOptionProvider> base64OptionSerializer;

    public CustomDimensions(IGeneratorOptionRegistry registry) {
        this.optionSerializer = new OptionSerializer<>(manipulator, registry, registry);
        this.base64OptionSerializer = new CombinedSerializer<>(base64JsonSerializer, optionSerializer);
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
        JsonArray dims = decodeConfig(file);
        for (JsonElement dim : dims) {
            if (!dim.isJsonObject()) continue;
            Integer id = getDimID(dim.getAsJsonObject());
            IGeneratorOptionProvider options = decodeOptions(dim.getAsJsonObject());
            dimensions.put(id, options);
        }
    }

    private JsonArray decodeConfig(File file) {
        try {
            return GsonSerializer.RW.deserialize(ISerializedRead.fromReader(new FileReader(file))).getAsJsonArray();
        } catch (FileNotFoundException e) {
            return new JsonArray();
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    private int getDimID(JsonObject def) throws IllegalStateException {
        JsonElement id = def.get("id");
        if (id == null || !id.isJsonPrimitive()) throw new IllegalStateException("Dimension definition does not contain a valid dimension ID: " + def);
        return id.getAsInt();
    }

    private IGeneratorOptionProvider decodeOptions(JsonObject def) throws IllegalStateException, IllegalArgumentException {
        JsonElement options = def.get("options");
        if (options == null) {
            throw new IllegalStateException("Dimension definition does not contain valid options: " + def);
        } else if (options.isJsonObject()) {
            return optionSerializer.deserialize(options);
        } else if (options.isJsonPrimitive() && options.getAsJsonPrimitive().isString()) {
            return base64OptionSerializer.deserialize(options.getAsString());
        } else {
            throw new IllegalStateException("Dimension definition does not contain valid options: " + def);
        }
    }

    @Override
    public void registerGenerators(IHookRegistry registry) {}

    @Override
    public void registerOptions(IGeneratorOptionRegistry registry) {
        registry.registerOption(new StringOption("cwg:dimensions:name", "Dimension Name", "Custom Dimension", true, false));
        registry.registerOption(new IntOption("cwg:dimensions:provider", "Provider ID", Config.dimensionProviderID, true, false));
    }

    @Override
    public void registerHooks(IHookRegistry registry) {}
    
}
