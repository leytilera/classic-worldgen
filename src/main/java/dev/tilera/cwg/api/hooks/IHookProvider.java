package dev.tilera.cwg.api.hooks;

public interface IHookProvider {

    @SuppressWarnings("unchecked")
    default <T> T getHook(IHookType<T> type) {
        return type.getHookClass().isAssignableFrom(this.getClass()) ? (T) this : null;
    }

    String getID();

    String getDisplayName();

    default boolean isSupported(IHookType<?> type) {
        return type.getHookClass().isAssignableFrom(this.getClass());
    }

}
