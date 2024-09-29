package dev.tilera.cwg.options;

import java.util.HashMap;
import java.util.Map;

import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.generator.IChunkManagerRegistry;
import dev.tilera.cwg.api.options.IOption;

public class ChunkManagerOption implements IOption<IChunkManagerFactory> {

    private String id;
    private String displayName;
    private IChunkManagerFactory defaultValue;
    private IChunkManagerRegistry registry;

    public ChunkManagerOption(String id, String displayName, IChunkManagerFactory defaultValue,
            IChunkManagerRegistry registry) {
        this.id = id;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
        this.registry = registry;
    }

    @Override
    public IChunkManagerFactory getDefault() {
        return defaultValue;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getVisableName() {
        return displayName;
    }

    @Override
    public Class<IChunkManagerFactory> getType() {
        return IChunkManagerFactory.class;
    }

    @Override
    public Map<IChunkManagerFactory, String> getPossibleValues() {
        Map<IChunkManagerFactory, String> map = new HashMap<>();
        for (IChunkManagerFactory m : registry.getChunkManagers()) {
            map.put(m, m.getDisplayName());
        }
        return map;
    }

    @Override
    public IChunkManagerFactory decodeString(String input) {
        return null;
    }

    @Override
    public IChunkManagerFactory fromRepresentation(String repr) {
        return registry.getFactory(repr);
    }

    @Override
    public String toRepresentation(IChunkManagerFactory obj) {
        return obj.getID();
    }

    @Override
    public Type getOptionType() {
        return Type.ENUM;
    }
    
}
