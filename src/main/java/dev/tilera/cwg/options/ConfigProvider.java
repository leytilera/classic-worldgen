package dev.tilera.cwg.options;

import dev.tilera.cwg.Config;
import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;

public class ConfigProvider implements IGeneratorOptionProvider {

    private IGeneratorOptionProvider parent;

    public ConfigProvider(IGeneratorOptionProvider parent) {
        this.parent = parent;
    }

    @Override
    public Integer getInt(String id) {
        return parent.getInt(id);
    }

    @Override
    public String getString(String id) {
        return parent.getString(id);
    }

    @Override
    public Double getDouble(String id) {
        return parent.getDouble(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        switch(id) {
            case "cwg:classic_extreme_hills": return Config.classicExtremeHills;
            case "cwg:farlands": return Config.enableFarlands;
            case "cwg:desert_lakes": return Config.enableDesertLakes;
            case "cwg:generator.classic:disableJungle": return Config.disableJungle;
            case "cwg:generator.classic:newVanillaBiomes": return Config.addNewVanillaBiomes;
            case "cwg:generator.classic:disableModdedBiomes": return Config.addNewVanillaBiomes;
            case "cwg:generator.classic:enableModdedWorldgen": return Config.enableModdedWorldgen;
            default: return parent.getBoolean(id);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue(String id, Class<T> type) {
        if (Boolean.class.equals(type)) {
            return (T) getBoolean(id);
        } else if (Integer.class.equals(type)) {
            return (T) getInt(id);
        } else if (String.class.equals(type)) {
            return (T) getString(id);
        } else if (Double.class.equals(type)) {
            return (T) getDouble(id);
        } else if (id.equals("cwg:cavegen_hook") && Config.enableSwissCheeseCaves) {
            return (T) CwgGlobals.getHookRegistry().getHookProvider("cwg:swiss_cavegen");
        }
        return parent.getValue(id, type);
    }

    @Override
    public IGeneratorOptionRegistry getRegistry() {
        return parent.getRegistry();
    }
    
}
