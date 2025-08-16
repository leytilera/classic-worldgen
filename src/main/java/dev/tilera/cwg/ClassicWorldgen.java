package dev.tilera.cwg;


import java.util.Set;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionRegistry;
import dev.tilera.cwg.biome.Biomes;
import dev.tilera.cwg.command.CommandChangeWorld;
import dev.tilera.cwg.hooks.HookRegistry;
import dev.tilera.cwg.api.hooks.common.ICavegenHook;
import dev.tilera.cwg.modules.IModule;
import dev.tilera.cwg.modules.ModuleDiscoverer;
import dev.tilera.cwg.noisegen.NoiseGeneratorOctavesFarlands;
import dev.tilera.cwg.options.ConfigProvider;
import dev.tilera.cwg.options.GlobalOptionManager;
import dev.tilera.cwg.options.OptionRegistry;
import dev.tilera.cwg.proxy.CommonProxy;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;

@Mod(modid = Constants.ID, name = Constants.NAME, version = Constants.VERSION, acceptableRemoteVersions = "*")
public class ClassicWorldgen {

    public static final WorldTypeCustom CUSTOM = new WorldTypeCustom();
    public static final WorldTypeClassic CLASSIC = new WorldTypeClassic();
    public static ConfigProvider CONFIG;
    public static BiomeGenBase[] biomeCache = new BiomeGenBase[256];

    @Mod.Instance
    public static ClassicWorldgen INSTANCE;
    @SidedProxy(modId = Constants.ID, serverSide = "dev.tilera.cwg.proxy.CommonProxy", clientSide = "dev.tilera.cwg.proxy.ClientProxy")
    public static CommonProxy proxy;

    Set<IModule> modules;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig();
        IGeneratorOptionRegistry optionRegistry = new OptionRegistry();
        CONFIG = new ConfigProvider(optionRegistry);
        CwgGlobals.setOptionManager(new GlobalOptionManager(optionRegistry, new HookRegistry()));
        CwgGlobals.setCurrentState(null);
        HookTypes.init(CwgGlobals.getHookRegistry());
        modules = new ModuleDiscoverer(event.getAsmData()).process();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Biomes.init();
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(this);
        initModules();

    }

    public void initModules() {
        for (IModule module : modules) {
            module.init(CwgGlobals.getOptionManager());
        }
    }

    @SubscribeEvent
    public void onInitMapGen(InitMapGenEvent event) {
        ICavegenHook hook = CwgGlobals.getOptionProvider().getValue("cwg:cavegen_hook", IHookProvider.class).getHook(HookTypes.CAVEGEN);
        if (event.type == InitMapGenEvent.EventType.CAVE) {
            event.newGen = hook.createCaveGenerator();
        } else if (event.type == InitMapGenEvent.EventType.RAVINE) {
            event.newGen = hook.createRavineGenerator();
        }
    }

    @SubscribeEvent
    public void onInitNoiseGen(InitNoiseGensEvent event) {
        if (CwgGlobals.getOptionProvider(event.world).getBoolean("cwg:farlands")) {
            NoiseGenerator[] noiseGens = new NoiseGenerator[event.originalNoiseGens.length];
            for (int i = 0; i < noiseGens.length; i++) {
                if (event.originalNoiseGens[i] instanceof NoiseGeneratorOctaves) {
                    int octaves = ((NoiseGeneratorOctaves)event.originalNoiseGens[i]).octaves;
                    noiseGens[i] = new NoiseGeneratorOctavesFarlands(event.rand, octaves);
                } else {
                    noiseGens[i] = event.originalNoiseGens[i];
                }
            }
            event.newNoiseGens = noiseGens;
        }
    }

    @EventHandler
    public void onServerLoad(FMLServerStartingEvent event) {
        if (Config.changeWorldTypeCommand)
            event.registerServerCommand(new CommandChangeWorld());
    }

    @Mod.EventHandler
    public void onServerStop(FMLServerStoppedEvent event) {
        CwgGlobals.setCurrentState(null);
    }
    
}
