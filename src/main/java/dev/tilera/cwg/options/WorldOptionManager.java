package dev.tilera.cwg.options;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOptionBuilder;
import net.minecraft.world.World;

import java.util.UUID;

public class WorldOptionManager implements IGeneratorOptionManager {

    private World world;
    private IGeneratorOptionManager global;

    public WorldOptionManager(World world, IGeneratorOptionManager global) {
        this.world = world;
        this.global = global;
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
        return null;
    }

    @Override
    public void saveOptions(UUID optionSet, IGeneratorOptionProvider options) {

    }

    @Override
    public IGeneratorOptionManager getWorldOptionManager(World world) {
        return global.getWorldOptionManager(world);
    }
}
