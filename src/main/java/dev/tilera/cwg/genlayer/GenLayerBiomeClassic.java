package dev.tilera.cwg.genlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import dev.tilera.cwg.Config;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class GenLayerBiomeClassic extends GenLayer {

    private BiomeEntry[] allowedBiomes;
    private boolean hasBiomeWeights;

    public GenLayerBiomeClassic(long arg0, GenLayer parent, WorldType type) {
        super(arg0);
        super.parent = parent;
        hasBiomeWeights = false;
        BiomeGenBase[] vanillaBiomes = new BiomeGenBase[]{BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.coldTaiga, BiomeGenBase.jungle};
        Set<BiomeGenBase> addedBiomes = new HashSet<>();
        ArrayList<BiomeEntry> biomeEntries = new ArrayList<>();

        for (BiomeGenBase b : vanillaBiomes) {
           if (Config.disableJungle && b == BiomeGenBase.jungle) continue;
           addedBiomes.add(b);
           biomeEntries.add(new BiomeEntry(b, 10));
        }

        if (Config.addNewVanillaBiomes) {
           biomeEntries.add(new BiomeEntry(BiomeGenBase.birchForest, 10));
           addedBiomes.add(BiomeGenBase.birchForest);
           biomeEntries.add(new BiomeEntry(BiomeGenBase.taiga, 10));
           addedBiomes.add(BiomeGenBase.taiga);
           biomeEntries.add(new BiomeEntry(BiomeGenBase.roofedForest, 10));
           addedBiomes.add(BiomeGenBase.roofedForest);
           biomeEntries.add(new BiomeEntry(BiomeGenBase.mesa, 10));
           addedBiomes.add(BiomeGenBase.mesa);
           biomeEntries.add(new BiomeEntry(BiomeGenBase.mesaPlateau, 10));
           addedBiomes.add(BiomeGenBase.mesaPlateau);
           biomeEntries.add(new BiomeEntry(BiomeGenBase.savanna, 10));
           addedBiomes.add(BiomeGenBase.savanna);
           biomeEntries.add(new BiomeEntry(BiomeGenBase.savannaPlateau, 10));
           addedBiomes.add(BiomeGenBase.savannaPlateau);
           biomeEntries.add(new BiomeEntry(BiomeGenBase.megaTaiga, 10));
           addedBiomes.add(BiomeGenBase.megaTaiga);
           biomeEntries.add(new BiomeEntry(BiomeGenBase.icePlains, 10));
           addedBiomes.add(BiomeGenBase.icePlains);
        }

        if (!Config.disableModdedBiomes) {
            for (BiomeType t : BiomeType.values()) {
               ImmutableList<BiomeEntry> biomesToAdd = BiomeManager.getBiomes(t);
               for (BiomeEntry biome : biomesToAdd) {
                  if (biome.biome.biomeID < 40 || addedBiomes.contains(biome.biome)) continue;
                  addedBiomes.add(biome.biome);
                  biomeEntries.add(biome);
                  if (biome.itemWeight != 10) hasBiomeWeights = true;
               }
            }
        }
        allowedBiomes = biomeEntries.toArray(new BiomeEntry[biomeEntries.size()]);
        
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
      int[] aint = this.parent.getInts(par1, par2, par3, par4);
      int[] aint1 = IntCache.getIntCache(par3 * par4);

      for(int i1 = 0; i1 < par4; ++i1) {
         for(int j1 = 0; j1 < par3; ++j1) {
            this.initChunkSeed((long)(j1 + par1), (long)(i1 + par2));
            int k1 = aint[j1 + i1 * par3];
            if (k1 == 0) {
               aint1[j1 + i1 * par3] = 0;
            } else if (k1 == BiomeGenBase.mushroomIsland.biomeID) {
               aint1[j1 + i1 * par3] = k1;
            } else if (k1 == 1) {
               aint1[j1 + i1 * par3] = getBiomeID();
            } else {
               int l1 = getBiomeID();
               if (l1 == BiomeGenBase.taiga.biomeID) {
                  aint1[j1 + i1 * par3] = l1;
               } else {
                  aint1[j1 + i1 * par3] = BiomeGenBase.icePlains.biomeID;
               }
            }
         }
      }

      return aint1;
    }
    
    protected int getBiomeID() {
       if (hasBiomeWeights) {
          return getWeightedBiomeEntry().biome.biomeID;
       } else {
          return this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biome.biomeID;
       }
    }

    protected BiomeEntry getWeightedBiomeEntry() {
        int totalWeight = WeightedRandom.getTotalWeight(allowedBiomes);
        int weight = this.nextInt(totalWeight);
        return (BiomeEntry)WeightedRandom.getItem(allowedBiomes, weight);
     }

}
