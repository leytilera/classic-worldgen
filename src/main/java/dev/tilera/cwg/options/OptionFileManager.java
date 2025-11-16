package dev.tilera.cwg.options;

import java.util.Map;
import java.util.UUID;

import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;
import dev.tilera.cwg.serialize.CombinedSerializer;
import dev.tilera.cwg.serialize.GsonManipulator;
import dev.tilera.cwg.serialize.GsonSerializer;
import dev.tilera.cwg.serialize.MapSerializer;
import dev.tilera.cwg.serialize.UUIDSerializer;

public class OptionFileManager {

    public static OptionFileManager INSTANCE = new OptionFileManager();

    public IObjectSerializer<ISerializedRead, Map<UUID, IGeneratorOptionProvider>> createSerializer(IGeneratorOptionManager manager, boolean prettyPrinting) {
        GsonManipulator manipulator = new GsonManipulator();
        return new CombinedSerializer<>(
            prettyPrinting ? GsonSerializer.PRETTY : GsonSerializer.RW, 
            new MapSerializer<>(
                manipulator,
                UUIDSerializer.INSTANCE,
                manager.createSerializer(manipulator)
            )
        );
    }
    
}
