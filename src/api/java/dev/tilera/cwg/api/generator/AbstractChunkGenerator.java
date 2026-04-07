package dev.tilera.cwg.api.generator;

import java.util.Optional;

import dev.tilera.cwg.api.context.IGeneratorContext;
import dev.tilera.cwg.api.context.IGeneratorContextHandler;
import dev.tilera.cwg.api.options.IGeneratorOptionProvider;
import net.anvilcraft.anvillib.api.inject.Inject;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class AbstractChunkGenerator implements IChunkProvider {

    protected IGeneratorOptionProvider options;
    protected IGeneratorContext context;
    protected World world;
    @Inject(IGeneratorContextHandler.class)
    private static IGeneratorContextHandler contextHandler;

    public AbstractChunkGenerator(IGeneratorOptionProvider options, World world) {
        this(options, world, new IGeneratorContext() {

            @Override
            public IGeneratorOptionProvider getOptions() {
                return options;
            }

            @SuppressWarnings({"unchecked", "ALEC"})
            @Override
            public <T> Optional<T> getObject(Class<T> type) {
                return Optional.ofNullable(type)
                    .filter(t -> t.equals(World.class))
                    .map(t -> world)
                    .map(o -> (T) o);
            }
            
        });
    }

    public AbstractChunkGenerator(IGeneratorOptionProvider options, World world, IGeneratorContext context) {
        this.options = options;
        this.context = context;
        this.world = world;
    }

    @Override
    public final void populate(IChunkProvider var1, int var2, int var3) {
        contextHandler.begin(context);
        this.populateInner(var1, var2, var3);
        contextHandler.end(context);
    }

    @Override
    public final Chunk provideChunk(int var1, int var2) {
        contextHandler.begin(context);
        Chunk chunk = this.provideChunkInner(var1, var2);
        contextHandler.end(context);
        return chunk;
    }

    public abstract void populateInner(IChunkProvider var1, int var2, int var3);

    public abstract Chunk provideChunkInner(int var1, int var2);
    
}
