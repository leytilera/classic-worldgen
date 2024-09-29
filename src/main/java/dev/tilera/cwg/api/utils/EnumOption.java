package dev.tilera.cwg.api.utils;

import java.util.HashMap;
import java.util.Map;

import dev.tilera.cwg.api.options.IOption;

public class EnumOption<T extends Enum<T>> implements IOption<T> {

    private String id;
    private String displayName;
    private T defaultValue;
    private IEnumManager<T> enumManager;

    public EnumOption(String id, String displayName, T defaultValue, IEnumManager<T> enumManager) {
        this.id = id;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
        this.enumManager = enumManager;
    }

    @Override
    public T getDefault() {
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
    public Class<T> getType() {
        return enumManager.getEnumClass();
    }

    @Override
    public Map<T, String> getPossibleValues() {
        Map<T, String> map = new HashMap<>();
        Class<T> clazz = getType();
        T[] consts = clazz.getEnumConstants();
        for(T c : consts) {
            map.put(c, enumManager.getDisplayName(c));
        }
        return map;
    }

    @Override
    public T decodeString(String input) {
        return null;
    }

    @Override
    public T fromRepresentation(String repr) {
        return Enum.valueOf(getType(), repr);
    }

    @Override
    public String toRepresentation(T obj) {
        return obj.name();
    }

    @Override
    public Type getOptionType() {
        return Type.ENUM;
    }
    
}
