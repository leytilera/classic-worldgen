package dev.tilera.cwg.serialize;

import java.util.Base64;
import java.util.regex.Pattern;

import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class Base64Encoder implements IObjectSerializer<String, String> {

    public static IObjectSerializer<String, String> INSTANCE = new Base64Encoder();
    public static Pattern BASE64_PATTERN = Pattern.compile("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");

    @Override
    public String serialize(String object) {
        return Base64.getEncoder().encodeToString(object.getBytes());
    }

    @Override
    public String deserialize(String encoded) throws IllegalArgumentException {
        return new String(Base64.getDecoder().decode(encoded));
    }

    @Override
    public boolean canDeserialize(String encoded) {
        return BASE64_PATTERN.matcher(encoded).matches();
    }
    
}
