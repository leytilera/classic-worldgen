package dev.tilera.cwg.options.common;

import java.util.HashMap;
import java.util.Map;

import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.serialize.BooleanSerializer;

public class BooleanOption implements IOption<Boolean> {

    private String id;
    private String displayName;
    private boolean defaultValue;
    private boolean isGeneratorSpecific;

    public BooleanOption(String id, String displayName, boolean defaultValue) {
        this.id = id;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
        this.isGeneratorSpecific = false;
    }

    public BooleanOption(String id, String displayName, boolean defaultValue, boolean isGeneratorSpecific) {
        this.id = id;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
        this.isGeneratorSpecific = isGeneratorSpecific;
    }

    @Override
    public Boolean getDefault() {
        return defaultValue;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getVisableName() {
        return displayName;
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public Map<Boolean, String> getPossibleValues() {
        Map<Boolean, String> map = new HashMap<>();
        map.put(false, "False");
        map.put(true, "True");
        return map;
    }

    @Override
    public Boolean decodeString(String input) {
        return null;
    }

    @Override
    public boolean isGeneratorSpecific() {
        return isGeneratorSpecific;
    }

    @Override
    public Type getOptionType() {
        return Type.ENUM;
    }

    @Override
    public <E> IObjectSerializer<E, Boolean> getSerializer(IObjectManipulator<E> manipulator) {
        return new BooleanSerializer<>(manipulator);
    }
    
}
