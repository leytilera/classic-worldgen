package dev.tilera.cwg.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import dev.tilera.cwg.Config;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenVines;

@Mixin(BiomeGenJungle.class)
public abstract class MixinBiomeGenJungle extends BiomeGenBase {

    public MixinBiomeGenJungle(int arg0) {
        super(arg0);
    }
    
    /**
     * @author tilera
     * @reason Remove melons
     */
    @Overwrite(remap = false)
    public void decorate(World p_76728_1_, Random p_76728_2_, int p_76728_3_, int p_76728_4_) {
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
        int k = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
        int l = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
        int height = p_76728_1_.getHeightValue(k, l) * 2;
        if (height < 1) {
           height = 1;
        }
  
        int i1 = p_76728_2_.nextInt(height);
        if (Config.enableJungleMelons)
            (new WorldGenMelon()).generate(p_76728_1_, p_76728_2_, k, i1, l);
        WorldGenVines worldgenvines = new WorldGenVines();
  
        for(l = 0; l < 50; ++l) {
           i1 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
           short short1 = 128;
           int j1 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
           worldgenvines.generate(p_76728_1_, p_76728_2_, i1, short1, j1);
        }
    }

}
