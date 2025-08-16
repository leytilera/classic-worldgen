package dev.tilera.cwg.options;

import com.google.gson.JsonElement;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOptionBuilder;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;
import dev.tilera.cwg.serialize.GsonManipulator;
import dev.tilera.cwg.serialize.MapSerializer;
import dev.tilera.cwg.serialize.NullSerializer;
import dev.tilera.cwg.serialize.UUIDSerializer;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.Future;

public class WorldOptionManager implements IGeneratorOptionManager {

    private World world;
    private IGeneratorOptionManager global;
    private File cwgFile;
    private Map<UUID, IGeneratorOptionProvider> optionSets = new HashMap<>();

    public WorldOptionManager(World world, IGeneratorOptionManager global) {
        this.world = world;
        this.global = global;
        File worldDir = world.getSaveHandler().getWorldDirectory();
        cwgFile = new File(worldDir, "cwg.json");
    }

    public void load() throws IOException {
        if (cwgFile.exists() && cwgFile.isFile()) {
            try (Reader file = new FileReader(cwgFile)) {
                JsonElement content = GlobalOptionManager.JSON_SERIALIZER.deserialize(ISerializedRead.fromReader(file));
                IObjectSerializer<JsonElement, Map<UUID, JsonElement>> serializer = new MapSerializer<>(
                        new GsonManipulator(),
                        UUIDSerializer.INSTANCE,
                        NullSerializer.instance()
                );
                Map<UUID, JsonElement> sets = serializer.deserialize(content);
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }

    public void save() throws IOException {

    }

    @Override
    public IGeneratorOptionRegistry getOptionRegistry() {
        return global.getOptionRegistry();
    }

    @Override
    public IHookRegistry getHookRegistry() {
        return getHookRegistry();
    }

    @Override
    public <T> IOptionBuilder<T> builder(Class<T> clazz) {
        return global.builder(clazz);
    }

    @Override
    public IGeneratorOptionProvider getOptions(UUID optionSet) {
        IGeneratorOptionProvider options = null;
        if (global.getOptionSets().contains(optionSet)) {
            options = global.getOptions(optionSet);
        } else {
            options = optionSets.get(optionSet);
        }
        if (options == null) {
            options = global.getOptions(optionSet);
        }
        return options;
    }

    @Override
    public void saveOptions(UUID optionSet, IGeneratorOptionProvider options) {
        optionSets.put(optionSet, options);
    }

    @Override
    public Collection<UUID> getOptionSets() {
        return optionSets.keySet();
    }

    @Override
    public Future<IGeneratorOptionManager> createWorldOptionManager(World world) {
        return global.createWorldOptionManager(world);
    }

    @Override
    public <E> IObjectSerializer<E, IGeneratorOptionProvider> createSerializer(IObjectManipulator<E> manipulator) {
        return global.createSerializer(manipulator);
    }
}
