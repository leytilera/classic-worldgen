package dev.tilera.cwg.api.utils;

import java.util.Map;

import dev.tilera.cwg.api.options.IOption;

public class StringOption implements IOption<String> {

    private String id;
    private String displayName;
    private String defaultValue;
    private boolean isInternal = false;
    private boolean generatorSpecific = false;

    public StringOption(String id, String displayName, String defaultValue) {
        this.id = id;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
    }

    public StringOption(String id, String displayName, String defaultValue, boolean isInternal, boolean generatorSpecific) {
        this(id, displayName, defaultValue);
        this.isInternal = isInternal;
        this.generatorSpecific = generatorSpecific;
    }

    @Override
    public String getDefault() {
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
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public Map<String, String> getPossibleValues() {
        return null;
    }

    @Override
    public String decodeString(String input) {
        return input;
    }

    @Override
    public String fromRepresentation(String repr) {
        return null;
    }

    @Override
    public String toRepresentation(String obj) {
        return null;
    }

    @Override
    public Type getOptionType() {
        return isInternal ? Type.INTERNAL : Type.STRING;
    }

    @Override
    public boolean isGeneratorSpecific() {
        return generatorSpecific;
    }
    
    
}
