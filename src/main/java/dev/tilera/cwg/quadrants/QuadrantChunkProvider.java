package dev.tilera.cwg.quadrants;

import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class QuadrantChunkProvider implements IChunkProvider {

    private QuadrantManager<IChunkProvider> quadrants;

    public QuadrantChunkProvider(QuadrantManager<IChunkProvider> quadrants) {
        this.quadrants = quadrants;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public boolean chunkExists(int x, int z) {
        return quadrants.getFor(x, z).chunkExists(x, z);
    }

    @Override
    public ChunkPosition func_147416_a(World arg0, String arg1, int x, int y, int z) {
        return quadrants.getFor(x, z).func_147416_a(arg0, arg1, x, y, z);
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType arg0, int x, int y, int z) {
        return quadrants.getFor(x, z).getPossibleCreatures(arg0, x, y, z);
    }

    @Override
    public Chunk loadChunk(int x, int z) {
        return quadrants.getFor(x, z).loadChunk(x, z);
    }

    @Override
    public String makeString() {
        return "ALEC";
    }

    @Override
    public void populate(IChunkProvider arg0, int x, int z) {
        quadrants.getFor(x, z).populate(arg0, x, z);
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        return quadrants.getFor(x, z).provideChunk(x, z);
    }

    @Override
    public void recreateStructures(int x, int z) {
        quadrants.getFor(x, z).recreateStructures(x, z);
    }

    @Override
    public boolean saveChunks(boolean arg0, IProgressUpdate arg1) {
        return true;
    }

    @Override
    public void saveExtraData() {}

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }
    
}
