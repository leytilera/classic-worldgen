package dev.tilera.cwg.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import dev.tilera.cwg.api.CwgGlobals;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenerator;

@Mixin(WorldGenDoublePlant.class)
public abstract class MixinGenDoublePlant extends WorldGenerator{
    
    @Shadow(remap = false)
    private int field_150549_a;

    /**
     * @author tilera
     * @reason disable doule plants
     */
    @Overwrite(remap = false)
    public boolean generate(World p_generate_1_, Random p_generate_2_, int p_generate_3_, int p_generate_4_, int p_generate_5_) {
        boolean var6 = false;
  
        if (!CwgGlobals.getOptionProvider(p_generate_1_).getBoolean("cwg:disable_tall_flowers")) {
            for(int var7 = 0; var7 < 64; ++var7) {
                int var8 = p_generate_3_ + p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8);
                int var9 = p_generate_4_ + p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4);
                int var10 = p_generate_5_ + p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8);
                if (p_generate_1_.isAirBlock(var8, var9, var10) && (!p_generate_1_.provider.hasNoSky || var9 < 254) && Blocks.double_plant.canPlaceBlockAt(p_generate_1_, var8, var9, var10)) {
                Blocks.double_plant.func_149889_c(p_generate_1_, var8, var9, var10, this.field_150549_a, 2);
                var6 = true;
                }
            }
        }
  
        return var6;
     }

}
