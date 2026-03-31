package dev.tilera.cwg.beta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.tilera.cwg.api.generator.AbstractChunkManager;
import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.hooks.common.ICavegenHook;
import dev.tilera.cwg.api.hooks.common.IStructureGenHook;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class BetaChunkManager extends AbstractChunkManager implements IBetaChunkManager {

    private IGeneratorOptionProvider provider = null;
    private NoiseGeneratorOctaves2 field_4194_e;
	private NoiseGeneratorOctaves2 field_4193_f;
	private NoiseGeneratorOctaves2 field_4192_g;
	public double[] temperature;
	public double[] humidity;
	public double[] field_4196_c;
    private static BiomeGenBase[] biomeLookupTable = new BiomeGenBase[4096];
	private IBetaBiomeProvider biomeProvider;
	public BiomeGenBase[] field_4195_d;

    public BetaChunkManager(World world, IGeneratorOptionProvider provider) {
        super();
        this.provider = provider;
		biomeProvider = provider.getValue("cwg:beta_biomes", IHookProvider.class).getHook(BetaModule.BIOMES);
        long seed = world.getSeed();
        this.generateBiomeLookup();
        field_4194_e = new NoiseGeneratorOctaves2(new Random(seed * 9871L), 4);
        field_4193_f = new NoiseGeneratorOctaves2(new Random(seed * 39811L), 4);
        field_4192_g = new NoiseGeneratorOctaves2(new Random(seed * 543321L), 2);
    }

    @Override
    public IChunkProvider getGenerator(World world) {
        IStructureGenHook structureHook = provider.getValue("cwg:structuregen_hook", IHookProvider.class).getHook(HookTypes.STRUCTUREGEN);
		ICavegenHook cavegenHook = provider.getValue("cwg:cavegen_hook", IHookProvider.class).getHook(HookTypes.CAVEGEN);
        return new ChunkProviderGenerate(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), structureHook, cavegenHook, biomeProvider);
    }

    @Override
    public IGeneratorOptionProvider getOptionProvider() {
        return this.provider;
    }

    @Override
    public double[] getHumidity() {
        return this.humidity;
    }

    @Override
    public double[] getTemperature() {
        return this.temperature;
    }

    @Override
    public double getTemperature(int var1, int var2) {
        this.temperature = this.field_4194_e.func_4112_a(this.temperature, (double)var1, (double)var2, 1, 1, (double)0.025F, (double)0.025F, 0.5D);
		return this.temperature[0];
    }

    @Override
    public double[] getTemperatures(double[] var1, int var2, int var3, int var4, int var5) {
        if(var1 == null || var1.length < var4 * var5) {
			var1 = new double[var4 * var5];
		}

		var1 = this.field_4194_e.func_4112_a(var1, (double)var2, (double)var3, var4, var5, (double)0.025F, (double)0.025F, 0.25D);
		this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, (double)var2, (double)var3, var4, var5, 0.25D, 0.25D, 0.5882352941176471D);
		int var6 = 0;

		for(int var7 = 0; var7 < var4; ++var7) {
			for(int var8 = 0; var8 < var5; ++var8) {
				double var9 = this.field_4196_c[var6] * 1.1D + 0.5D;
				double var11 = 0.01D;
				double var13 = 1.0D - var11;
				double var15 = (var1[var6] * 0.15D + 0.7D) * var13 + var9 * var11;
				var15 = 1.0D - (1.0D - var15) * (1.0D - var15);
				if(var15 < 0.0D) {
					var15 = 0.0D;
				}

				if(var15 > 1.0D) {
					var15 = 1.0D;
				}

				var1[var6] = var15;
				++var6;
			}
		}

		return var1;
    }

    public void generateBiomeLookup() {
        for(int var0 = 0; var0 < 64; ++var0) {
			for(int var1 = 0; var1 < 64; ++var1) {
				biomeLookupTable[var0 + var1 * 64] = getBiome((float)var0 / 63.0F, (float)var1 / 63.0F);
			}
		}
    }

    public static BiomeGenBase getBiomeFromLookup(double var0, double var2) {
		int var4 = (int)(var0 * 63.0D);
		int var5 = (int)(var2 * 63.0D);
		return biomeLookupTable[var4 + var5 * 64];
	}

    public BiomeGenBase getBiome(float f, float f1) {
      f1 *= f;
      if (f < 0.1F) {
         return biomeProvider.getTundra();
      } else if (f1 < 0.2F) {
         if (f < 0.5F) {
            return biomeProvider.getTundra();
         } else {
            return f < 0.95F ? biomeProvider.getSavanna() : biomeProvider.getDesert();
         }
      } else if (f1 > 0.5F && f < 0.7F) {
         return biomeProvider.getSwampland();
      } else if (f < 0.5F) {
         return biomeProvider.getTaiga();
      } else if (f < 0.97F) {
         return f1 < 0.35F ? biomeProvider.getShrubland() : biomeProvider.getForest();
      } else if (f1 < 0.45F) {
         return biomeProvider.getPlains();
      } else {
         return f1 < 0.9F ? biomeProvider.getSeasonalForest() : biomeProvider.getRainforest();
      }
    }

	@Override
	public BiomeGenBase getBiomeGenAt(int var1, int var2) {
		return this.func_4069_a(var1, var2, 1, 1)[0];
	}

	public BiomeGenBase[] func_4069_a(int var1, int var2, int var3, int var4) {
		this.field_4195_d = this.loadBlockGeneratorData(this.field_4195_d, var1, var2, var3, var4);
		return this.field_4195_d;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] p_76933_1_, int p_76933_2_, int p_76933_3_, int p_76933_4_, int p_76933_5_)
    {
        return this.getBiomeGenAt(p_76933_1_, p_76933_2_, p_76933_3_, p_76933_4_, p_76933_5_, true);
    }

	@Override
	public List getBiomesToSpawnIn() {
		return new ArrayList<>();
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] var1, int var2, int var3, int var4, int var5, boolean varx) {
		if(var1 == null || var1.length < var4 * var5) {
			var1 = new BiomeGenBase[var4 * var5];
		}

		this.temperature = this.field_4194_e.func_4112_a(this.temperature, (double)var2, (double)var3, var4, var4, (double)0.025F, (double)0.025F, 0.25D);
		this.humidity = this.field_4193_f.func_4112_a(this.humidity, (double)var2, (double)var3, var4, var4, (double)0.05F, (double)0.05F, 1.0D / 3.0D);
		this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, (double)var2, (double)var3, var4, var4, 0.25D, 0.25D, 0.5882352941176471D);
		int var6 = 0;

		for(int var7 = 0; var7 < var4; ++var7) {
			for(int var8 = 0; var8 < var5; ++var8) {
				double var9 = this.field_4196_c[var6] * 1.1D + 0.5D;
				double var11 = 0.01D;
				double var13 = 1.0D - var11;
				double var15 = (this.temperature[var6] * 0.15D + 0.7D) * var13 + var9 * var11;
				var11 = 0.002D;
				var13 = 1.0D - var11;
				double var17 = (this.humidity[var6] * 0.15D + 0.5D) * var13 + var9 * var11;
				var15 = 1.0D - (1.0D - var15) * (1.0D - var15);
				if(var15 < 0.0D) {
					var15 = 0.0D;
				}

				if(var17 < 0.0D) {
					var17 = 0.0D;
				}

				if(var15 > 1.0D) {
					var15 = 1.0D;
				}

				if(var17 > 1.0D) {
					var17 = 1.0D;
				}

				this.temperature[var6] = var15;
				this.humidity[var6] = var17;
				var1[var6++] = getBiomeFromLookup(var15, var17);
			}
		}
		return var1;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
		return this.getBiomeGenAt(var1, var2, var3, var4, var5, false);
	}

	@Override
	public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List p_76940_4_) {
		return false;
	}

	@Override
	public ChunkPosition findBiomePosition(int p_150795_1_, int p_150795_2_, int p_150795_3_, List p_150795_4_, Random p_150795_5_) {
		return null;
	}	

}
