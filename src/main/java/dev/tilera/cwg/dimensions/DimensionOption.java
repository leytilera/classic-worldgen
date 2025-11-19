package dev.tilera.cwg.dimensions;

import java.util.Map;
import java.util.Optional;

import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.serialize.IntSerializer;
import dev.tilera.cwg.serialize.OptionalSerializer;

public class DimensionOption implements IOption<Optional<Integer>> {

    @Override
    public Optional<Integer> getDefault() {
        return Optional.empty();
    }

    @Override
    public String getID() {
        return "cwg:dimensions:id";
    }

    @Override
    public String getVisableName() {
        return "Dimension ID";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Optional<Integer>> getType() {
        return (Class<Optional<Integer>>) (Class<?>) Optional.class;
    }

    @Override
    public Type getOptionType() {
        return Type.INTERNAL;
    }

    @Override
    public Map<Optional<Integer>, String> getPossibleValues() {
        return null;
    }

    @Override
    public Optional<Integer> decodeString(String input) {
        return null;
    }

    @Override
    public <E> IObjectSerializer<E, Optional<Integer>> getSerializer(IObjectType<E> manipulator) {
        return new OptionalSerializer<E, Integer>(new IntSerializer<E>(manipulator), manipulator);
    }
    
}
