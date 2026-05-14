package dev.tilera.cwg.biome;

import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeGenBeta extends BiomeGenBase {

    BetaBiomeType type;
    public static WorldGenAbstractTree forestTrees = new WorldGenForest(false, false);
    public static WorldGenAbstractTree taigaTrees1 = new WorldGenTaiga1();
    public static WorldGenAbstractTree taigaTrees2 = new WorldGenTaiga2(false);

    public BiomeGenBeta(int id, BetaBiomeType type) {
        super(id);
        this.type = type;
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random rand) {
        if (type == BetaBiomeType.RAINFOREST) {
            return rand.nextInt(3) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees;
        } else if (type == BetaBiomeType.FOREST) {
            return rand.nextInt(5) == 0 ? forestTrees : (rand.nextInt(3) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees);
        } else if (type == BetaBiomeType.TAIGA) {
            return rand.nextInt(3) == 0 ? taigaTrees1 : taigaTrees2;
        }
        return super.func_150567_a(rand);
    }    
    
}
