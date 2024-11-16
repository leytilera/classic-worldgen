package dev.tilera.cwg.infdev;

import net.minecraft.block.Block;

public class ChunkConverter {
    
    public static void convert(Block[] blocks1, Block[] blocks2) {
        int len1 = blocks1.length / 256;
        int len2 = blocks2.length / 256;
        int k = Math.min(len1, len2);

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                for (int y = 0; y < k; ++y)
                {
                    int k1 = x * len1 * 16 | z * len1 | y;
                    int k2 = x * len2 * 16 | z * len2 | y;
                    blocks2[k2] = blocks1[k1];
                }
            }
        }
    }

}
