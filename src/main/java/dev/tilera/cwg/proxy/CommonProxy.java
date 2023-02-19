package dev.tilera.cwg.proxy;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommonProxy {
    
    public World getWorld() {
        World[] worlds = MinecraftServer.getServer().worldServers;
        if (worlds.length == 0) return null;
        return worlds[0];
    }

}
