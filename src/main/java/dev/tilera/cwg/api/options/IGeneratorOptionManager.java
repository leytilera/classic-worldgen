package dev.tilera.cwg.api.options;

import dev.tilera.cwg.api.hooks.IHookRegistry;
import net.minecraft.world.World;

import java.util.UUID;

public interface IGeneratorOptionManager {

    public static UUID DEFAULT = UUID.fromString("15807302-4764-50f4-947c-cdf91a282c27");
    public static UUID CONFIG = UUID.fromString("16b4dc2c-46f8-5011-960f-155908a17de7");
    public static UUID REGISTRY = UUID.fromString("005f303b-d342-5a27-9ca3-73a22b5aabd9");
    public static UUID CLASSIC = UUID.fromString("31fad291-5e62-5e50-a079-812a3c06cd61");

    IGeneratorOptionRegistry getOptionRegistry();

    IHookRegistry getHookRegistry();

    <T> IOptionBuilder<T> builder(Class<T> clazz);

    IGeneratorOptionProvider getOptions(UUID optionSet);

    void saveOptions(UUID optionSet, IGeneratorOptionProvider options);

    IGeneratorOptionManager getWorldOptionManager(World world);

}
