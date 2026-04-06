package dev.tilera.cwg.serialize;

import java.util.UUID;
import java.util.regex.Pattern;

import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class UUIDSerializer implements IObjectSerializer<String, UUID> {

    public static IObjectSerializer<String, UUID> INSTANCE = new UUIDSerializer();
    public static Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$");

    @Override
    public String serialize(UUID object) {
        return object.toString();

    }

    @Override
    public UUID deserialize(String encoded) throws IllegalArgumentException {
        return UUID.fromString(encoded);
    }

    @Override
    public boolean canDeserialize(String encoded) {
        return UUID_PATTERN.matcher(encoded).matches();
    }
    
}
