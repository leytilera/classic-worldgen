package dev.tilera.cwg.api.context;

public interface IGeneratorContextHandler {
    
    IGeneratorContext getContext();

    void begin(IGeneratorContext context);

    void end(IGeneratorContext context);

}
