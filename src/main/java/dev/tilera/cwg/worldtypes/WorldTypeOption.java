package dev.tilera.cwg.worldtypes;

import java.util.HashMap;
import java.util.Map;

import dev.tilera.cwg.api.options.IOption;
import net.minecraft.world.WorldType;

public class WorldTypeOption implements IOption<WorldType> {

    @Override
    public WorldType getDefault() {
        return WorldType.DEFAULT;
    }

    @Override
    public String getID() {
        return "cwg:generator.worldtype:worldtype";
    }

    @Override
    public String getVisableName() {
        return "World Type";
    }

    @Override
    public Class<WorldType> getType() {
        return WorldType.class;
    }

    @Override
    public Type getOptionType() {
        return Type.ENUM;
    }

    @Override
    public Map<WorldType, String> getPossibleValues() {
        Map<WorldType, String> map = new HashMap<>();
        for (WorldType t : WorldType.worldTypes) {
            if (t == null) continue;
            map.put(t, t.getWorldTypeName());
        }
        return map;
    }

    @Override
    public WorldType decodeString(String input) {
        return null;
    }

    @Override
    public WorldType fromRepresentation(String repr) {
        return WorldType.parseWorldType(repr);
    }

    @Override
    public String toRepresentation(WorldType obj) {
        return obj.getWorldTypeName();
    }

    @Override
    public boolean isGeneratorSpecific() {
        return true;
    }
    
}
