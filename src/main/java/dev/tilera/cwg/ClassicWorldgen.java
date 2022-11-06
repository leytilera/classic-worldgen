package dev.tilera.cwg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dev.tilera.cwg.caves.MapGenCavesSwiss;
import dev.tilera.cwg.command.CommandChangeWorld;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.InitMapGenEvent;

@Mod(modid = Constants.ID, name = Constants.NAME, version = Constants.VERSION)
public class ClassicWorldgen {

    public static final WorldType CLASSIC = new WorldTypeClassic("onesix");
    public static WorldType USED = null;

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
        if (event.type == InitMapGenEvent.EventType.CAVE && (Config.enableSwissCheeseCaves || USED == CLASSIC)) {
            event.newGen = new MapGenCavesSwiss();
        }
    }

    @EventHandler
    public void onServerLoad(FMLServerStartingEvent event) {
        USED = event.getServer().getEntityWorld().getWorldInfo().getTerrainType();
        if (Config.changeWorldTypeCommand)
            event.registerServerCommand(new CommandChangeWorld());
    }
    
}
