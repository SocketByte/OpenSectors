package pl.socketbyte.opensectors.linker.api.callback;

public interface ParametrizedCallback<T> {
    boolean allow(T callback);
}
