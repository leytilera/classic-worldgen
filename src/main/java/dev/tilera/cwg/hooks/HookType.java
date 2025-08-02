package dev.tilera.cwg.hooks;

import dev.tilera.cwg.api.CwgGlobals;
import dev.tilera.cwg.api.hooks.IHookType;

public class HookType<T> implements IHookType<T> {

    private Class<T> clazz;
    
    public HookType(Class<T> clazz) {
        this.clazz = clazz;
        CwgGlobals.getHookRegistry().getHookTypeManager().addHookType(this);
    }

    @Override
    public Class<T> getHookClass() {
        return clazz;
    }
    
}
