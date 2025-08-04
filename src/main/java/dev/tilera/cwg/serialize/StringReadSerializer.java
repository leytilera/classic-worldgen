package dev.tilera.cwg.serialize;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;

public class StringReadSerializer implements IObjectSerializer<String, ISerializedRead> {

    public static IObjectSerializer<String, ISerializedRead> INSTANCE = new StringReadSerializer();

    @Override
    public String serialize(ISerializedRead object) {
        StringWriter writer = new StringWriter();
        try {
            object.write(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    @Override
    public ISerializedRead deserialize(String encoded) throws IllegalArgumentException {
        return ISerializedRead.fromReader(new StringReader(encoded));
    }
    
}
