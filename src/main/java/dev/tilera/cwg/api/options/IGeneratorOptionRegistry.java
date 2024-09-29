package dev.tilera.cwg.api.options;

import java.util.Set;

public interface IGeneratorOptionRegistry extends IGeneratorOptionProvider {
    
   void registerOption(IOption<?> option);

   Set<String> getRegisteredOptions();

   String encodeOptions(IGeneratorOptionProvider provider);

   IGeneratorOptionProvider decodeOptions(String options);

   <T> boolean isRegistered(String id, Class<T> type);

}
