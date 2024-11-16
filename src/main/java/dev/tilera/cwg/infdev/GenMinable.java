package dev.tilera.cwg.infdev;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenMinable extends WorldGenerator {
    private Block minableBlock;
    private int numberOfBlocks;
    private int generatortype;
 
    public GenMinable(Block b, int j, int type) {
       this.minableBlock = b;
       this.numberOfBlocks = j;
       this.generatortype = type;
    }
 
    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
       float f;
       double d;
       double d1;
       double d2;
       double d3;
       double d4;
       double d5;
       int l;
       double d6;
       double d7;
       double d8;
       double d9;
       double d10;
       double d11;
       int i1;
       int j1;
       int k1;
       if (this.generatortype != 0 && this.generatortype != 1) {
          f = random.nextFloat() * 3.141593F;
          d = (double)((float)(i + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
          d1 = (double)((float)(i + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
          d2 = (double)((float)(k + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
          d3 = (double)((float)(k + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
          d4 = (double)(j + random.nextInt(3) + 2);
          d5 = (double)(j + random.nextInt(3) + 2);
 
          for(l = 0; l <= this.numberOfBlocks; ++l) {
             d6 = d + (d1 - d) * (double)l / (double)this.numberOfBlocks;
             d7 = d4 + (d5 - d4) * (double)l / (double)this.numberOfBlocks;
             d8 = d2 + (d3 - d2) * (double)l / (double)this.numberOfBlocks;
             d9 = random.nextDouble() * (double)this.numberOfBlocks / 16.0D;
             d10 = (double)(MathHelper.sin((float)l * 3.141593F / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
             d11 = (double)(MathHelper.sin((float)l * 3.141593F / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
             i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
             j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
             k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
             int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
             int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
             int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);
 
             for(int k2 = i1; k2 <= l1; ++k2) {
                double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2.0D);
                if (!(d12 * d12 >= 1.0D)) {
                   for(int l2 = j1; l2 <= i2; ++l2) {
                      double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2.0D);
                      if (!(d12 * d12 + d13 * d13 >= 1.0D)) {
                         for(int i3 = k1; i3 <= j2; ++i3) {
                            double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2.0D);
                            if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlock(k2, l2, i3) == Blocks.stone) {
                               world.setBlock(k2, l2, i3, this.minableBlock);
                            }
                         }
                      }
                   }
                }
             }
          }
 
          return true;
       } else {
          f = random.nextFloat() * 3.141593F;
          d = (double)((float)(i + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
          d1 = (double)((float)(i + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
          d2 = (double)((float)(k + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
          d3 = (double)((float)(k + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
          d4 = (double)(j + random.nextInt(3) + 2);
          d5 = (double)(j + random.nextInt(3) + 2);
 
          for(l = 0; l <= this.numberOfBlocks; ++l) {
             d6 = d + (d1 - d) * (double)l / (double)this.numberOfBlocks;
             d7 = d4 + (d5 - d4) * (double)l / (double)this.numberOfBlocks;
             d8 = d2 + (d3 - d2) * (double)l / (double)this.numberOfBlocks;
             d9 = random.nextDouble() * (double)this.numberOfBlocks / 16.0D;
             d10 = (double)(MathHelper.sin((float)l * 3.141593F / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
             d11 = (double)(MathHelper.sin((float)l * 3.141593F / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
 
             for(i1 = (int)(d6 - d10 / 2.0D); i1 <= (int)(d6 + d10 / 2.0D); ++i1) {
                for(j1 = (int)(d7 - d11 / 2.0D); j1 <= (int)(d7 + d11 / 2.0D); ++j1) {
                   for(k1 = (int)(d8 - d10 / 2.0D); k1 <= (int)(d8 + d10 / 2.0D); ++k1) {
                      double d12 = ((double)i1 + 0.5D - d6) / (d10 / 2.0D);
                      double d13 = ((double)j1 + 0.5D - d7) / (d11 / 2.0D);
                      d12 = ((double)k1 + 0.5D - d8) / (d10 / 2.0D);
                      if (d12 * d12 + d13 * d13 + d12 * d12 < 1.0D && world.getBlock(i1, j1, k1) == Blocks.stone) {
                         world.setBlock(i1, j1, k1, this.minableBlock);
                      }
                   }
                }
             }
          }
 
          return true;
       }
    }
 }
 