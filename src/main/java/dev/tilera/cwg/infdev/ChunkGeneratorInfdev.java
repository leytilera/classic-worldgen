package dev.tilera.cwg.infdev;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Post;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Pre;

public class ChunkGeneratorInfdev implements IChunkProvider {
   private Random field_913_j;
   private NoiseOctavesInfdev field_912_k;
   private NoiseOctavesInfdev field_911_l;
   private NoiseOctavesInfdev field_910_m;
   private NoiseOctavesInfdev field_909_n;
   private NoiseOctavesInfdev field_908_o;
   public NoiseOctavesInfdev field_922_a;
   public NoiseOctavesInfdev field_921_b;
   public NoiseOctavesInfdev field_920_c;
   private World field_907_p;
   private final boolean mapFeaturesEnabled;
   private double[] field_906_q;
   private double[] field_905_r = new double[256];
   private double[] field_904_s = new double[256];
   private double[] field_903_t = new double[256];
   private MapGenBase caveGenerator;
   private MapGenStronghold strongholdGenerator = new MapGenStronghold();
   private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
   double[] field_919_d;
   double[] field_918_e;
   double[] field_917_f;
   double[] field_916_g;
   double[] field_915_h;
   int[][] field_914_i = new int[32][32];

   public ChunkGeneratorInfdev(World world, long l, boolean par4, MapGenBase caveGenerator) {
      this.field_907_p = world;
      this.mapFeaturesEnabled = par4;
      this.field_913_j = new Random(l);
      this.field_912_k = new NoiseOctavesInfdev(this.field_913_j, 16);
      this.field_911_l = new NoiseOctavesInfdev(this.field_913_j, 16);
      this.field_910_m = new NoiseOctavesInfdev(this.field_913_j, 8);
      this.field_909_n = new NoiseOctavesInfdev(this.field_913_j, 4);
      this.field_908_o = new NoiseOctavesInfdev(this.field_913_j, 4);
      this.field_922_a = new NoiseOctavesInfdev(this.field_913_j, 10);
      this.field_921_b = new NoiseOctavesInfdev(this.field_913_j, 16);
      this.field_920_c = new NoiseOctavesInfdev(this.field_913_j, 8);
      this.caveGenerator = caveGenerator;
   }

   public void generateTerrain(int i, int j, Block[] blocks) {
      byte byte0 = 4;
      byte byte1 = 64;
      int k = byte0 + 1;
      byte byte2 = 17;
      int l = byte0 + 1;
      this.field_906_q = this.initializeNoiseField(this.field_906_q, i * byte0, 0, j * byte0, k, byte2, l);

      for(int i1 = 0; i1 < byte0; ++i1) {
         for(int j1 = 0; j1 < byte0; ++j1) {
            for(int k1 = 0; k1 < 16; ++k1) {
               double d = 0.125D;
               double d1 = this.field_906_q[((i1 + 0) * l + j1 + 0) * byte2 + k1 + 0];
               double d2 = this.field_906_q[((i1 + 0) * l + j1 + 1) * byte2 + k1 + 0];
               double d3 = this.field_906_q[((i1 + 1) * l + j1 + 0) * byte2 + k1 + 0];
               double d4 = this.field_906_q[((i1 + 1) * l + j1 + 1) * byte2 + k1 + 0];
               double d5 = (this.field_906_q[((i1 + 0) * l + j1 + 0) * byte2 + k1 + 1] - d1) * d;
               double d6 = (this.field_906_q[((i1 + 0) * l + j1 + 1) * byte2 + k1 + 1] - d2) * d;
               double d7 = (this.field_906_q[((i1 + 1) * l + j1 + 0) * byte2 + k1 + 1] - d3) * d;
               double d8 = (this.field_906_q[((i1 + 1) * l + j1 + 1) * byte2 + k1 + 1] - d4) * d;

               for(int l1 = 0; l1 < 8; ++l1) {
                  double d9 = 0.25D;
                  double d10 = d1;
                  double d11 = d2;
                  double d12 = (d3 - d1) * d9;
                  double d13 = (d4 - d2) * d9;

                  for(int i2 = 0; i2 < 4; ++i2) {
                     int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                     char c = 128;
                     double d14 = 0.25D;
                     double d15 = d10;
                     double d16 = (d11 - d10) * d14;

                     for(int k2 = 0; k2 < 4; ++k2) {
                        Block l2 = Blocks.air;
                        if (k1 * 8 + l1 < byte1) {
                           l2 = Blocks.water;
                        }

                        if (d15 > 0.0D) {
                           l2 = Blocks.stone;
                        }

                        blocks[j2] = l2;
                        j2 += c;
                        d15 += d16;
                     }

                     d10 += d12;
                     d11 += d13;
                  }

                  d1 += d5;
                  d2 += d6;
                  d3 += d7;
                  d4 += d8;
               }
            }
         }
      }

   }

   public void replaceBlocksForBiome(int i, int j, Block[] blocks) {
      byte byte0 = 64;
      double d = 0.03125D;
      this.field_905_r = this.field_909_n.func_807_a(this.field_905_r, (double)(i * 16), (double)(j * 16), 0.0D, 16, 16, 1, d, d, 1.0D);
      this.field_904_s = this.field_909_n.func_807_a(this.field_904_s, (double)(j * 16), 109.0134D, (double)(i * 16), 16, 1, 16, d, 1.0D, d);
      this.field_903_t = this.field_908_o.func_807_a(this.field_903_t, (double)(i * 16), (double)(j * 16), 0.0D, 16, 16, 1, d * 2.0D, d * 2.0D, d * 2.0D);

      for(int k = 0; k < 16; ++k) {
         for(int l = 0; l < 16; ++l) {
            boolean flag = this.field_905_r[k + l * 16] + this.field_913_j.nextDouble() * 0.2D > 0.0D;
            boolean flag1 = this.field_904_s[k + l * 16] + this.field_913_j.nextDouble() * 0.2D > 3.0D;
            int i1 = (int)(this.field_903_t[k + l * 16] / 3.0D + 3.0D + this.field_913_j.nextDouble() * 0.25D);
            int j1 = -1;
            Block byte1 = Blocks.grass;
            Block byte2 = Blocks.dirt;

            for(int k1 = 127; k1 >= 0; --k1) {
               int l1 = (k * 16 + l) * 128 + k1;
               if (k1 <= 0 + this.field_913_j.nextInt(6) - 1) {
                  blocks[l1] = Blocks.bedrock;
               } else {
                  Block byte3 = blocks[l1];
                  if (byte3 == Blocks.air) {
                     j1 = -1;
                  } else if (byte3 == Blocks.stone) {
                     if (j1 == -1) {
                        if (i1 <= 0) {
                           byte1 = Blocks.air;
                           byte2 = Blocks.stone;
                        } else if (k1 >= byte0 - 4 && k1 <= byte0 + 1) {
                           byte1 = Blocks.grass;
                           byte2 = Blocks.dirt;
                           if (flag1) {
                              byte1 = Blocks.air;
                           }

                           if (flag1) {
                              byte2 = Blocks.gravel;
                           }

                           if (flag) {
                              byte1 = Blocks.sand;
                           }

                           if (flag) {
                              byte2 = Blocks.sand;
                           }
                        }

                        if (k1 < byte0 && byte1 == Blocks.air) {
                           byte1 = Blocks.flowing_water;
                        }

                        j1 = i1;
                        if (k1 >= byte0 - 1) {
                           blocks[l1] = (Block)byte1;
                        } else {
                           blocks[l1] = (Block)byte2;
                        }
                     } else if (j1 > 0) {
                        --j1;
                        blocks[l1] = (Block)byte2;
                     }
                  }
               }
            }
         }
      }

   }

   public Chunk loadChunk(int par1, int par2) {
      return this.provideChunk(par1, par2);
   }

   public Chunk provideChunk(int i, int j) {
      this.field_913_j.setSeed((long)i * 341873128712L + (long)j * 132897987541L);
      Block[] blocks1 = new Block[32768];
      this.generateTerrain(i, j, blocks1);
      this.replaceBlocksForBiome(i, j, blocks1);
      Block[] blocks2 = new Block[65536];
      //System.arraycopy(blocks1, 0, blocks2, 0, blocks1.length);
      ChunkConverter.convert(blocks1, blocks2);
      this.caveGenerator.func_151539_a(this, this.field_907_p, i, j, blocks2);
      if (this.mapFeaturesEnabled) {
         this.strongholdGenerator.func_151539_a(this, this.field_907_p, i, j, blocks2);
         this.mineshaftGenerator.func_151539_a(this, this.field_907_p, i, j, blocks2);
      }
      Chunk chunk = new Chunk(this.field_907_p, blocks2, new byte[65536], i, j);
      chunk.generateSkylightMap();
      return chunk;
   }

   private double[] initializeNoiseField(double[] ad, int i, int j, int k, int l, int i1, int j1) {
      if (ad == null) {
         ad = new double[l * i1 * j1];
      }

      double d = 684.412D;
      double d1 = 684.412D;
      this.field_916_g = this.field_922_a.func_807_a(this.field_916_g, (double)i, (double)j, (double)k, l, 1, j1, 1.0D, 0.0D, 1.0D);
      this.field_915_h = this.field_921_b.func_807_a(this.field_915_h, (double)i, (double)j, (double)k, l, 1, j1, 100.0D, 0.0D, 100.0D);
      this.field_919_d = this.field_910_m.func_807_a(this.field_919_d, (double)i, (double)j, (double)k, l, i1, j1, d / 80.0D, d1 / 160.0D, d / 80.0D);
      this.field_918_e = this.field_912_k.func_807_a(this.field_918_e, (double)i, (double)j, (double)k, l, i1, j1, d, d1, d);
      this.field_917_f = this.field_911_l.func_807_a(this.field_917_f, (double)i, (double)j, (double)k, l, i1, j1, d, d1, d);
      int k1 = 0;
      int l1 = 0;

      for(int i2 = 0; i2 < l; ++i2) {
         for(int j2 = 0; j2 < j1; ++j2) {
            double d2 = (this.field_916_g[l1] + 256.0D) / 512.0D;
            if (d2 > 1.0D) {
               d2 = 1.0D;
            }

            double d3 = 0.0D;
            double d4 = this.field_915_h[l1] / 8000.0D;
            if (d4 < 0.0D) {
               d4 = -d4;
            }

            d4 = d4 * 3.0D - 3.0D;
            if (d4 < 0.0D) {
               d4 /= 2.0D;
               if (d4 < -1.0D) {
                  d4 = -1.0D;
               }

               d4 /= 1.4D;
               d4 /= 2.0D;
               d2 = 0.0D;
            } else {
               if (d4 > 1.0D) {
                  d4 = 1.0D;
               }

               d4 /= 6.0D;
            }

            d2 += 0.5D;
            d4 = d4 * (double)i1 / 16.0D;
            double d5 = (double)i1 / 2.0D + d4 * 4.0D;
            ++l1;

            for(int k2 = 0; k2 < i1; ++k2) {
               double d6 = 0.0D;
               double d7 = ((double)k2 - d5) * 12.0D / d2;
               if (d7 < 0.0D) {
                  d7 *= 4.0D;
               }

               double d8 = this.field_918_e[k1] / 512.0D;
               double d9 = this.field_917_f[k1] / 512.0D;
               double d10 = (this.field_919_d[k1] / 10.0D + 1.0D) / 2.0D;
               if (d10 < 0.0D) {
                  d6 = d8;
               } else if (d10 > 1.0D) {
                  d6 = d9;
               } else {
                  d6 = d8 + (d9 - d8) * d10;
               }

               d6 -= d7;
               double d12;
               if (k2 > i1 - 4) {
                  d12 = (double)((float)(k2 - (i1 - 4)) / 3.0F);
                  d6 = d6 * (1.0D - d12) + -10.0D * d12;
               }

               if ((double)k2 < d3) {
                  d12 = (d3 - (double)k2) / 4.0D;
                  if (d12 < 0.0D) {
                     d12 = 0.0D;
                  }

                  if (d12 > 1.0D) {
                     d12 = 1.0D;
                  }

                  d6 = d6 * (1.0D - d12) + -10.0D * d12;
               }

               ad[k1] = d6;
               ++k1;
            }
         }
      }

      return ad;
   }

   public boolean chunkExists(int par1, int par2) {
      return true;
   }

   public void populate(IChunkProvider ichunkprovider, int i, int j) {
      BlockFalling.fallInstantly = true;
      int k = i * 16;
      int l = j * 16;
      BiomeGenBase biomegenbase = this.field_907_p.getWorldChunkManager().getBiomeGenAt(k + 16, l + 16);
      this.field_913_j.setSeed(this.field_907_p.getSeed());
      long l1 = this.field_913_j.nextLong() / 2L * 2L + 1L;
      long l2 = this.field_913_j.nextLong() / 2L * 2L + 1L;
      this.field_913_j.setSeed((long)i * l1 + (long)j * l2 ^ this.field_907_p.getSeed());
      double d = 0.25D;
      MinecraftForge.EVENT_BUS.post(new Pre(ichunkprovider, this.field_907_p, this.field_913_j, i, j, false));
      if (this.mapFeaturesEnabled) {
         this.strongholdGenerator.generateStructuresInChunk(this.field_907_p, this.field_913_j, i, j);
         this.mineshaftGenerator.generateStructuresInChunk(this.field_907_p, this.field_913_j, i, j);
      }

      int l3;
      int i6;
      int sn1;
      int sn2;

      for(l3 = 0; l3 < 20; ++l3) {
         i6 = k + this.field_913_j.nextInt(16);
         sn1 = this.field_913_j.nextInt(128);
         sn2 = l + this.field_913_j.nextInt(16);
         (new GenMinable(Blocks.dirt, 32, 0)).generate(this.field_907_p, this.field_913_j, i6, sn1, sn2);
      }

      for(l3 = 0; l3 < 10; ++l3) {
         i6 = k + this.field_913_j.nextInt(16);
         sn1 = this.field_913_j.nextInt(128);
         sn2 = l + this.field_913_j.nextInt(16);
         (new GenMinable(Blocks.gravel, 32, 0)).generate(this.field_907_p, this.field_913_j, i6, sn1, sn2);
      }

      for(l3 = 0; l3 < 20; ++l3) {
         i6 = k + this.field_913_j.nextInt(16);
         sn1 = this.field_913_j.nextInt(128);
         sn2 = l + this.field_913_j.nextInt(16);
         (new GenMinable(Blocks.coal_ore, 16, 0)).generate(this.field_907_p, this.field_913_j, i6, sn1, sn2);
      }

      for(l3 = 0; l3 < 20; ++l3) {
         i6 = k + this.field_913_j.nextInt(16);
         sn1 = this.field_913_j.nextInt(64);
         sn2 = l + this.field_913_j.nextInt(16);
         (new GenMinable(Blocks.iron_ore, 8, 0)).generate(this.field_907_p, this.field_913_j, i6, sn1, sn2);
      }

      for(l3 = 0; l3 < 2; ++l3) {
         i6 = k + this.field_913_j.nextInt(16);
         sn1 = this.field_913_j.nextInt(32);
         sn2 = l + this.field_913_j.nextInt(16);
         (new GenMinable(Blocks.gold_ore, 8, 0)).generate(this.field_907_p, this.field_913_j, i6, sn1, sn2);
      }

      for(l3 = 0; l3 < 8; ++l3) {
         i6 = k + this.field_913_j.nextInt(16);
         sn1 = this.field_913_j.nextInt(16);
         sn2 = l + this.field_913_j.nextInt(16);
         (new GenMinable(Blocks.redstone_ore, 7, 0)).generate(this.field_907_p, this.field_913_j, i6, sn1, sn2);
      }

      for(l3 = 0; l3 < 1; ++l3) {
         i6 = k + this.field_913_j.nextInt(16);
         sn1 = this.field_913_j.nextInt(16);
         sn2 = l + this.field_913_j.nextInt(16);
         (new GenMinable(Blocks.diamond_ore, 7, 0)).generate(this.field_907_p, this.field_913_j, i6, sn1, sn2);
      }

      d = 0.5D;
      l3 = (int)((this.field_920_c.func_806_a((double)k * d, (double)l * d) / 8.0D + this.field_913_j.nextDouble() * 4.0D + 4.0D) / 3.0D);
      if (l3 < 0) {
         l3 = 0;
      }

      if (this.field_913_j.nextInt(10) == 0) {
         ++l3;
      }

      Object obj = new WorldGenTrees(false, 5, 0, 0, false);

      int sn3;
      for(sn1 = 0; sn1 < l3; ++sn1) {
         sn2 = k + this.field_913_j.nextInt(16) + 8;
         sn3 = l + this.field_913_j.nextInt(16) + 8;
         ((WorldGenerator)((WorldGenerator)obj)).setScale(1.0D, 1.0D, 1.0D);
         ((WorldGenerator)((WorldGenerator)obj)).generate(this.field_907_p, this.field_913_j, sn2, this.field_907_p.getHeightValue(sn2, sn3), sn3);
      }

      int j19;
      for(sn1 = 0; sn1 < 2; ++sn1) {
         sn2 = k + this.field_913_j.nextInt(16) + 8;
         sn3 = this.field_913_j.nextInt(128);
         j19 = l + this.field_913_j.nextInt(16) + 8;
         (new WorldGenFlowers(Blocks.yellow_flower)).generate(this.field_907_p, this.field_913_j, sn2, sn3, j19);
      }

      if (this.field_913_j.nextInt(2) == 0) {
         sn1 = k + this.field_913_j.nextInt(16) + 8;
         sn2 = this.field_913_j.nextInt(128);
         sn3 = l + this.field_913_j.nextInt(16) + 8;
         (new WorldGenFlowers(Blocks.red_flower)).generate(this.field_907_p, this.field_913_j, sn1, sn2, sn3);
      }

      {
         if (this.field_913_j.nextInt(6) == 0) {
            sn1 = k + this.field_913_j.nextInt(16) + 8;
            sn2 = this.field_913_j.nextInt(64);
            sn3 = l + this.field_913_j.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.brown_mushroom)).generate(this.field_907_p, this.field_913_j, sn1, sn2, sn3);
         }

         if (this.field_913_j.nextInt(12) == 0) {
            sn1 = k + this.field_913_j.nextInt(16) + 8;
            sn2 = this.field_913_j.nextInt(64);
            sn3 = l + this.field_913_j.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.red_mushroom)).generate(this.field_907_p, this.field_913_j, sn1, sn2, sn3);
         }
      }

      for(sn1 = 0; sn1 < 50; ++sn1) {
         sn2 = k + this.field_913_j.nextInt(16) + 8;
         sn3 = this.field_913_j.nextInt(this.field_913_j.nextInt(120) + 8);
         j19 = l + this.field_913_j.nextInt(16) + 8;
         (new WorldGenLiquids(Blocks.flowing_water)).generate(this.field_907_p, this.field_913_j, sn2, sn3, j19);
      }

      for(sn1 = 0; sn1 < 20; ++sn1) {
         sn2 = k + this.field_913_j.nextInt(16) + 8;
         sn3 = this.field_913_j.nextInt(this.field_913_j.nextInt(this.field_913_j.nextInt(112) + 8) + 8);
         j19 = l + this.field_913_j.nextInt(16) + 8;
         (new WorldGenLiquids(Blocks.flowing_lava)).generate(this.field_907_p, this.field_913_j, sn2, sn3, j19);
      }

      SpawnerAnimals.performWorldGenSpawning(this.field_907_p, biomegenbase, k + 8, l + 8, 16, 16, this.field_913_j);
      k += 8;
      l += 8;

      for(sn1 = 0; sn1 < 16; ++sn1) {
         for(sn2 = 0; sn2 < 16; ++sn2) {
            sn3 = this.field_907_p.getPrecipitationHeight(k + sn1, l + sn2);
            if (this.field_907_p.isBlockFreezable(sn1 + k, sn3 - 1, sn2 + l)) {
               this.field_907_p.setBlock(sn1 + k, sn3 - 1, sn2 + l, Blocks.ice, 0, 2);
            }

            Block b = this.field_907_p.getBlock(sn1 + k, sn3 - 1, sn2 + l);
            if (this.field_907_p.func_147478_e(sn1 + k, sn3, sn2 + l, false) && b != Blocks.ice && b != Blocks.water && sn3 > 63) {
               this.field_907_p.setBlock(sn1 + k, sn3, sn2 + l, Blocks.snow_layer, 0, 2);
            }
         }
      }

      MinecraftForge.EVENT_BUS.post(new Post(ichunkprovider, this.field_907_p, this.field_913_j, i, j, false));
      BlockFalling.fallInstantly = false;
   }

   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
      return true;
   }

   public boolean unloadQueuedChunks() {
      return false;
   }

   public boolean unload100OldestChunks() {
      return false;
   }

   public boolean canSave() {
      return true;
   }

   public String makeString() {
      return "RandomLevelSource";
   }

   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
      BiomeGenBase var5 = this.field_907_p.getBiomeGenForCoords(par2, par4);
      return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType);
   }

   public ChunkPosition func_147416_a(World par1World, String par2Str, int par3, int par4, int par5) {
      return "Stronghold".equals(par2Str) && this.strongholdGenerator != null ? this.strongholdGenerator.func_151545_a(par1World, par3, par4, par5) : null;
   }

   public int getLoadedChunkCount() {
      return 0;
   }

   public void saveExtraData() {
   }

   public void recreateStructures(int par1, int par2) {
      if (this.mapFeaturesEnabled) {
         this.strongholdGenerator.func_151539_a(this, this.field_907_p, par1, par2, (Block[])null);
         this.mineshaftGenerator.func_151539_a(this, this.field_907_p, par1, par2, (Block[])null);
      }

   }
}
