package dev.tilera.cwg;

import java.io.File;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;

public class Config {

    static Configuration conf;
    public static boolean enableJungleMelons = false;
    public static boolean enableHeightSnow = false;
    public static boolean classicExtremeHills = true;
    public static boolean enableDoublePlants = false;
    public static boolean enableNewFlowers = false;
    public static boolean enableSwissCheeseCaves = true;
    public static boolean classicWorldGen = true;
    public static boolean blockNewVanillaBiomes = true;
    public static boolean addNewVanillaBiomes = false;
    public static boolean respectBiomeWeight = false;

    public static void initConfig() {
        conf = new Configuration(new File(Loader.instance().getConfigDir(), "ClassicWorldgen.cfg"));
        conf.load();
        enableJungleMelons = conf.getBoolean("enableJungleMelons", "plants", enableJungleMelons, "Enable melons generating in jungle biomes");
        enableHeightSnow = conf.getBoolean("enableHeightSnow", "hills", enableHeightSnow, "Enable snow on mountains");
        classicExtremeHills = conf.getBoolean("classicExtremeHills", "hills", classicExtremeHills, "Enable classic extreme hills (grass instread of stone of top)");
        enableDoublePlants = conf.getBoolean("enableDoublePlants", "plants", enableDoublePlants, "Enable double plants");
        enableNewFlowers = conf.getBoolean("enableNewFlowers", "plants", enableNewFlowers, "Enable new 1.7 flowers");
        enableSwissCheeseCaves = conf.getBoolean("enableSwissCheeseCaves", "caves", enableSwissCheeseCaves, "Enable classic cavegen");
        classicWorldGen = conf.getBoolean("classicWorldGen", "worldgen", classicWorldGen, "Enable the classic genlayer stack");
        blockNewVanillaBiomes = conf.getBoolean("blockNewVanillaBiomes", "worldgen", blockNewVanillaBiomes, "prevent new 1.7 vanilla biomes from generating with classicWorldGen");
        addNewVanillaBiomes = conf.getBoolean("addNewVanillaBiomes", "worldgen", addNewVanillaBiomes, "generate new 1.7 vanilla biomes with classicWorldGen");
        respectBiomeWeight = conf.getBoolean("respectBiomeWeight", "worldgen", respectBiomeWeight, "respect the biome weight with classicWorldGen");
        conf.save();
    }

}
