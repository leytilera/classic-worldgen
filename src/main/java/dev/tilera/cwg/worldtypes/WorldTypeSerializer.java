package dev.tilera.cwg.worldtypes;

import dev.tilera.cwg.api.serialize.IObjectSerializer;
import net.minecraft.world.WorldType;

public class WorldTypeSerializer implements IObjectSerializer<String, WorldType> {

    public static IObjectSerializer<String, WorldType> INSTANCE = new WorldTypeSerializer();

    @Override
    public String serialize(WorldType object) {
        return object.getWorldTypeName();
    }

    @Override
    public WorldType deserialize(String encoded) throws IllegalArgumentException {
        WorldType wt = WorldType.parseWorldType(encoded);
        if (wt == null) throw new IllegalArgumentException("Invalid world type: " + encoded);
        return wt;
    }
    
}
