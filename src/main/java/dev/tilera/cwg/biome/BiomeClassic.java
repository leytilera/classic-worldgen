package dev.tilera.cwg.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeClassic extends BiomeGenBase {
   public BiomeClassic(int id) {
      super(id);
   }

   @SideOnly(Side.CLIENT)
   public int getBiomeGrassColor(int a, int b, int c) {
      return 11272039;
   }

   @SideOnly(Side.CLIENT)
   public int getBiomeFoliageColor(int a, int b, int c) {
      return 5242667;
   }
}
