package dev.tilera.cwg.options;

import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class OptionSerializer<T> implements IObjectSerializer<T, IGeneratorOptionProvider> {

    private IObjectType<T> manipulator;
    private IGeneratorOptionProvider defaults;
    private IGeneratorOptionRegistry registry;

    public OptionSerializer(IObjectType<T> manipulator, IGeneratorOptionProvider defaults,
            IGeneratorOptionRegistry registry) {
        this.manipulator = manipulator;
        this.defaults = defaults;
        this.registry = registry;
    }

    private <E> T serializeOption(IOption<E> option, String id, IGeneratorOptionProvider provider) {
        IObjectSerializer<T, E> os = option.getSerializer(manipulator);
        E value = provider.getValue(id, option.getType());
        return os.serialize(value);
    }

    private <E> E deserializeOption(IOption<E> option, T object) throws IllegalArgumentException {
        IObjectSerializer<T, E> os = option.getSerializer(manipulator);
        return os.deserialize(object);
    }

    @Override
    public T serialize(IGeneratorOptionProvider object) {
        T json = manipulator.objects().create();
        IChunkManagerFactory generator = object.getValue("cwg:generator", IHookProvider.class).getHook(HookTypes.GENERATOR);
        for (String id : registry.getOptions()) {
            Class<?> clazz = registry.getOptionType(id);
            IOption<?> o = registry.getOption(id, clazz);
            if (o == null) {
                continue;
            } else if (generator != null && o.isGeneratorSpecific() && !generator.hasSpecificOption(o)) {
                continue;
            } else{
                T property = serializeOption(o, id, object);
                json = manipulator.objects().set(json, id, property);
            } 
        }
        return json;
    }

    @Override
    public IGeneratorOptionProvider deserialize(T encoded) throws IllegalArgumentException {
        T json = encoded;
        OptionProvider provider = new OptionProvider(new Pointer<>(defaults));
        for (String prop : manipulator.objects().getIndices(json)) {
            T value = manipulator.objects().get(json, prop);
            if (manipulator.isNull(value)) continue;
            IOption<?> o = registry.getOption(prop, registry.getOptionType(prop));
            if (o == null) {
                continue;
            } else {
                Object v = deserializeOption(o, value);
                provider.putValue(o.getID(), v);
            }
        }
        return provider;
    }

    @Override
    public boolean canDeserialize(T encoded) {
        return true;
    }
    
}
