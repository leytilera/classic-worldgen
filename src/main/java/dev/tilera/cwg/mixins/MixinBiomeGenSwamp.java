package dev.tilera.cwg.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import dev.tilera.cwg.Config;
import net.minecraft.block.BlockFlower;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenSwamp;

@Mixin(BiomeGenSwamp.class)
public abstract class MixinBiomeGenSwamp extends BiomeGenBase {

    public MixinBiomeGenSwamp(int arg0) {
        super(arg0);
    }
    
    /**
     * @author tilera
     * @reason disable new flowers
     */
    @Overwrite(remap = false)
    public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_) {
        if (!Config.disableNewFlowers) {
            return BlockFlower.field_149859_a[1];
        }
        return super.func_150572_a(p_150572_1_, p_150572_2_, p_150572_3_, p_150572_4_);
    }

}
