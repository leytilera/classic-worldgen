package dev.tilera.cwg.options.common;

import java.util.Map;

import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.serialize.IntSerializer;

public class IntOption implements IOption<Integer> {

    private String id;
    private String displayName;
    private int defaultValue;
    private boolean isInternal = false;
    private boolean isGeneratorSpecific = false;

    public IntOption(String id, String displayName, int defaultValue) {
        this.id = id;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
    }

    public IntOption(String id, String displayName, int defaultValue, boolean isInternal, boolean isGeneratorSpecific) {
        this.id = id;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
        this.isInternal = isInternal;
        this.isGeneratorSpecific = isGeneratorSpecific;
    }

    @Override
    public Integer getDefault() {
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
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public Type getOptionType() {
        return isInternal ? Type.INTERNAL : Type.STRING;
    }

    @Override
    public Map<Integer, String> getPossibleValues() {
        return null;
    }

    @Override
    public Integer decodeString(String input) {
        return Integer.parseInt(input);
    }

    @Override
    public boolean isGeneratorSpecific() {
        return isGeneratorSpecific;
    }

    @Override
    public <E> IObjectSerializer<E, Integer> getSerializer(IObjectManipulator<E> manipulator) {
        return new IntSerializer<>(manipulator);
    }
    
}
