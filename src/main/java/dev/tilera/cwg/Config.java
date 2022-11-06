package dev.tilera.cwg;

import java.io.File;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;

public class Config {

    static Configuration conf;
    public static boolean enableSwissCheeseCaves = false;
    public static boolean blockNewVanillaBiomes = true;
    public static boolean addNewVanillaBiomes = false;
    public static boolean changeWorldTypeCommand = false;

    public static void initConfig() {
        conf = new Configuration(new File(Loader.instance().getConfigDir(), "ClassicWorldgen.cfg"));
        conf.load();
        enableSwissCheeseCaves = conf.getBoolean("enableSwissCheeseCaves", "caves", enableSwissCheeseCaves, "enable classic (1.6) cavegen");
        blockNewVanillaBiomes = conf.getBoolean("blockNewVanillaBiomes", "worldgen", blockNewVanillaBiomes, "prevent new 1.7 vanilla biomes from generating with classic/1.6 WorldType");
        addNewVanillaBiomes = conf.getBoolean("addNewVanillaBiomes", "worldgen", addNewVanillaBiomes, "generate new 1.7 vanilla biomes with classic/1.6 WorldType");
        changeWorldTypeCommand = conf.getBoolean("changeTypeCommand", "commands", changeWorldTypeCommand, "enable command to change the WorldType");
        conf.save();
    }

}
