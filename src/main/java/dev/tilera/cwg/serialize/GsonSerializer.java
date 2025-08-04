package dev.tilera.cwg.serialize;

import java.io.IOException;

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
        };
    }

    @Override
    public JsonElement deserialize(ISerializedRead encoded) throws IllegalArgumentException {
        try {
            return gson.fromJson(encoded.read(), JsonElement.class);
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
