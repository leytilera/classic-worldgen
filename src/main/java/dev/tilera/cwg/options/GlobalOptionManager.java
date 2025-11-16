package dev.tilera.cwg.options;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import cpw.mods.fml.common.Loader;
import dev.tilera.cwg.ClassicWorldgen;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOptionBuilder;
import dev.tilera.cwg.api.reference.IMutableReference;
import dev.tilera.cwg.api.reference.IReference;
import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;
import dev.tilera.cwg.serialize.GsonSerializer;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import org.apache.commons.io.FilenameUtils;

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
    private File cwgDir;

    public GlobalOptionManager(IGeneratorOptionRegistry optionRegistry, IHookRegistry hookRegistry) {
        this.optionRegistry = optionRegistry;
        this.hookRegistry = hookRegistry;
        this.cwgDir = new File(Loader.instance().getConfigDir(), "cwg");
        cwgDir.mkdirs();
        optionSetFiles.put(DEFAULT, "default");
        optionSetFiles.put(REGISTRY, "builtin");
        optionSetFiles.put(CONFIG, "builtin");
        optionSetFiles.put(CLASSIC, "builtin");
        optionSets.put(REGISTRY, optionRegistry);
        optionSets.put(CLASSIC, ClassicWorldgen.CLASSIC);
        optionSets.put(CONFIG, ClassicWorldgen.CONFIG);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> this.save()));
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
    public IGeneratorOptionManager createWorldOptionManager(World world) {
        return !world.isRemote ? new WorldOptionManager(world, this) : new ClientOptionManager(this);
    }

    @Override
    public <E> IObjectSerializer<E, IGeneratorOptionProvider> createSerializer(IObjectType<E> manipulator) {
        return new OptionSerializerV2<>(manipulator, this);
    }

    @Override
    public IReference<IGeneratorOptionProvider> getOptions(UUID id) {
        if (optionSets.containsKey(id)) {
            return new Pointer<>(optionSets.get(id));
        } else if (missing.containsKey(id)) {
            return missing.get(id);
        } else if (DEFAULT.equals(id)) {
            missing.put(id, new Pointer<>(new DefaultOptionProvider(ClassicWorldgen.CONFIG)));
            return missing.get(id);
        } else {
            missing.put(id, new Pointer<>(optionRegistry));
            return missing.get(id);
        }
    }

    @Override
    public void load() throws IOException {
        IObjectSerializer<ISerializedRead, Map<UUID, IGeneratorOptionProvider>> serializer = OptionFileManager.INSTANCE.createSerializer(this, true);
        File[] files = cwgDir.listFiles((file) -> 
            !file.isDirectory()
            && file.getName().endsWith(".json") 
            && !file.getName().equals("dimensions.json")
            && !file.getName().equals("builtin.json")
        );
        for (File file : files) {
            String name = FilenameUtils.getBaseName(file.getName());
            Map<UUID, IGeneratorOptionProvider> content = serializer.deserialize(ISerializedRead.fromReader(new FileReader(file)));
            content.forEach((k, v) -> {
                if (!optionSets.containsKey(k)) {
                    optionSetFiles.put(k, name);
                    saveOptions(k, v);
                }
            });
        }
    }

    @Override
    public void save() {
        IObjectSerializer<ISerializedRead, Map<UUID, IGeneratorOptionProvider>> serializer = OptionFileManager.INSTANCE.createSerializer(this, true);
        Map<String, Map<UUID, IGeneratorOptionProvider>> files = new HashMap<>();
        optionSetFiles.forEach((k, v) -> {
            IGeneratorOptionProvider options = getOptions(k).get();
            if (!files.containsKey(v)) {
                files.put(v, new HashMap<>());
            }
            files.get(v).put(k, options);
        });
        files.forEach((k, v) -> {
            File file = new File(cwgDir, k + ".json");
            try {
                Writer writer = new FileWriter(file);
                serializer.serialize(v).write(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
