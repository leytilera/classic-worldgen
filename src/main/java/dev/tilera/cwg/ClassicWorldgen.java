package dev.tilera.cwg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dev.tilera.cwg.caves.MapGenCavesSwiss;
import dev.tilera.cwg.command.CommandChangeWorld;
import dev.tilera.cwg.noisegen.NoiseGeneratorOctavesFarlands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;

@Mod(modid = Constants.ID, name = Constants.NAME, version = Constants.VERSION, acceptableRemoteVersions = "*")
public class ClassicWorldgen {

    public static final WorldType CLASSIC = new WorldTypeClassic("onesix");
    public static BiomeGenBase[] biomeCache = new BiomeGenBase[256];

    @Mod.Instance
    public static ClassicWorldgen INSTANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.initConfig();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(this);      
    }

    @SubscribeEvent
    public void onInitMapGen(InitMapGenEvent event) {
        if (event.type == InitMapGenEvent.EventType.CAVE && Config.enableSwissCheeseCaves) {
            event.newGen = new MapGenCavesSwiss();
        }
    }

    @SubscribeEvent
    public void onInitNoiseGen(InitNoiseGensEvent event) {
        if (Config.enableFarlands) {
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

    public static boolean isClassicWorld() {
        World[] worlds = MinecraftServer.getServer().worldServers;
        if (worlds.length == 0) return true;
        World world = worlds[0];
        if (world == null) return true;
        return isClassicWorld(world);
    }

    public static boolean isClassicWorld(World world) {
        return world.getWorldChunkManager() instanceof WorldChunkManagerClassic;
    }
    
}
