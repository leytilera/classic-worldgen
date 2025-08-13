package dev.tilera.cwg.options;

import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.serialize.CombinedSerializer;
import dev.tilera.cwg.serialize.StringSerializer;
import dev.tilera.cwg.serialize.UUIDSerializer;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ParentOption implements IOption<UUID> {

    public static IOption<UUID> INSTANCE = new ParentOption();

    @Override
    public UUID getDefault() {
        return IGeneratorOptionManager.REGISTRY;
    }

    @Override
    public String getID() {
        return "cwg:internal:parent";
    }

    @Override
    public String getVisableName() {
        return "Parent Options";
    }

    @Override
    public Class<UUID> getType() {
        return UUID.class;
    }

    @Override
    public Type getOptionType() {
        return Type.STRING;
    }

    @Override
    public Map<UUID, String> getPossibleValues() {
        return Collections.emptyMap();
    }

    @Override
    public UUID decodeString(String input) {
        return UUID.fromString(input);
    }

    @Override
    public <E> IObjectSerializer<E, UUID> getSerializer(IObjectManipulator<E> manipulator) {
        return new CombinedSerializer<>(new StringSerializer<>(manipulator), UUIDSerializer.INSTANCE);
    }
}
