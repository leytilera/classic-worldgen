package dev.tilera.cwg;

import dev.tilera.cwg.api.CwgGlobals;
import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class ChunkManagerClient extends DelegateChunkManager {

    private boolean isSingleplayer;

    public ChunkManagerClient() {
        super(CwgGlobals.getOptionManager().getOptionRegistry(), null, new WorldChunkManagerHell(BiomeGenBase.ocean, 0.5f));
        this.isSingleplayer = Minecraft.getMinecraft().isSingleplayer();
    }

    @Override
    public BiomeGenBase getBiomeGenAt(int p_76935_1_, int p_76935_2_) {
        if (this.isSingleplayer) {
            return CwgGlobals.getCurrentState().getBiomeGenForCoordsBody(p_76935_1_, p_76935_2_);
        } else {
            return super.getBiomeGenAt(p_76935_1_, p_76935_2_);
        }
    }    
    
}
