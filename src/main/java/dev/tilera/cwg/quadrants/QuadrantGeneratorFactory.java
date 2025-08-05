package dev.tilera.cwg.quadrants;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import net.minecraft.world.World;

public class QuadrantGeneratorFactory implements IChunkManagerFactory {

    @Override
    public AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world) {
        IGeneratorOptionProvider nw = getOrDefault(options.getValue("cwg:generator.quadrants:nw", IGeneratorOptionProvider.class), CwgGlobals.getOptionRegistry());
        IGeneratorOptionProvider sw = getOrDefault(options.getValue("cwg:generator.quadrants:sw", IGeneratorOptionProvider.class), CwgGlobals.getOptionRegistry());
        IGeneratorOptionProvider ne = getOrDefault(options.getValue("cwg:generator.quadrants:ne", IGeneratorOptionProvider.class), CwgGlobals.getOptionRegistry());
        IGeneratorOptionProvider se = getOrDefault(options.getValue("cwg:generator.quadrants:se", IGeneratorOptionProvider.class), CwgGlobals.getOptionRegistry());
        QuadrantManager<AbstractChunkManager> quadrants = new QuadrantManager<AbstractChunkManager>(
            createFromOptions(nw, world), 
            createFromOptions(sw, world), 
            createFromOptions(ne, world), 
            createFromOptions(se, world)
        );
        return new QuadrantChunkManager(quadrants, options);
    }

    @Override
    public String getID() {
        return "cwg:quadrants";
    }

    @Override
    public String getDisplayName() {
        return "Quadrants";
    }

    @Override
    public boolean hasSpecificOption(IOption<?> option) {
        return option.getID().startsWith("cwg:generator.quadrants");
    }

    public AbstractChunkManager createFromOptions(IGeneratorOptionProvider options, World world) {
        return options.getValue("cwg:generator", IChunkManagerFactory.class).createChunkManager(options, world);
    }

    public IGeneratorOptionProvider getOrDefault(IGeneratorOptionProvider opts, IGeneratorOptionProvider defaultOpts) {
        return opts == null ? defaultOpts : opts;
    }
    
}
