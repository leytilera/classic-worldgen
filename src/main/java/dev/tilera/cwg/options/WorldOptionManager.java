package dev.tilera.cwg.options;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOptionBuilder;
import dev.tilera.cwg.api.reference.IReference;
import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import java.util.function.Predicate;

public class WorldOptionManager implements IGeneratorOptionManager {

    private IGeneratorOptionManager global;
    private File cwgFile;
    private UUID optionSet;
    private Map<UUID, IGeneratorOptionProvider> optionSets = new HashMap<>();

    public WorldOptionManager(World world, IGeneratorOptionManager global) {
        this.global = global;
        File worldDir = world.getSaveHandler().getWorldDirectory();
        cwgFile = new File(worldDir, "cwg.json");
        try {
            this.optionSet = UUID.fromString(world.getWorldInfo().getGeneratorOptions());
        } catch(IllegalArgumentException e) {
            this.optionSet = DEFAULT;
        }
    }

    private UUID load(UUID set, Map<UUID, IGeneratorOptionProvider> sets) {
        IGeneratorOptionProvider options;
        if (global.getOptionSets().contains(set)) {
            options = global.getOptions(set).get();
        } else if (sets.containsKey(set)) {
            options = sets.get(set);
        } else {
            throw new RuntimeException("ALEC: Option Set with ID " + set + " does not exist.");
        }
        saveOptions(set, options);
        return options.getValue("cwg:internal:parent", UUID.class);
    }

    @Override
    public void load() throws IOException {
        Map<UUID, IGeneratorOptionProvider> sets;
        if (cwgFile.exists() && cwgFile.isFile()) {
            try {
                Reader file = new FileReader(cwgFile);
                IObjectSerializer<ISerializedRead, Map<UUID, IGeneratorOptionProvider>> serializer = OptionFileManager.INSTANCE.createSerializer(this, false);
                sets = serializer.deserialize(ISerializedRead.fromReader(file));
            } catch (Exception e) {
                throw new IOException(e);
            }
        } else {
            sets = new HashMap<>();
        }
        UUID current = optionSet;
        Predicate<UUID> requiresFetch = (UUID id) -> !DEFAULT.equals(id) && !REGISTRY.equals(id) && !CONFIG.equals(id) && !CLASSIC.equals(id);
        while (requiresFetch.test(current)) {
            current = load(current, sets);
        }
    }

    @Override
    public void save() throws IOException {
        IObjectSerializer<ISerializedRead, Map<UUID, IGeneratorOptionProvider>> serializer = OptionFileManager.INSTANCE.createSerializer(this, false);
        try {
            Writer writer = new FileWriter(cwgFile);
            serializer.serialize(optionSets).write(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IGeneratorOptionRegistry getOptionRegistry() {
        return global.getOptionRegistry();
    }

    @Override
    public IHookRegistry getHookRegistry() {
        return global.getHookRegistry();
    }

    @Override
    public <T> IOptionBuilder<T> builder(Class<T> clazz) {
        return global.builder(clazz);
    }

    @Override
    public IReference<IGeneratorOptionProvider> getOptions(UUID optionSet) {
        IReference<IGeneratorOptionProvider> options = null;
        if (optionSets.containsKey(optionSet)) {
            options = new Pointer<>(optionSets.get(optionSet));
        } else {
            options = global.getOptions(optionSet);
        }
        return options;
    }

    @Override
    public void saveOptions(UUID optionSet, IGeneratorOptionProvider options) {
        optionSets.put(optionSet, options);
        if (!global.getOptionSets().contains(optionSet)) {
            global.saveOptions(optionSet, options);
        }
    }

    @Override
    public Collection<UUID> getOptionSets() {
        return optionSets.keySet();
    }

    @Override
    public IGeneratorOptionManager createWorldOptionManager(World world) {
        return global.createWorldOptionManager(world);
    }

    @Override
    public <E> IObjectSerializer<E, IGeneratorOptionProvider> createSerializer(IObjectType<E> manipulator) {
        return global.createSerializer(manipulator);
    }
}
