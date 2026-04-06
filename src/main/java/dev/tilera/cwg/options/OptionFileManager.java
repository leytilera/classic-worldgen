package dev.tilera.cwg.options;

import java.util.Map;
import java.util.UUID;

import com.google.gson.JsonElement;

import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.ISerializedRead;
import dev.tilera.cwg.serialize.CombinedSerializer;
import dev.tilera.cwg.serialize.GsonSerializer;
import dev.tilera.cwg.serialize.MapSerializer;
import dev.tilera.cwg.serialize.UUIDSerializer;
import net.anvilcraft.anvillib.api.inject.Implementation;
import net.anvilcraft.anvillib.api.inject.Inject;

@Implementation(OptionFileManager.class)
public class OptionFileManager {

    @Inject(OptionFileManager.class)
    public static OptionFileManager INSTANCE;
    @Inject(IObjectType.class)
    private IObjectType<JsonElement> manipulator;

    public IObjectSerializer<ISerializedRead, Map<UUID, IGeneratorOptionProvider>> createSerializer(IGeneratorOptionManager manager, boolean prettyPrinting) {
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
