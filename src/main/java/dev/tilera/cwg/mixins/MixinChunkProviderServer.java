package dev.tilera.cwg.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import cpw.mods.fml.common.registry.GameRegistry;
import dev.tilera.cwg.api.CwgGlobals;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;

@Mixin(ChunkProviderServer.class)
public abstract class MixinChunkProviderServer implements IChunkProvider {

    /**
     * @author tilera
     * @reason Only generate modded things when enabled
     */
    @Overwrite(remap = false)
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {
        Chunk chunk = this.provideChunk(p_73153_2_, p_73153_3_);
        if (!chunk.isTerrainPopulated) {
            chunk.func_150809_p();
            if (((ChunkProviderServer) (IChunkProvider) this).currentChunkProvider != null) {
                ((ChunkProviderServer) (IChunkProvider) this).currentChunkProvider.populate(p_73153_1_, p_73153_2_,
                        p_73153_3_);
                if (CwgGlobals.getOptionProvider(((ChunkProviderServer) (IChunkProvider) this).worldObj)
                        .getBoolean("cwg:generator.classic:enableModdedWorldgen")) {
                    GameRegistry.generateWorld(p_73153_2_, p_73153_3_,
                            ((ChunkProviderServer) (IChunkProvider) this).worldObj,
                            ((ChunkProviderServer) (IChunkProvider) this).currentChunkProvider, p_73153_1_);
                }
                chunk.setChunkModified();
            }
        }

    }

}
