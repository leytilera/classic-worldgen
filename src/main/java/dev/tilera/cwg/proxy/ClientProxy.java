package dev.tilera.cwg.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

    @Override
    public World getWorld() {
        World world = null;
        if (MinecraftServer.getServer() != null) {
            World[] worlds = MinecraftServer.getServer().worldServers;
            if (worlds.length == 0) return null;
            world = worlds[0];
        } else if (Minecraft.getMinecraft() != null) {
            world = Minecraft.getMinecraft().theWorld;
        }
        return world;
    }

}
