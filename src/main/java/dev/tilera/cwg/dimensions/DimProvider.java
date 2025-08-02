package dev.tilera.cwg.dimensions;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class DimProvider extends WorldProvider {

    @Override
    public String getDimensionName() {
        return getOptions().getString("cwg:dimensions:name");
    }

    @Override
    public String getSaveFolder() {
        return "cwg/DIM" + dimensionId;  
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return ((AbstractChunkManager)this.worldChunkMgr).getGenerator(this.worldObj);
    }

    @Override
    protected void registerWorldChunkManager() {
        IGeneratorOptionProvider options = CustomDimensions.INSTANCE.getDimensionOptions(dimensionId);
        this.worldChunkMgr = options.getValue("cwg:generator", IHookProvider.class).getHook(HookTypes.GENERATOR).createChunkManager(options, worldObj);
    }

    protected IGeneratorOptionProvider getOptions() {
        return ((AbstractChunkManager)this.worldChunkMgr).getOptionProvider();
    }
    
}
