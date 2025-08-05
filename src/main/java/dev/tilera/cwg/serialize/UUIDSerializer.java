package dev.tilera.cwg.serialize;

import java.util.UUID;

import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class UUIDSerializer implements IObjectSerializer<String, UUID> {

    public static IObjectSerializer<String, UUID> INSTANCE = new UUIDSerializer();

    @Override
    public String serialize(UUID object) {
        return object.toString();

    }

    @Override
    public UUID deserialize(String encoded) throws IllegalArgumentException {
        return UUID.fromString(encoded);
    }
    
}
