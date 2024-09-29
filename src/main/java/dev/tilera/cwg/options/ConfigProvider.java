package dev.tilera.cwg.options;

import dev.tilera.cwg.Config;
import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;

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
            default: return parent.getBoolean(id);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue(String id, Class<T> type) {
        if (id.equals("cwg:cavegen_hook") && Config.enableSwissCheeseCaves) {
            return (T) CwgGlobals.getHookRegistry().getHookProvider("cwg:swiss_cavegen");
        }
        return parent.getValue(id, type);
    }
    
}
