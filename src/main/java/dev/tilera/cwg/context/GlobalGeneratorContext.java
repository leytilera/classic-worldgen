package dev.tilera.cwg.context;

import java.util.Optional;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.context.IGeneratorContext;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.minecraft.world.World;

public class GlobalGeneratorContext implements IGeneratorContext {

    @Override
    public IGeneratorOptionProvider getOptions() {
        return CwgGlobals.getOptionProvider();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> getObject(Class<T> type) {
        return Optional.ofNullable(type)
            .filter(t -> t.equals(World.class))
            .map(t -> CwgGlobals.getCurrentState())
            .map(o -> (T) o);
    }
    
}
