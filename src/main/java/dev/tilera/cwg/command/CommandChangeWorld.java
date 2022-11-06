package dev.tilera.cwg.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;

public class CommandChangeWorld extends CommandBase {

    @Override
    public String getCommandName() {
        return "cwg";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "Change WorldType of current world";
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) {
        if (arg1.length == 1) {
            WorldType newType = WorldType.parseWorldType(arg1[0]);
            if (newType != null) {
                WorldInfo info = arg0.getEntityWorld().getWorldInfo();
                info.setTerrainType(newType);
                arg0.getEntityWorld().getSaveHandler().saveWorldInfo(info);
            } else {
                arg0.addChatMessage(new ChatComponentText(arg1[0] + " is not a valid WorldType"));
            }
        } else {
            arg0.addChatMessage(new ChatComponentText("You must pass a WorldType to use this command"));
        }
    }
    
}
