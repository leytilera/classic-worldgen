package dev.tilera.cwg.options;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOptionBuilder;
import dev.tilera.cwg.api.reference.IReference;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.IObjectType;
import net.minecraft.world.World;

public class ClientOptionManager implements IGeneratorOptionManager {

    private IGeneratorOptionManager inner;

    public ClientOptionManager(IGeneratorOptionManager inner) {
        this.inner = inner;
    }

    @Override
    public IGeneratorOptionRegistry getOptionRegistry() {
        return inner.getOptionRegistry();
    }

    @Override
    public IHookRegistry getHookRegistry() {
        return inner.getHookRegistry();
    }

    @Override
    public <T> IOptionBuilder<T> builder(Class<T> clazz) {
        return inner.builder(clazz);
    }

    @Override
    public IReference<IGeneratorOptionProvider> getOptions(UUID optionSet) {
        return inner.getOptions(optionSet);
    }

    @Override
    public void saveOptions(UUID optionSet, IGeneratorOptionProvider options) {
        
    }

    @Override
    public Collection<UUID> getOptionSets() {
        return inner.getOptionSets();
    }

    @Override
    public IGeneratorOptionManager createWorldOptionManager(World world) {
        return inner.createWorldOptionManager(world);
    }

    @Override
    public <E> IObjectSerializer<E, IGeneratorOptionProvider> createSerializer(IObjectType<E> manipulator) {
        return inner.createSerializer(manipulator);
    }

    @Override
    public void load() throws IOException {
        
    }

    @Override
    public void save() throws IOException {
        
    }
    
}
