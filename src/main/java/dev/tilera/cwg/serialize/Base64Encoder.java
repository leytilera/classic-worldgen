package dev.tilera.cwg.serialize;

import java.util.Base64;

import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class Base64Encoder implements IObjectSerializer<String, String> {

    public static IObjectSerializer<String, String> INSTANCE = new Base64Encoder();

    @Override
    public String serialize(String object) {
        return Base64.getEncoder().encodeToString(object.getBytes());
    }

    @Override
    public String deserialize(String encoded) throws IllegalArgumentException {
        return new String(Base64.getDecoder().decode(encoded));
    }
    
}
