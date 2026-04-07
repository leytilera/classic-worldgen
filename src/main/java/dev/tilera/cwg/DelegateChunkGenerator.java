package dev.tilera.cwg;

import java.util.List;

import dev.tilera.cwg.api.generator.AbstractChunkGenerator;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class DelegateChunkGenerator extends AbstractChunkGenerator {

    private IChunkProvider generator;

    public DelegateChunkGenerator(IGeneratorOptionProvider options, World world, IChunkProvider generator) {
        super(options, world);
        this.generator = generator;
    }

    @Override
    public boolean chunkExists(int p_73149_1_, int p_73149_2_) {
        return generator.chunkExists(p_73149_1_, p_73149_2_);
    }

    @Override
    public Chunk loadChunk(int p_73158_1_, int p_73158_2_) {
        return generator.loadChunk(p_73158_1_, p_73158_2_);
    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
        return generator.saveChunks(p_73151_1_, p_73151_2_);
    }

    @Override
    public boolean unloadQueuedChunks() {
        return generator.unloadQueuedChunks();
    }

    @Override
    public boolean canSave() {
        return generator.canSave();
    }

    @Override
    public String makeString() {
        return generator.makeString();
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
        return generator.getPossibleCreatures(p_73155_1_, p_73155_2_, p_73155_3_, p_73155_4_);
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return generator.func_147416_a(p_147416_1_, p_147416_2_, p_147416_3_, p_147416_4_, p_147416_5_);
    }

    @Override
    public int getLoadedChunkCount() {
        return generator.getLoadedChunkCount();
    }

    @Override
    public void recreateStructures(int p_82695_1_, int p_82695_2_) {
        generator.recreateStructures(p_82695_1_, p_82695_2_);
    }

    @Override
    public void saveExtraData() {
        generator.saveExtraData();
    }

    @Override
    public void populateInner(IChunkProvider var1, int var2, int var3) {
        generator.populate(var1, var2, var3);
    }

    @Override
    public Chunk provideChunkInner(int var1, int var2) {
        return generator.provideChunk(var1, var2);
    }
    
}
