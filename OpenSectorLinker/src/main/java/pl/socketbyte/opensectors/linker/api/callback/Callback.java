package pl.socketbyte.opensectors.linker.api.callback;

public interface Callback<T> {
    void execute(T callback);
}
