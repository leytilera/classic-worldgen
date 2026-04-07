package dev.tilera.cwg.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dev.tilera.cwg.api.context.IGeneratorContext;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.minecraft.world.World;

public class CommonGeneratorContext implements IGeneratorContext {

    IGeneratorOptionProvider options;
    Map<Class<?>, Object> objects = new HashMap<>();

    public CommonGeneratorContext(IGeneratorOptionProvider options, World world, Object... objects) {
        this.options = options;
        this.objects.put(World.class, world);
        for (Object obj : objects) {
            this.objects.put(obj.getClass(), obj);
        }
    }

    @Override
    public IGeneratorOptionProvider getOptions() {
        return this.options;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> getObject(Class<T> type) {
        return Optional.ofNullable(this.objects.get(type)).map(o -> (T) o);
    }
    
}
