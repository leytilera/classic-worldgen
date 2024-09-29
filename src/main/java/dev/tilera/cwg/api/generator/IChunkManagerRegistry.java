package dev.tilera.cwg.api.generator;

import java.util.List;

public interface IChunkManagerRegistry {
    
    void registerChunkManager(IChunkManagerFactory factory);

    IChunkManagerFactory getFactory(String id);

    List<IChunkManagerFactory> getChunkManagers();

}
