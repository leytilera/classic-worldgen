package dev.tilera.cwg;

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
import dev.tilera.cwg.api.utils.BooleanOption;
import dev.tilera.cwg.api.utils.IntOption;
import dev.tilera.cwg.api.utils.StringOption;
import dev.tilera.cwg.classic.ClassicChunkManagerFactory;
import dev.tilera.cwg.command.CommandChangeWorld;
import dev.tilera.cwg.dimensions.CustomDimensions;
import dev.tilera.cwg.dimensions.DimProvider;
import dev.tilera.cwg.hooks.DefaultCavegenHook;
import dev.tilera.cwg.hooks.DefaultTemperatureHook;
import dev.tilera.cwg.hooks.HookOption;
import dev.tilera.cwg.hooks.HookRegistry;
import dev.tilera.cwg.hooks.ICavegenHook;
import dev.tilera.cwg.hooks.SwissCavegenHook;
import dev.tilera.cwg.noisegen.NoiseGeneratorOctavesFarlands;
import dev.tilera.cwg.options.ChunkManagerOption;
import dev.tilera.cwg.options.ConfigProvider;
import dev.tilera.cwg.proxy.CommonProxy;
import dev.tilera.cwg.vanilla.SingleBiomeChunkManagerFactory;
import dev.tilera.cwg.vanilla.VanillaChunkManagerFactory;
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
    public static final ConfigProvider CONFIG = new ConfigProvider(CUSTOM);
    public static BiomeGenBase[] biomeCache = new BiomeGenBase[256];

    @Mod.Instance
    public static ClassicWorldgen INSTANCE;
    @SidedProxy(modId = Constants.ID, serverSide = "dev.tilera.cwg.proxy.CommonProxy", clientSide = "dev.tilera.cwg.proxy.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig();
        CustomDimensions.INSTANCE = new CustomDimensions(CUSTOM);
        CwgGlobals.setOptionRegistry(CUSTOM);
        CwgGlobals.setDefaultProvider(CONFIG);
        CwgGlobals.setGeneratorRegistry(CUSTOM);
        CwgGlobals.setHookRegistry(new HookRegistry());
        CwgGlobals.setCurrentState(null);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(this);
        registerGenerators();
        registerOptions();
        registerHooks();
        DimensionManager.registerProviderType(Config.dimensionProviderID, DimProvider.class, false);
        CustomDimensions.INSTANCE.readConfig(Config.dimensionsDefinition);
        CustomDimensions.INSTANCE.registerDimensions();
    }

    public void registerOptions() {
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:classic_extreme_hills", "Classic Extreme Hills", false));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:farlands", "Enable Farlands", false));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:desert_lakes", "Enable Desert Lakes", false));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:generator.classic:disableJungle", "Disable Jungle", false, true));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:generator.classic:newVanillaBiomes", "New Vanilla Biomes", false, true));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:generator.classic:disableModdedBiomes", "No Modded Biomes", false, true));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:generator.classic:enableModdedWorldgen", "Modded Worldgen", true, true));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:disable_jungle_melons", "Disable Jungle Melons", false));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:disable_new_flowers", "Disable new Flowers", false));
        CwgGlobals.getOptionRegistry().registerOption(new BooleanOption("cwg:disable_tall_flowers", "Disable Tall Flowers", false));
        CwgGlobals.getOptionRegistry().registerOption(new StringOption("cwg:dimensions:name", "Dimension Name", "Custom Dimension", true));
        CwgGlobals.getOptionRegistry().registerOption(new IntOption("cwg:dimensions:provider", "Provider ID", Config.dimensionProviderID, true, false));
        CwgGlobals.getOptionRegistry().registerOption(new IntOption("cwg:generator.singleBiome:biomeID", "Biome ID", 0, false, true));
    }

    public void registerHooks() {
        IHookProvider tempDef = new DefaultTemperatureHook();
        CwgGlobals.getHookRegistry().registerHookProvider(tempDef);
        CwgGlobals.getOptionRegistry().registerOption(new HookOption("cwg:temperature_hook", tempDef, CwgGlobals.getHookRegistry()));
        IHookProvider caveDef = new DefaultCavegenHook();
        CwgGlobals.getHookRegistry().registerHookProvider(caveDef);
        CwgGlobals.getHookRegistry().registerHookProvider(new SwissCavegenHook());
        CwgGlobals.getOptionRegistry().registerOption(new HookOption("cwg:cavegen_hook", caveDef, CwgGlobals.getHookRegistry()));
    }

    public void registerGenerators() {
        VanillaChunkManagerFactory def = new VanillaChunkManagerFactory();
        CwgGlobals.getGeneratorRegistry().registerChunkManager(def);
        CwgGlobals.getGeneratorRegistry().registerChunkManager(new ClassicChunkManagerFactory());
        CwgGlobals.getGeneratorRegistry().registerChunkManager(new SingleBiomeChunkManagerFactory());
        CwgGlobals.getOptionRegistry().registerOption(new ChunkManagerOption(
            "cwg:generator", 
            "Generator",
            def,
            CwgGlobals.getGeneratorRegistry()
        ));
    }

    @SubscribeEvent
    public void onInitMapGen(InitMapGenEvent event) {
        ICavegenHook hook = CwgGlobals.getOptionProvider().getValue("cwg:cavegen_hook", IHookProvider.class).getHook(ICavegenHook.class);
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
