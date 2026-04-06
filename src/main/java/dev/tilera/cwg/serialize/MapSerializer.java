package dev.tilera.cwg.serialize;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;

public class MapSerializer<T, K, V> implements IObjectSerializer<T, Map<K, V>> {

    private IObjectType<T> manipulator;
    private IObjectSerializer<String, K> keySerializer;
    private IObjectSerializer<T, V> valuSerializer;

    public MapSerializer(IObjectType<T> manipulator, IObjectSerializer<String, K> keySerializer,
            IObjectSerializer<T, V> valuSerializer) {
        this.manipulator = manipulator;
        this.keySerializer = keySerializer;
        this.valuSerializer = valuSerializer;
    }

    @Override
    public T serialize(Map<K, V> object) {
        T obj = manipulator.objects().create();
        for (Entry<K, V> e : object.entrySet()) {
            manipulator.objects().set(obj, keySerializer.serialize(e.getKey()), valuSerializer.serialize(e.getValue()));
        }
        return obj;
    }

    @Override
    public Map<K, V> deserialize(T encoded) throws IllegalArgumentException {
        Map<K, V> map = new HashMap<>();
        for (String key : manipulator.objects().getIndices(encoded)) {
            T value = manipulator.objects().get(encoded, key);
            map.put(keySerializer.deserialize(key), valuSerializer.deserialize(value));
        }
        return map;
    }

    @Override
    public boolean canDeserialize(T encoded) {
        return true;
    }
    
}
