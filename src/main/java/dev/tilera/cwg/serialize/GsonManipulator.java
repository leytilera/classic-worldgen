package dev.tilera.cwg.serialize;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.RepresentationType;

public class GsonManipulator implements IObjectManipulator<JsonElement> {

    @Override
    public JsonElement createPrimitive(String string) {
        return new JsonPrimitive(string);
    }

    @Override
    public JsonElement createPrimitive(boolean bool) {
        return new JsonPrimitive(bool);
    }

    @Override
    public JsonElement createPrimitive(int integer) {
        return new JsonPrimitive(integer);
    }

    @Override
    public JsonElement createPrimitive(double d) {
        return new JsonPrimitive(d);
    }

    @Override
    public JsonElement createNull() {
        return JsonNull.INSTANCE;
    }

    @Override
    public JsonElement createObject() {
        return new JsonObject();
    }

    @Override
    public JsonElement createArray() {
        return new JsonArray();
    }

    @Override
    public boolean isNull(JsonElement object) {
        return object == null || object.isJsonNull();
    }

    @Override
    public boolean isOfType(JsonElement object, RepresentationType type) {
        switch(type) {
            case ARRAY:
                return object.isJsonArray();
            case BOOLEAN:
                return object.isJsonPrimitive() && object.getAsJsonPrimitive().isBoolean();
            case DOUBLE:
            case INTEGER:
                return object.isJsonPrimitive() && object.getAsJsonPrimitive().isNumber();
            case OBJECT:
                return object.isJsonObject();
            case STRING:
            return object.isJsonPrimitive() && object.getAsJsonPrimitive().isString();
            default:
                return false;
            
        }
    }

    @Override
    public String asString(JsonElement object) throws IllegalArgumentException {
        try {
            return object.getAsString();
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean asBoolean(JsonElement object) throws IllegalArgumentException {
        try {
            return object.getAsBoolean();
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public int asInteger(JsonElement object) throws IllegalArgumentException {
        try {
            return object.getAsInt();
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public double asDouble(JsonElement object) throws IllegalArgumentException {
        try {
            return object.getAsDouble();
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public JsonElement getProperty(JsonElement object, String name) throws IllegalArgumentException {
        try {
            return object.getAsJsonObject().get(name);
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public JsonElement getIndex(JsonElement object, int index) throws IllegalArgumentException, IndexOutOfBoundsException {
        try {
            return object.getAsJsonArray().get(index);
        } catch(IllegalStateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public JsonElement setProperty(JsonElement object, String name, JsonElement property) throws IllegalArgumentException {
        try {
            object.getAsJsonObject().add(name, property);
            return object;
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public JsonElement setIndex(JsonElement object, int index, JsonElement value) throws IllegalArgumentException {
        // TODO: Nicht sehr Alecgant.
        if (index != getSize(object)) throw new IllegalArgumentException("Can only append to GSON arrays");
        try {
            object.getAsJsonArray().add(value);
            return object;
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String[] getProperties(JsonElement object) throws IllegalArgumentException {
        try {
            return object.getAsJsonObject().entrySet().stream().map((e) -> e.getKey()).toArray(String[]::new);
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public int getSize(JsonElement object) throws IllegalArgumentException {
        try {
            return object.getAsJsonArray().size();
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
