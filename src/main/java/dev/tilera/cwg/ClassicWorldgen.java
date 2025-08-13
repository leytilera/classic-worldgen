package dev.tilera.cwg;

import java.util.ArrayList;
import java.util.List;

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
import dev.tilera.cwg.classic.ClassicModule;
import dev.tilera.cwg.command.CommandChangeWorld;
import dev.tilera.cwg.dimensions.CustomDimensions;
import dev.tilera.cwg.dimensions.DimProvider;
import dev.tilera.cwg.hooks.HookRegistry;
import dev.tilera.cwg.hooks.HooksModule;
import dev.tilera.cwg.hooks.ICavegenHook;
import dev.tilera.cwg.infdev.InfdevModule;
import dev.tilera.cwg.modules.IModule;
import dev.tilera.cwg.noisegen.NoiseGeneratorOctavesFarlands;
import dev.tilera.cwg.options.ConfigProvider;
import dev.tilera.cwg.options.GlobalOptionManager;
import dev.tilera.cwg.options.OptionRegistry;
import dev.tilera.cwg.proxy.CommonProxy;
import dev.tilera.cwg.quadrants.QuadrantsModule;
import dev.tilera.cwg.worldtypes.WorldtypeModule;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.DimensionManager;
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

    List<IModule> modules = new ArrayList<>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig();
        IGeneratorOptionRegistry optionRegistry = new OptionRegistry();
        CONFIG = new ConfigProvider(optionRegistry);
        CustomDimensions.INSTANCE = new CustomDimensions(optionRegistry);
        CwgGlobals.setOptionManager(new GlobalOptionManager(optionRegistry, new HookRegistry()));
        CwgGlobals.setDefaultProvider(CONFIG);
        CwgGlobals.setCurrentState(null);
        HookTypes.init(CwgGlobals.getHookRegistry());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Biomes.init();
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(this);
        addModules();
        registerGenerators();
        registerOptions();
        registerHooks();
        DimensionManager.registerProviderType(Config.dimensionProviderID, DimProvider.class, false);
        CustomDimensions.INSTANCE.readConfig(Config.dimensionsDefinition);
        CustomDimensions.INSTANCE.registerDimensions();
    }

    public void addModules() {
        modules.add(CustomDimensions.INSTANCE);
        modules.add(new DefaultModule());
        modules.add(new HooksModule());
        modules.add(new ClassicModule());
        modules.add(new InfdevModule());
        modules.add(new WorldtypeModule());
        modules.add(new QuadrantsModule());
    }

    public void registerOptions() {
        for(IModule module : modules) {
            module.registerOptions(CwgGlobals.getOptionRegistry());
        }
    }

    public void registerHooks() {
        for(IModule module : modules) {
            module.registerHooks(CwgGlobals.getHookRegistry());
        }
    }

    public void registerGenerators() {
        for(IModule module : modules) {
            module.registerGenerators(CwgGlobals.getHookRegistry());
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
