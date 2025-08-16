package dev.tilera.cwg.options;

import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OptionSerializerV2<T> implements IObjectSerializer<T, IGeneratorOptionProvider> {

    private IObjectManipulator<T> manipulator;
    private GlobalOptionManager manager;

    public OptionSerializerV2(IObjectManipulator<T> manipulator, GlobalOptionManager manager) {
        this.manipulator = manipulator;
        this.manager = manager;
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
        T json = manipulator.createObject();
        IGeneratorOptionRegistry registry = manager.getOptionRegistry();
        IChunkManagerFactory generator = object.getValue("cwg:generator", IHookProvider.class).getHook(HookTypes.GENERATOR);
        for (String id : object.getOptions()) {
            Class<?> clazz = registry.getOptionType(id);
            IOption<?> o = registry.getOption(id, clazz);
            if (o == null) {
                continue;
            } else if (generator != null && o.isGeneratorSpecific() && !generator.hasSpecificOption(o)) {
                continue;
            } else{
                T property = serializeOption(o, id, object);
                json = manipulator.setProperty(json, id, property);
            }
        }
        return json;
    }

    @Override
    public IGeneratorOptionProvider deserialize(T json) throws IllegalArgumentException {
        IGeneratorOptionRegistry registry = manager.getOptionRegistry();
        Map<String, Object> options = new HashMap<>();
        for (String prop : manipulator.getProperties(json)) {
            T value = manipulator.getProperty(json, prop);
            if (manipulator.isNull(value)) continue;
            IOption<?> o = registry.getOption(prop, registry.getOptionType(prop));
            if (o != null) {
                Object v = deserializeOption(o, value);
                options.put(o.getID(), v);
            }
        }
        UUID parent = ParentOption.INSTANCE.getDefault();
        if (options.containsKey(ParentOption.INSTANCE.getID())) {
            parent = (UUID) options.get(ParentOption.INSTANCE.getID());
        }
        OptionProvider provider = new OptionProvider(manager.getReference(parent));
        options.forEach(provider::putValue);
        return provider;
    }
}
