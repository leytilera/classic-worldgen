package dev.tilera.cwg;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.classic.ClassicChunkManagerFactory;
import dev.tilera.cwg.classic.WorldChunkManagerClassic;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.*;

public class WorldTypeClassic extends WorldType implements IGeneratorOptionProvider {

    private static ClassicChunkManagerFactory factory = new ClassicChunkManagerFactory();

    public WorldTypeClassic() {
        super("onesix");
    }
    
    @Override
    public WorldChunkManager getChunkManager(World world) {
        WorldChunkManagerClassic manager = factory.createChunkManager(this, world);
        CwgGlobals.setCurrentState(world);
        return manager;
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        if (world.provider.worldChunkMgr instanceof AbstractChunkManager) {
            return ((AbstractChunkManager)world.provider.worldChunkMgr).getGenerator(world);
        } else {
            throw new RuntimeException("Invalid WorldChunkManager");
        }
    }

    @Override
    public Integer getInt(String id) {
        return ClassicWorldgen.CONFIG.getInt(id);
    }

    @Override
    public String getString(String id) {
        return ClassicWorldgen.CONFIG.getString(id);
    }

    @Override
    public Double getDouble(String id) {
        return ClassicWorldgen.CONFIG.getDouble(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        switch(id) {
            case "cwg:classic_extreme_hills":
            case "cwg:disable_jungle_melons":
            case "cwg:disable_new_flowers":
            case "cwg:disable_tall_flowers":
                return true;
            default: return ClassicWorldgen.CONFIG.getBoolean(id);
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
        } else if (id.equals("cwg:cavegen_hook")) {
            return (T) CwgGlobals.getHookRegistry().getHookProvider("cwg:swiss_cavegen");
        }
        return ClassicWorldgen.CONFIG.getValue(id, type);
    }

    @Override
    public Collection<String> getOptions() {
        return Arrays.asList(
                "cwg:cavegen_hook",
                "cwg:classic_extreme_hills",
                "cwg:disable_jungle_melons",
                "cwg:disable_new_flowers",
                "cwg:disable_tall_flowers"
        );
    }

}
