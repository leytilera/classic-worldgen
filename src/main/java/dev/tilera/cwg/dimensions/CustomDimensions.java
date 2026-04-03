package dev.tilera.cwg.dimensions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import dev.tilera.cwg.Config;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.api.reference.IMutableReference;
import dev.tilera.cwg.options.common.IntOption;
import dev.tilera.cwg.options.common.StringOption;
import dev.tilera.cwg.modules.IModule;
import net.anvilcraft.anvillib.api.inject.Implementation;
import net.anvilcraft.anvillib.api.inject.Inject;
import net.minecraftforge.common.DimensionManager;

@Implementation(value = IModule.class, id = "dimensions")
public class CustomDimensions implements IModule {

    @Inject(value = IModule.class, preferred = "dimensions")
    private static IModule INSTANCE;
    private Map<Integer, IGeneratorOptionProvider> dimensions = new HashMap<>();
    
    public static CustomDimensions instance() {
        return (CustomDimensions) INSTANCE;
    }

    public IGeneratorOptionProvider getDimensionOptions(int id) {
        return dimensions.get(id);
    }

    public Integer[] getCustomDimensions() {
        return dimensions.keySet().toArray(new Integer[0]);
    }

    private boolean isDimension(IGeneratorOptionProvider options) {
        return options.getValue("cwg:dimensions:id", Optional.class).isPresent();
    }

    private void addDimension(IGeneratorOptionProvider options) {
        if (!isDimension(options)) return;
        Integer id = (Integer) options.getValue("cwg:dimensions:id", Optional.class).get();
        if (dimensions.containsKey(id)) {
            throw new RuntimeException("ALEC: Duplicate dimension ID: " + id);
        }
        dimensions.put(id, options);
        int providerId = options.getInt("cwg:dimensions:provider");
        DimensionManager.registerDimension(id, providerId);
    }

    @Override
    public void init(IGeneratorOptionManager manager) {
        IGeneratorOptionRegistry registry = manager.getOptionRegistry();
        registry.registerOption(new DimensionOption());
        registry.registerOption(new StringOption("cwg:dimensions:name", "Dimension Name", "Custom Dimension", true, false));
        registry.registerOption(new IntOption("cwg:dimensions:provider", "Provider ID", Config.dimensionProviderID, true, false));
        DimensionManager.registerProviderType(Config.dimensionProviderID, DimProvider.class, false);
        for(UUID id : manager.getOptionSets()) {
            IGeneratorOptionProvider options = manager.getOptions(id).get();
            this.addDimension(options);
        } 
        IMutableReference<IGeneratorOptionProvider> lastAdded = (IMutableReference<IGeneratorOptionProvider>) manager.getOptions(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        lastAdded.notify(this::addDimension);
    }

    public void initDimensions(IGeneratorOptionManager manager) {
        
    }
    
}
