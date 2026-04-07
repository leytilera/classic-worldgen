package dev.tilera.cwg.context;

import java.util.Stack;

import dev.tilera.cwg.api.context.IGeneratorContext;
import dev.tilera.cwg.api.context.IGeneratorContextHandler;
import net.anvilcraft.anvillib.api.inject.Implementation;
import net.anvilcraft.anvillib.api.inject.Inject;

@Implementation(IGeneratorContextHandler.class)
public class GeneratorContextHandler implements IGeneratorContextHandler {

    @Inject(IGeneratorContextHandler.class)
    public static IGeneratorContextHandler INSTANCE;
    private final ThreadLocal<Stack<IGeneratorContext>> contextStack = ThreadLocal.withInitial(Stack::new);
    private final IGeneratorContext global = new GlobalGeneratorContext();

    @Override
    public IGeneratorContext getContext() {
        if (contextStack.get().isEmpty()) {
            return global;
        }
        return contextStack.get().peek();
    }

    @Override
    public void begin(IGeneratorContext context) {
        contextStack.get().add(context);
    }

    @Override
    public void end(IGeneratorContext context) {
        IGeneratorContext prev = contextStack.get().pop();
        if (!prev.equals(context)) {
            throw new IllegalStateException("Bad generator context state: Expected context: " + context + " Actual context: " + prev);
        }
    }
    
}
