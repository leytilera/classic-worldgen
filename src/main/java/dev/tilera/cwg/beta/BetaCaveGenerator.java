package dev.tilera.cwg.beta;

import java.util.Random;

import dev.tilera.cwg.infdev.ChunkConverter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;

public class BetaCaveGenerator extends MapGenBase {

    protected void func_870_a(int var1, int var2, Block[] var3, double var4, double var6, double var8) {
		this.releaseEntitySkin(var1, var2, var3, var4, var6, var8, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}

	protected void releaseEntitySkin(int i, int j, Block[] blocks, double d, double d1, double d2, float f, float f1, float f2, int k, int l, double d3) {
      double d4 = (double)(i * 16 + 8);
      double d5 = (double)(j * 16 + 8);
      float f3 = 0.0F;
      float f4 = 0.0F;
      Random random = new Random(super.rand.nextLong());
      if (l <= 0) {
         int i1 = super.range * 16 - 16;
         l = i1 - random.nextInt(i1 / 4);
      }

      boolean flag = false;
      if (k == -1) {
         k = l / 2;
         flag = true;
      }

      int j1 = random.nextInt(l / 2) + l / 4;

      for(boolean flag1 = random.nextInt(6) == 0; k < l; ++k) {
         double d6 = (double)1.5F + (double)(MathHelper.sin((float)k * 3.141593F / (float)l) * f * 1.0F);
         double d7 = d6 * d3;
         float f5 = MathHelper.cos(f2);
         float f6 = MathHelper.sin(f2);
         d += (double)(MathHelper.cos(f1) * f5);
         d1 += (double)f6;
         d2 += (double)(MathHelper.sin(f1) * f5);
         if (flag1) {
            f2 *= 0.92F;
         } else {
            f2 *= 0.7F;
         }

         f2 += f4 * 0.1F;
         f1 += f3 * 0.1F;
         f4 *= 0.9F;
         f3 *= 0.75F;
         f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (!flag && k == j1 && f > 1.0F) {
            this.releaseEntitySkin(i, j, blocks, d, d1, d2, random.nextFloat() * 0.5F + 0.5F, f1 - 1.570796F, f2 / 3.0F, k, l, (double)1.0F);
            this.releaseEntitySkin(i, j, blocks, d, d1, d2, random.nextFloat() * 0.5F + 0.5F, f1 + 1.570796F, f2 / 3.0F, k, l, (double)1.0F);
            return;
         }

         if (flag || random.nextInt(4) != 0) {
            double d8a = d - d4;
            double d9a = d2 - d5;
            double d10a = (double)(l - k);
            double d11 = (double)(f + 2.0F + 16.0F);
            if (d8a * d8a + d9a * d9a - d10a * d10a > d11 * d11) {
               return;
            }

            if (!(d < d4 - (double)16.0F - d6 * (double)2.0F) && !(d2 < d5 - (double)16.0F - d6 * (double)2.0F) && !(d > d4 + (double)16.0F + d6 * (double)2.0F) && !(d2 > d5 + (double)16.0F + d6 * (double)2.0F)) {
               int d8 = MathHelper.floor_double(d - d6) - i * 16 - 1;
               int k1 = MathHelper.floor_double(d + d6) - i * 16 + 1;
               int d9 = MathHelper.floor_double(d1 - d7) - 1;
               int l1 = MathHelper.floor_double(d1 + d7) + 1;
               int d10 = MathHelper.floor_double(d2 - d6) - j * 16 - 1;
               int i2 = MathHelper.floor_double(d2 + d6) - j * 16 + 1;
               if (d8 < 0) {
                  d8 = 0;
               }

               if (k1 > 16) {
                  k1 = 16;
               }

               if (d9 < 1) {
                  d9 = 1;
               }

               if (l1 > 120) {
                  l1 = 120;
               }

               if (d10 < 0) {
                  d10 = 0;
               }

               if (i2 > 16) {
                  i2 = 16;
               }

               boolean flag2 = false;

               for(int j2 = d8; !flag2 && j2 < k1; ++j2) {
                  for(int l2 = d10; !flag2 && l2 < i2; ++l2) {
                     for(int i3 = l1 + 1; !flag2 && i3 >= d9 - 1; --i3) {
                        int j3 = (j2 * 16 + l2) * 128 + i3;
                        if (i3 >= 0 && i3 < 128) {
                           if (blocks[j3] == Blocks.flowing_water || blocks[j3] == Blocks.water) {
                              flag2 = true;
                           }

                           if (i3 != d9 - 1 && j2 != d8 && j2 != k1 - 1 && l2 != d10 && l2 != i2 - 1) {
                              i3 = d9;
                           }
                        }
                     }
                  }
               }

               if (!flag2) {
                  for(int k2 = d8; k2 < k1; ++k2) {
                     double d12 = ((double)(k2 + i * 16) + (double)0.5F - d) / d6;

                     for(int k3 = d10; k3 < i2; ++k3) {
                        double d13 = ((double)(k3 + j * 16) + (double)0.5F - d2) / d6;
                        int l3 = (k2 * 16 + k3) * 128 + l1;
                        boolean flag3 = false;
                        if (!(d12 * d12 + d13 * d13 >= (double)1.0F)) {
                           for(int i4 = l1 - 1; i4 >= d9; --i4) {
                              double d14 = ((double)i4 + (double)0.5F - d1) / d7;
                              if (d14 > -0.7 && d12 * d12 + d14 * d14 + d13 * d13 < (double)1.0F) {
                                 Block byte0 = blocks[l3];
                                 if (byte0 == Blocks.grass) {
                                    flag3 = true;
                                 }

                                 if (byte0 == Blocks.stone || byte0 == Blocks.dirt || byte0 == Blocks.grass) {
                                    if (i4 < 10) {
                                       blocks[l3] = Blocks.flowing_lava;
                                    } else {
                                       blocks[l3] = Blocks.air;
                                       if (flag3 && blocks[l3 - 1] == Blocks.dirt) {
                                          blocks[l3 - 1] = Blocks.grass;
                                       }
                                    }
                                 }
                              }

                              --l3;
                           }
                        }
                     }
                  }

                  if (flag) {
                     break;
                  }
               }
            }
         }
      }

   }

    @Override
	protected void func_151538_a(World var1, int var2, int var3, int var4, int var5, Block[] var6) {
		int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);
		if(this.rand.nextInt(15) != 0) {
			var7 = 0;
		}

		for(int var8 = 0; var8 < var7; ++var8) {
			double var9 = (double)(var2 * 16 + this.rand.nextInt(16));
			double var11 = (double)this.rand.nextInt(this.rand.nextInt(120) + 8);
			double var13 = (double)(var3 * 16 + this.rand.nextInt(16));
			int var15 = 1;
			if(this.rand.nextInt(4) == 0) {
				this.func_870_a(var4, var5, var6, var9, var11, var13);
				var15 += this.rand.nextInt(4);
			}

			for(int var16 = 0; var16 < var15; ++var16) {
				float var17 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float var18 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float var19 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
				this.releaseEntitySkin(var4, var5, var6, var9, var11, var13, var19, var17, var18, 0, 0, 1.0D);
			}
		}
	}

    @Override
    public void func_151539_a(IChunkProvider p_151539_1_, World p_151539_2_, int p_151539_3_, int p_151539_4_, Block[] p_151539_5_)
    {
        Block[] blockus = new Block[32768];
        ChunkConverter.convert(p_151539_5_, blockus);
        int k = this.range;
        this.worldObj = p_151539_2_;
        this.rand.setSeed(p_151539_2_.getSeed());
        long l = this.rand.nextLong() / 2L * 2L + 1L;
		long i1 = this.rand.nextLong() / 2L * 2L + 1L;

        for (int j1 = p_151539_3_ - k; j1 <= p_151539_3_ + k; ++j1)
        {
            for (int k1 = p_151539_4_ - k; k1 <= p_151539_4_ + k; ++k1)
            {
                this.rand.setSeed((long)j1 * l + (long)k1 * i1 ^ p_151539_2_.getSeed());
                this.func_151538_a(p_151539_2_, j1, k1, p_151539_3_, p_151539_4_, blockus);
            }
        }
        ChunkConverter.convert(blockus, p_151539_5_);
    }
    
}
