package dev.tilera.cwg.infdev;

import java.util.Random;
import net.minecraft.world.gen.NoiseGenerator;

public class NoisePerlinInfdev extends NoiseGenerator {
   private int[] field_1189_d;
   public double field_1188_a;
   public double field_1187_b;
   public double field_1190_c;

   public NoisePerlinInfdev() {
      this(new Random());
   }

   public NoisePerlinInfdev(Random random) {
      this.field_1189_d = new int[512];
      this.field_1188_a = random.nextDouble() * 256.0D;
      this.field_1187_b = random.nextDouble() * 256.0D;
      this.field_1190_c = random.nextDouble() * 256.0D;

      int j;
      for(j = 0; j < 256; this.field_1189_d[j] = j++) {
      }

      for(j = 0; j < 256; ++j) {
         int k = random.nextInt(256 - j) + j;
         int l = this.field_1189_d[j];
         this.field_1189_d[j] = this.field_1189_d[k];
         this.field_1189_d[k] = l;
         this.field_1189_d[j + 256] = this.field_1189_d[j];
      }

   }

   public double func_802_a(double d, double d1, double d2) {
      double d3 = d + this.field_1188_a;
      double d4 = d1 + this.field_1187_b;
      double d5 = d2 + this.field_1190_c;
      int i = (int)d3;
      int j = (int)d4;
      int k = (int)d5;
      if (d3 < (double)i) {
         --i;
      }

      if (d4 < (double)j) {
         --j;
      }

      if (d5 < (double)k) {
         --k;
      }

      int l = i & 255;
      int i1 = j & 255;
      int j1 = k & 255;
      d3 -= (double)i;
      d4 -= (double)j;
      d5 -= (double)k;
      double d6 = d3 * d3 * d3 * (d3 * (d3 * 6.0D - 15.0D) + 10.0D);
      double d7 = d4 * d4 * d4 * (d4 * (d4 * 6.0D - 15.0D) + 10.0D);
      double d8 = d5 * d5 * d5 * (d5 * (d5 * 6.0D - 15.0D) + 10.0D);
      int k1 = this.field_1189_d[l] + i1;
      int l1 = this.field_1189_d[k1] + j1;
      int i2 = this.field_1189_d[k1 + 1] + j1;
      int j2 = this.field_1189_d[l + 1] + i1;
      int k2 = this.field_1189_d[j2] + j1;
      int l2 = this.field_1189_d[j2 + 1] + j1;
      return this.func_804_b(d8, this.func_804_b(d7, this.func_804_b(d6, this.func_803_a(this.field_1189_d[l1], d3, d4, d5), this.func_803_a(this.field_1189_d[k2], d3 - 1.0D, d4, d5)), this.func_804_b(d6, this.func_803_a(this.field_1189_d[i2], d3, d4 - 1.0D, d5), this.func_803_a(this.field_1189_d[l2], d3 - 1.0D, d4 - 1.0D, d5))), this.func_804_b(d7, this.func_804_b(d6, this.func_803_a(this.field_1189_d[l1 + 1], d3, d4, d5 - 1.0D), this.func_803_a(this.field_1189_d[k2 + 1], d3 - 1.0D, d4, d5 - 1.0D)), this.func_804_b(d6, this.func_803_a(this.field_1189_d[i2 + 1], d3, d4 - 1.0D, d5 - 1.0D), this.func_803_a(this.field_1189_d[l2 + 1], d3 - 1.0D, d4 - 1.0D, d5 - 1.0D))));
   }

   public double func_804_b(double d, double d1, double d2) {
      return d1 + d * (d2 - d1);
   }

   public double func_803_a(int i, double d, double d1, double d2) {
      int j = i & 15;
      double d3 = j >= 8 ? d1 : d;
      double d4 = j >= 4 ? (j != 12 && j != 14 ? d2 : d) : d1;
      return ((j & 1) != 0 ? -d3 : d3) + ((j & 2) != 0 ? -d4 : d4);
   }

   public double func_801_a(double d, double d1) {
      return this.func_802_a(d, d1, 0.0D);
   }

   public void func_805_a(double[] ad, double d, double d1, double d2, int i, int j, int k, double d3, double d4, double d5, double d6) {
      int l = 0;
      double d7 = 1.0D / d6;
      int i1 = -1;
      double d8 = 0.0D;
      double d9 = 0.0D;
      double d10 = 0.0D;
      double d11 = 0.0D;

      for(int l2 = 0; l2 < i; ++l2) {
         double d12 = (d + (double)l2) * d3 + this.field_1188_a;
         int i3 = (int)d12;
         if (d12 < (double)i3) {
            --i3;
         }

         int j3 = i3 & 255;
         d12 -= (double)i3;
         double d13 = d12 * d12 * d12 * (d12 * (d12 * 6.0D - 15.0D) + 10.0D);

         for(int k3 = 0; k3 < k; ++k3) {
            double d14 = (d2 + (double)k3) * d5 + this.field_1190_c;
            int l3 = (int)d14;
            if (d14 < (double)l3) {
               --l3;
            }

            int i4 = l3 & 255;
            d14 -= (double)l3;
            double d15 = d14 * d14 * d14 * (d14 * (d14 * 6.0D - 15.0D) + 10.0D);

            for(int j4 = 0; j4 < j; ++j4) {
               double d16 = (d1 + (double)j4) * d4 + this.field_1187_b;
               int k4 = (int)d16;
               if (d16 < (double)k4) {
                  --k4;
               }

               int l4 = k4 & 255;
               d16 -= (double)k4;
               double d17 = d16 * d16 * d16 * (d16 * (d16 * 6.0D - 15.0D) + 10.0D);
               if (j4 == 0 || l4 != i1) {
                  i1 = l4;
                  int j1 = this.field_1189_d[j3] + l4;
                  int k1 = this.field_1189_d[j1] + i4;
                  int l1 = this.field_1189_d[j1 + 1] + i4;
                  int i2 = this.field_1189_d[j3 + 1] + l4;
                  int j2 = this.field_1189_d[i2] + i4;
                  int k2 = this.field_1189_d[i2 + 1] + i4;
                  d8 = this.func_804_b(d13, this.func_803_a(this.field_1189_d[k1], d12, d16, d14), this.func_803_a(this.field_1189_d[j2], d12 - 1.0D, d16, d14));
                  d9 = this.func_804_b(d13, this.func_803_a(this.field_1189_d[l1], d12, d16 - 1.0D, d14), this.func_803_a(this.field_1189_d[k2], d12 - 1.0D, d16 - 1.0D, d14));
                  d10 = this.func_804_b(d13, this.func_803_a(this.field_1189_d[k1 + 1], d12, d16, d14 - 1.0D), this.func_803_a(this.field_1189_d[j2 + 1], d12 - 1.0D, d16, d14 - 1.0D));
                  d11 = this.func_804_b(d13, this.func_803_a(this.field_1189_d[l1 + 1], d12, d16 - 1.0D, d14 - 1.0D), this.func_803_a(this.field_1189_d[k2 + 1], d12 - 1.0D, d16 - 1.0D, d14 - 1.0D));
               }

               double d18 = this.func_804_b(d17, d8, d9);
               double d19 = this.func_804_b(d17, d10, d11);
               double d20 = this.func_804_b(d15, d18, d19);
               int var10001 = l++;
               ad[var10001] += d20 * d7;
            }
         }
      }

   }
}
