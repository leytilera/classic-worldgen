package dev.tilera.cwg.worldtypes;

import dev.tilera.cwg.DelegateChunkManager;
import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.generator.IChunkManagerFactory;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IOption;
import dev.tilera.cwg.api.utils.StringOption;
import dev.tilera.cwg.hooks.ICavegenHook;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;

public class WorldtypeChunkManagerFactory implements IChunkManagerFactory {

    @Override
    public AbstractChunkManager createChunkManager(IGeneratorOptionProvider options, World world) {
        WorldType original = world.getWorldInfo().getTerrainType();
        WorldType type = options.getValue("cwg:generator.worldtype:worldtype", WorldType.class);
        String generatorOptions = options.getString("cwg:generator.worldtype:options");
        String gnBack = tempChangeOpts(world, generatorOptions, type);
        IChunkProvider provider = type.getChunkGenerator(world, generatorOptions);
        WorldChunkManager manager = type.getChunkManager(world);
        tempChangeOpts(world, gnBack, original);
        if (provider instanceof ChunkProviderGenerate) {
            ICavegenHook hook = options.getValue("cwg:cavegen_hook", IHookProvider.class).getHook(ICavegenHook.class);
            hook.setCavegen((ChunkProviderGenerate) provider);
        }
        return new DelegateChunkManager(options, provider, manager);
    }

    @Override
    public String getID() {
        return "cwg:worldtype";
    }

    @Override
    public String getDisplayName() {
        return "World Type";
    }

    @Override
    public boolean hasSpecificOption(IOption<?> option) {
        return option.getID().startsWith("cwg:generator.worldtype:");
    }

    private String tempChangeOpts(World world, String generatorOptions, WorldType worldType) {
        String temp = world.getWorldInfo().generatorOptions;
        world.getWorldInfo().generatorOptions = generatorOptions;
        world.getWorldInfo().terrainType = worldType;
        return temp;
    }

    @Override
    public void onRegister() {
        CwgGlobals.getOptionRegistry().registerOption(new WorldTypeOption());
        CwgGlobals.getOptionRegistry().registerOption(new StringOption("cwg:generator.worldtype:options", "Worldtype Generator Options", "", false, true));
    }

}
