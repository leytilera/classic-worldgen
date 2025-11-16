package dev.tilera.cwg;

import java.io.IOException;
import java.util.UUID;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionManager;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
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
        IGeneratorOptionManager optManager = CwgGlobals.getOptionManager().createWorldOptionManager(world);
        try {
            optManager.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String opts = world.getWorldInfo().getGeneratorOptions();
        UUID optId = null;
        try {
            optId = UUID.fromString(opts);
        } catch (IllegalArgumentException e) {
            optId = IGeneratorOptionManager.DEFAULT;
        }
        IGeneratorOptionProvider options = optManager.getOptions(optId).get();
        AbstractChunkManager manager = options.getValue("cwg:generator", IHookProvider.class).getHook(HookTypes.GENERATOR).createChunkManager(options, world);
        CwgGlobals.setCurrentState(world);
        try {
            optManager.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        IGeneratorOptionManager manager = CwgGlobals.getOptionManager();
        //instance.displayGuiScreen(new GuiCustomize(manager, guiCreateWorld, new OptionProvider(new Pointer<>(manager.getOptionRegistry()))));
    }

}
