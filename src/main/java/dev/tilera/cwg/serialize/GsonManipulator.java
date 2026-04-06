package dev.tilera.cwg.serialize;

import java.util.stream.IntStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.IObjectType;
import net.anvilcraft.anvillib.api.inject.Implementation;

@Implementation(value = IObjectType.class, id = "gson")
public class GsonManipulator implements IObjectType<JsonElement> {

    private GsonManipulator() {
        
    }

    private final IObjectSerializer<JsonElement, String> stringSerializer = new IObjectSerializer<JsonElement, String>() {

        @Override
        public JsonElement serialize(String object) {
            return new JsonPrimitive(object);
        }

        @Override
        public String deserialize(JsonElement encoded) throws IllegalArgumentException {
            try {
                return encoded.getAsString();
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public boolean canDeserialize(JsonElement encoded) {
            return encoded != null && encoded.isJsonPrimitive() && encoded.getAsJsonPrimitive().isString();
        }
        
    };
    private final IObjectSerializer<JsonElement, Boolean> booleanSerializer = new IObjectSerializer<JsonElement, Boolean>() {

        @Override
        public JsonElement serialize(Boolean object) {
            return new JsonPrimitive(object);
        }

        @Override
        public Boolean deserialize(JsonElement encoded) throws IllegalArgumentException {
            try {
                return encoded.getAsBoolean();
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public boolean canDeserialize(JsonElement encoded) {
            return encoded != null && encoded.isJsonPrimitive() && encoded.getAsJsonPrimitive().isBoolean();
        }
        
    };
    private final IObjectSerializer<JsonElement, Long> integerSerializer = new IObjectSerializer<JsonElement, Long>() {

        @Override
        public JsonElement serialize(Long object) {
            return new JsonPrimitive(object);
        }

        @Override
        public Long deserialize(JsonElement encoded) throws IllegalArgumentException {
            try {
                return encoded.getAsLong();
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public boolean canDeserialize(JsonElement encoded) {
            return encoded != null && encoded.isJsonPrimitive() && encoded.getAsJsonPrimitive().isNumber();
        }
        
    };
    private final IObjectSerializer<JsonElement, Double> floatSerializer = new IObjectSerializer<JsonElement, Double>() {

        @Override
        public JsonElement serialize(Double object) {
            return new JsonPrimitive(object);
        }

        @Override
        public Double deserialize(JsonElement encoded) throws IllegalArgumentException {
            try {
                return encoded.getAsDouble();
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public boolean canDeserialize(JsonElement encoded) {
            return encoded != null && encoded.isJsonPrimitive() && encoded.getAsJsonPrimitive().isNumber();
        }
        
    };
    private final IObjectManipulator<JsonElement, Integer> arrayManipulator = new IObjectManipulator<JsonElement, Integer>() {

        @Override
        public JsonElement get(JsonElement object, Integer index) throws IllegalArgumentException, IndexOutOfBoundsException {
            try {
                return object.getAsJsonArray().get(index);
            } catch(IllegalStateException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public JsonElement set(JsonElement object, Integer index, JsonElement value) throws IllegalArgumentException {
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
        public int getSize(JsonElement object) {
            try {
                return object.getAsJsonArray().size();
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public Integer[] getIndices(JsonElement object) {
            return IntStream.range(0, getSize(object)).boxed().toArray(Integer[]::new);
        }

        @Override
        public JsonElement create() {
            return new JsonArray();
        }

        @Override
        public boolean isInstance(JsonElement object) {
            return object != null && object.isJsonArray();
        }
        
    };
    private final IObjectManipulator<JsonElement, String> objectManipulator = new IObjectManipulator<JsonElement, String>() {

        @Override
        public JsonElement get(JsonElement object, String index) throws IllegalArgumentException, IndexOutOfBoundsException {
            try {
                return object.getAsJsonObject().get(index);
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public JsonElement set(JsonElement object, String index, JsonElement value) throws IllegalArgumentException {
            try {
                object.getAsJsonObject().add(index, value);
                return object;
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public int getSize(JsonElement object) {
            try {
                return object.getAsJsonObject().entrySet().size();
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public String[] getIndices(JsonElement object) {
            try {
                return object.getAsJsonObject().entrySet().stream().map((e) -> e.getKey()).toArray(String[]::new);
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public JsonElement create() {
            return new JsonObject();
        }

        @Override
        public boolean isInstance(JsonElement object) {
            return object != null && object.isJsonObject();
        }
        
    };

    @Override
    public JsonElement createNull() {
        return JsonNull.INSTANCE;
    }

    @Override
    public boolean isNull(JsonElement object) {
        return object == null || object.isJsonNull();
    }

    @Override
    public IObjectSerializer<JsonElement, String> strings() {
        return this.stringSerializer;
    }

    @Override
    public IObjectSerializer<JsonElement, Boolean> booleans() {
        return this.booleanSerializer;
    }

    @Override
    public IObjectSerializer<JsonElement, Long> integers() {
        return this.integerSerializer;
    }

    @Override
    public IObjectSerializer<JsonElement, Double> floats() {
        return this.floatSerializer;
    }

    @Override
    public IObjectManipulator<JsonElement, Integer> arrays() {
        return this.arrayManipulator;
    }

    @Override
    public IObjectManipulator<JsonElement, String> objects() {
        return this.objectManipulator;
    }
    
}
