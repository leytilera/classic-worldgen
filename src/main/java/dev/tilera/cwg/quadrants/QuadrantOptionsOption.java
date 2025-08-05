package dev.tilera.cwg.quadrants;

import java.util.Map;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.serialize.IObjectManipulator;
import dev.tilera.cwg.api.serialize.IObjectSerializer;
import dev.tilera.cwg.options.OptionSerializer;

public class QuadrantOptionsOption implements IOption<IGeneratorOptionProvider> {

    private String id;
    private String displayName;

    public QuadrantOptionsOption(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Override
    public IGeneratorOptionProvider getDefault() {
        return CwgGlobals.getOptionRegistry();
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
    public Class<IGeneratorOptionProvider> getType() {
        return IGeneratorOptionProvider.class;
    }

    @Override
    public Type getOptionType() {
        return Type.INTERNAL;
    }

    @Override
    public Map<IGeneratorOptionProvider, String> getPossibleValues() {
        return null;
    }

    @Override
    public IGeneratorOptionProvider decodeString(String input) {
        return CwgGlobals.getOptionRegistry().decodeOptions(input);
    }

    @Override
    public boolean isGeneratorSpecific() {
        return true;
    }

    @Override
    public <E> IObjectSerializer<E, IGeneratorOptionProvider> getSerializer(IObjectManipulator<E> manipulator) {
        return new OptionSerializer<>(manipulator, getDefault(), CwgGlobals.getOptionRegistry());
    }   
    
}
