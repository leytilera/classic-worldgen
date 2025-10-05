package dev.tilera.cwg.options;

import java.util.Map;
import java.util.Optional;

import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectType;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.serialize.OptionalSerializer;
import dev.tilera.cwg.serialize.StringSerializer;

public class OptionSetNameOption implements IOption<Optional<String>> {

    public static IOption<Optional<String>> INSTANCE = new OptionSetNameOption();

    @Override
    public Optional<String> getDefault() {
        return Optional.empty();
    }

    @Override
    public String getID() {
        return "cwg:internal:name";
    }

    @Override
    public String getVisableName() {
        return "Name";
    }

    @SuppressWarnings({"unchecked", "ALEC"})
    @Override
    public Class<Optional<String>> getType() {
        return (Class<Optional<String>>) (Class<?>) Optional.class;
    }

    @Override
    public Type getOptionType() {
        return Type.STRING;
    }

    @Override
    public Map<Optional<String>, String> getPossibleValues() {
        return null;
    }

    @Override
    public Optional<String> decodeString(String input) {
        return Optional.of(input);
    }

    @Override
    public <E> IObjectSerializer<E, Optional<String>> getSerializer(IObjectType<E> manipulator) {
        return new OptionalSerializer<>(new StringSerializer<>(manipulator), manipulator);
    }
    
}
