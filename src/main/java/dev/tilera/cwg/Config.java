package dev.tilera.cwg;

import java.io.File;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;

public class Config {

    static Configuration conf;
    public static boolean enableSwissCheeseCaves = false;
    public static boolean addNewVanillaBiomes = false;
    public static boolean changeWorldTypeCommand = false;
    public static boolean classicExtremeHills = false;
    public static boolean enableFarlands = false;
    public static boolean disableJungle = false;
    public static boolean disableModdedBiomes = false;
    public static boolean enableDesertLakes = true;
    public static boolean enableModdedWorldgen = true;

    public static void initConfig() {
        conf = new Configuration(new File(Loader.instance().getConfigDir(), "ClassicWorldgen.cfg"));
        conf.load();
        enableSwissCheeseCaves = conf.getBoolean("enableSwissCheeseCaves", "caves", enableSwissCheeseCaves, "enable classic (1.6) cavegen");
        addNewVanillaBiomes = conf.getBoolean("addNewVanillaBiomes", "worldgen", addNewVanillaBiomes, "generate new 1.7 vanilla biomes with classic/1.6 WorldType");
        classicExtremeHills = conf.getBoolean("classicExtremeHills", "tweaks", classicExtremeHills, "generate 1.6 extreme hills instead of 1.7");
        changeWorldTypeCommand = conf.getBoolean("changeTypeCommand", "commands", changeWorldTypeCommand, "enable command to change the WorldType");
        enableFarlands = conf.getBoolean("enableFarlands", "tweaks", enableFarlands, "reenable the Farlands!");
        disableJungle = conf.getBoolean("disableJungle", "worldgen", disableJungle, "prevent jungle biomes from generating in classic worldgen");
        disableModdedBiomes = conf.getBoolean("disableModdedBiomes", "worldgen", disableModdedBiomes, "prevent modded biomes from generating in classic worldgen");
        enableDesertLakes = conf.getBoolean("enableDesertLakes", "worldgen", enableDesertLakes, "enable lakes in desert in classic worldgen");
        enableModdedWorldgen = conf.getBoolean("enableModdedWorldgen", "worldgen", enableModdedWorldgen, "enable worldgen features from other mods");
        conf.save();
    }

}
