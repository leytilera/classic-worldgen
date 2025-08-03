package dev.tilera.cwg.classic;

import dev.tilera.cwg.api.hooks.IHookProvider;
import dev.tilera.cwg.api.hooks.common.HookTypes;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import dev.tilera.cwg.hooks.ICavegenHook;
import dev.tilera.cwg.hooks.IStructureGenHook;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;

public class ChunkProviderClassic extends ChunkProviderGenerate {

    private IGeneratorOptionProvider options;

    public ChunkProviderClassic(World world, long seed, boolean features, IGeneratorOptionProvider options) {
        super(world, seed, features);
        this.options = options;
        ICavegenHook hook1 = options.getValue("cwg:cavegen_hook", IHookProvider.class).getHook(HookTypes.CAVEGEN);
        hook1.setCavegen(this);
        IStructureGenHook hook2 = options.getValue("cwg:structuregen_hook", IHookProvider.class).getHook(HookTypes.STRUCTUREGEN);
        hook2.setStructureGen(this);;
    }

    @Override
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {
        BlockFalling.fallInstantly = true;
        int k = p_73153_2_ * 16;
        int l = p_73153_3_ * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)p_73153_2_ * i1 + (long)p_73153_3_ * j1 ^ this.worldObj.getSeed());
        boolean flag = false;

        if (options.getBoolean("cwg:generator.classic:enableModdedWorldgen")) {
            MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag));
        }

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
            flag = this.villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
            this.strongholdGenerator.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
            this.scatteredFeatureGenerator.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
        }

        int k1;
        int l1;
        int i2;
        boolean canGenerteLake = (biomegenbase != BiomeGenBase.desert && biomegenbase != BiomeGenBase.desertHills) || this.hasDesertLakes();
        if (canGenerteLake && !flag && this.rand.nextInt(4) == 0
            && TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, EventType.LAKE))
        {
            k1 = k + this.rand.nextInt(16) + 8;
            l1 = this.rand.nextInt(256);
            i2 = l + this.rand.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.water)).generate(this.worldObj, this.rand, k1, l1, i2);
        }

        if (TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, EventType.LAVA) && !flag && this.rand.nextInt(8) == 0)
        {
            k1 = k + this.rand.nextInt(16) + 8;
            l1 = this.rand.nextInt(this.rand.nextInt(248) + 8);
            i2 = l + this.rand.nextInt(16) + 8;

            if (l1 < 63 || this.rand.nextInt(10) == 0)
            {
                (new WorldGenLakes(Blocks.lava)).generate(this.worldObj, this.rand, k1, l1, i2);
            }
        }

        boolean doGen = !options.getBoolean("cwg:generator.classic:enableModdedWorldgen") || TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, EventType.DUNGEON);
        for (k1 = 0; doGen && k1 < 8; ++k1)
        {
            l1 = k + this.rand.nextInt(16) + 8;
            i2 = this.rand.nextInt(256);
            int j2 = l + this.rand.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(this.worldObj, this.rand, l1, i2, j2);
        }

        biomegenbase.decorate(this.worldObj, this.rand, k, l);
        if (TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, EventType.ANIMALS))
        {
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k + 8, l + 8, 16, 16, this.rand);
        }
        k += 8;
        l += 8;

        doGen = TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, EventType.ICE);
        for (k1 = 0; doGen && k1 < 16; ++k1)
        {
            for (l1 = 0; l1 < 16; ++l1)
            {
                i2 = this.worldObj.getPrecipitationHeight(k + k1, l + l1);

                if (this.worldObj.isBlockFreezable(k1 + k, i2 - 1, l1 + l))
                {
                    this.worldObj.setBlock(k1 + k, i2 - 1, l1 + l, Blocks.ice, 0, 2);
                }

                if (this.worldObj.func_147478_e(k1 + k, i2, l1 + l, true))
                {
                    this.worldObj.setBlock(k1 + k, i2, l1 + l, Blocks.snow_layer, 0, 2);
                }
            }
        }

        if (options.getBoolean("cwg:generator.classic:enableModdedWorldgen")) {
            MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag));
        }

        BlockFalling.fallInstantly = false;
    }

    public boolean hasDesertLakes() {
        return options.getBoolean("cwg:classic_extreme_hills");
    }
    
}
