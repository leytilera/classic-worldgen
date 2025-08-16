package dev.tilera.cwg.options;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dev.tilera.cwg.ClassicWorldgen;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOptionBuilder;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;
import dev.tilera.cwg.serialize.GsonSerializer;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class GlobalOptionManager implements IGeneratorOptionManager {

    public static IObjectSerializer<ISerializedRead, JsonElement> JSON_SERIALIZER = new GsonSerializer(
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
    );
    private IGeneratorOptionRegistry optionRegistry;
    private IHookRegistry hookRegistry;
    private Map<UUID, IGeneratorOptionProvider> optionSets = new HashMap<>();
    private Map<UUID, String> optionSetFiles = new HashMap<>();
    private Map<UUID, IMutableReference<IGeneratorOptionProvider>> missing = new HashMap<>();

    public GlobalOptionManager(IGeneratorOptionRegistry optionRegistry, IHookRegistry hookRegistry) {
        this.optionRegistry = optionRegistry;
        this.hookRegistry = hookRegistry;
        optionSetFiles.put(DEFAULT, "default");
        optionSetFiles.put(REGISTRY, "builtin");
        optionSetFiles.put(CONFIG, "builtin");
        optionSetFiles.put(CLASSIC, "builtin");
        optionSets.put(REGISTRY, optionRegistry);
        optionSets.put(CLASSIC, ClassicWorldgen.CLASSIC);
        optionSets.put(CONFIG, ClassicWorldgen.CONFIG);
    }

    @Override
    public IGeneratorOptionRegistry getOptionRegistry() {
        return this.optionRegistry;
    }

    @Override
    public IHookRegistry getHookRegistry() {
        return this.hookRegistry;
    }

    @Override
    public <T> IOptionBuilder<T> builder(Class<T> clazz) {
        return null;
    }

    @Override
    public IGeneratorOptionProvider getOptions(UUID optionSet) {
        IGeneratorOptionProvider options = optionSets.get(optionSet);
        if (options == null) {
            options = optionSets.get(REGISTRY);
        }
        return options;
    }

    @Override
    public void saveOptions(UUID optionSet, IGeneratorOptionProvider options) {
        optionSets.put(optionSet, options);
        if (!optionSetFiles.containsKey(optionSet)) {
            optionSetFiles.put(optionSet, "generated");
        }
        if (missing.containsKey(optionSet)) {
            IMutableReference<IGeneratorOptionProvider> ref = missing.remove(optionSet);
            ref.set(options);
        }
    }

    @Override
    public Collection<UUID> getOptionSets() {
        return optionSets.keySet();
    }

    @Override
    public Future<IGeneratorOptionManager> createWorldOptionManager(World world) {
        WorldOptionManager manager = new WorldOptionManager(world, this);
        FutureTask<IGeneratorOptionManager> task = new FutureTask<>(() -> {
            manager.load();
            return manager;
        });
        task.run();
        return task;
    }

    @Override
    public <E> IObjectSerializer<E, IGeneratorOptionProvider> createSerializer(IObjectManipulator<E> manipulator) {
        return new OptionSerializerV2<>(manipulator, this);
    }

    public IReference<IGeneratorOptionProvider> getReference(UUID id) {
        if (optionSets.containsKey(id)) {
            return new Pointer<>(optionSets.get(id));
        } else if (missing.containsKey(id)) {
            return missing.get(id);
        } else {
            missing.put(id, new Pointer<>(optionRegistry));
            return missing.get(id);
        }
    }
}
