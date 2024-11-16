package dev.tilera.cwg.options;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.generator.IChunkManagerRegistry;

public class GeneratorRegistry implements IChunkManagerRegistry {

    private Map<String, IChunkManagerFactory> registry = new HashMap<>();

    @Override
    public void registerChunkManager(IChunkManagerFactory factory) {
        registry.put(factory.getID(), factory);
    }

    @Override
    public IChunkManagerFactory getFactory(String id) {
        return registry.get(id);
    }

    @Override
    public List<IChunkManagerFactory> getChunkManagers() {
        return registry.values().stream().collect(Collectors.toList());
    }
    
}
