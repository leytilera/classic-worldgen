package dev.tilera.cwg.options;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dev.tilera.cwg.ClassicWorldgen;
import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOptionBuilder;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;
import dev.tilera.cwg.serialize.GsonSerializer;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GlobalOptionManager implements IGeneratorOptionManager {

    private IObjectSerializer<ISerializedRead, JsonElement> jsonSerialier = new GsonSerializer(
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
    );
    private IGeneratorOptionRegistry optionRegistry;
    private IHookRegistry hookRegistry;
    private Map<UUID, IGeneratorOptionProvider> optionSets = new HashMap<>();
    private Map<UUID, String> optionSetFiles = new HashMap<>();
    private Pair<World, IGeneratorOptionManager> currentWorld = null;

    public GlobalOptionManager(IGeneratorOptionRegistry optionRegistry, IHookRegistry hookRegistry) {
        this.optionRegistry = optionRegistry;
        this.hookRegistry = hookRegistry;
        optionSetFiles.put(DEFAULT, "default");
        optionSetFiles.put(REGISTRY, "builtin");
        optionSetFiles.put(CONFIG, "builtin");
        optionSetFiles.put(CLASSIC, "builtin");
        optionSets.put(REGISTRY, optionRegistry);
        optionSets.put(CLASSIC, ClassicWorldgen.CLASSIC);
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
        return null;
    }

    @Override
    public void saveOptions(UUID optionSet, IGeneratorOptionProvider options) {

    }

    @Override
    public IGeneratorOptionManager getWorldOptionManager(World world) {
        if (currentWorld == null || !currentWorld.getLeft().equals(world)) {
            currentWorld = new ImmutablePair<>(world, new WorldOptionManager(world, this));
        }
        return currentWorld.getRight();
    }
}
