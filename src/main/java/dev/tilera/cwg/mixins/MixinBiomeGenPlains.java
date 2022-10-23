package dev.tilera.cwg.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import dev.tilera.cwg.Config;
import net.minecraft.block.BlockFlower;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenPlains;

@Mixin(BiomeGenPlains.class)
public abstract class MixinBiomeGenPlains extends BiomeGenBase {

    public MixinBiomeGenPlains(int arg0) {
        super(arg0);
    }
    
    /**
     * @author tilera
     * @reason disable new flowers
     */
    @Overwrite(remap = false)
    public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_) {
        if (Config.enableNewFlowers) {
            double d0 = plantNoise.func_151601_a((double)p_150572_2_ / 200.0D, (double)p_150572_4_ / 200.0D);
            int l;
            if (d0 < -0.8D) {
                l = p_150572_1_.nextInt(4);
                return BlockFlower.field_149859_a[4 + l];
            } else if (p_150572_1_.nextInt(3) > 0) {
                l = p_150572_1_.nextInt(3);
                return l == 0 ? BlockFlower.field_149859_a[0] : (l == 1 ? BlockFlower.field_149859_a[3] : BlockFlower.field_149859_a[8]);
            } else {
                return BlockFlower.field_149858_b[0];
            }
        }
        return super.func_150572_a(p_150572_1_, p_150572_2_, p_150572_3_, p_150572_4_);
    }

}
