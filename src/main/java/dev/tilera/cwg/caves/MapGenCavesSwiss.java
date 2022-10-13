package dev.tilera.cwg.caves;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenCavesSwiss extends MapGenCaves {

   @Override
   protected void func_151538_a(World par1World, int par2, int par3, int par4, int par5, Block[] par6ArrayOfByte) {
      int i1 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);
      if (this.rand.nextInt(15) != 0) {
         i1 = 0;
      }

      for(int j1 = 0; j1 < i1; ++j1) {
         double d0 = (double)(par2 * 16 + this.rand.nextInt(16));
         double d1 = (double)this.rand.nextInt(this.rand.nextInt(120) + 8);
         double d2 = (double)(par3 * 16 + this.rand.nextInt(16));
         int k1 = 1;
         if (this.rand.nextInt(4) == 0) {
            this.func_151542_a(this.rand.nextLong(), par4, par5, par6ArrayOfByte, d0, d1, d2);
            k1 += this.rand.nextInt(4);
         }

         for(int l1 = 0; l1 < k1; ++l1) {
            float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
            float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
            if (this.rand.nextInt(10) == 0) {
               f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
            }

            this.func_151541_a(this.rand.nextLong(), par4, par5, par6ArrayOfByte, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
         }
      }

   }

}
