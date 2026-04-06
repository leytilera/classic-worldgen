package dev.tilera.cwg.serialize;

import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.ISerializedRead;

public class GsonSerializer implements IObjectSerializer<ISerializedRead, JsonElement> {

    public static IObjectSerializer<ISerializedRead, JsonElement> RW = new GsonSerializer();
    public static IObjectSerializer<String, JsonElement> STRING = new CombinedSerializer<>(StringReadSerializer.INSTANCE, RW);
    public static IObjectSerializer<ISerializedRead, JsonElement> PRETTY = new GsonSerializer(new GsonBuilder().setPrettyPrinting().create());

    private Gson gson;

    public GsonSerializer() {
        this.gson = new GsonBuilder().create();
    }
    

    public GsonSerializer(Gson gson) {
        this.gson = gson;
    }


    @Override
    public ISerializedRead serialize(JsonElement object) {
        return (writer) -> {
            gson.toJson(object, writer);
            writer.close();
        };
    }

    @Override
    public JsonElement deserialize(ISerializedRead encoded) throws IllegalArgumentException {
        try {
            Reader reader = encoded.read();
            JsonElement result = gson.fromJson(reader, JsonElement.class);
            reader.close();
            return result;
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean canDeserialize(ISerializedRead encoded) {
        return true;
    }
    
}
