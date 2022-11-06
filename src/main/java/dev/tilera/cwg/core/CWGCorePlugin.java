package dev.tilera.cwg.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import dev.tilera.cwg.Constants;
import io.github.tox1cozz.mixinbooterlegacy.IEarlyMixinLoader;

@Name("CWG Core Plugin")
public class CWGCorePlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        List<String> mixins = new ArrayList<>();
        mixins.add("cwg.mixins.json");
        return mixins;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return "dev.tilera.cwg.core.CWGCorePlugin$Container";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> arg0) {
        
    }

    public static class Container extends DummyModContainer {

        public Container() {
            super(new ModMetadata());
            ModMetadata meta = getMetadata();
            meta.modId = "cwgcore";
            meta.name = "CWG Core Plugin";
            meta.version = Constants.VERSION;
        }

    }
    
}
