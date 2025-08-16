package dev.tilera.cwg;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.gui.GuiCustomize;
import dev.tilera.cwg.options.OptionProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldTypeCustom extends WorldType {

    public WorldTypeCustom() {
        super("cwg");
    }

    @Override
    public WorldChunkManager getChunkManager(World world) {
        IGeneratorOptionRegistry registry = CwgGlobals.getOptionRegistry();
        String opts = world.getWorldInfo().getGeneratorOptions();
        IGeneratorOptionProvider options;
        if (opts.isEmpty()) {
            options = registry;
        } else {
            try {
                options = registry.decodeOptions(opts);
            } catch (Exception e) {
                e.printStackTrace();
                options = registry;
            }
        }
        AbstractChunkManager manager = options.getValue("cwg:generator", IHookProvider.class).getHook(HookTypes.GENERATOR).createChunkManager(options, world);
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
    public boolean isCustomizable() {
        return true;
    }

    @Override
    public void onCustomizeButton(Minecraft instance, GuiCreateWorld guiCreateWorld) {
        IGeneratorOptionRegistry registry = CwgGlobals.getOptionRegistry();
        instance.displayGuiScreen(new GuiCustomize(registry, guiCreateWorld, new OptionProvider(registry)));
    }

}
