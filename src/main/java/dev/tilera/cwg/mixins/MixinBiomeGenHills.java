package dev.tilera.cwg.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import dev.tilera.cwg.ClassicWorldgen;
import dev.tilera.cwg.Config;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenHills;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

@Mixin(BiomeGenHills.class)
public abstract class MixinBiomeGenHills extends BiomeGenBase {

    @Shadow(remap = false)
    private WorldGenTaiga2 field_150634_aD;
    @Shadow(remap = false)
    private int field_150636_aF;
    @Shadow(remap = false)
    private int field_150637_aG;
    @Shadow(remap = false)
    private int field_150638_aH;

    public MixinBiomeGenHills(int arg0) {
        super(arg0);
    }

    /**
     * @author tilera
     * @reason old tree generation
     */
    @Overwrite(remap = false)
    public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
        if (Config.classicExtremeHills)
            return super.func_150567_a(p_150567_1_);
        else
            return (WorldGenAbstractTree)(p_150567_1_.nextInt(3) > 0 ? this.field_150634_aD : super.func_150567_a(p_150567_1_));
    }
    
    /**
     * @author tilera
     * @reason old terrain generation
     */
    @Overwrite(remap = false)
    public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
        if (p_150573_1_.getWorldInfo().getTerrainType() == ClassicWorldgen.CLASSIC || Config.classicExtremeHills) {
            super.genTerrainBlocks(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
        } else {
            this.topBlock = Blocks.grass;
            this.field_150604_aj = 0;
            this.fillerBlock = Blocks.dirt;
            if ((p_150573_7_ < -1.0D || p_150573_7_ > 2.0D) && this.field_150638_aH == this.field_150637_aG) {
                this.topBlock = Blocks.gravel;
                this.fillerBlock = Blocks.gravel;
            } else if (p_150573_7_ > 1.0D && this.field_150638_aH != this.field_150636_aF) {
                this.topBlock = Blocks.stone;
                this.fillerBlock = Blocks.stone;
      }

      this.genBiomeTerrain(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
        }
    }

}
