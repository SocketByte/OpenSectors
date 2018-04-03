package pl.socketbyte.opensectors.linker.api.callback;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallbackWaiter<T> {

    private static final Map<Class, CallbackWaiter> waiters = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> CallbackWaiter<T> getWaiter(Class<? extends T> clazz) {
        return waiters.get(clazz);
    }

    private final CompletableFuture<T> future = new CompletableFuture<>();
    private final Class<? extends T> typeClass;

    private ParametrizedCallback<T> parametrizedCallback;

    public CallbackWaiter(Class<? extends T> typeClass) {
        this.typeClass = typeClass;
    }

    public void setParametrizedCallback(ParametrizedCallback<T> parametrizedCallback) {
        this.parametrizedCallback = parametrizedCallback;
    }

    public ParametrizedCallback<T> getParametrizedCallback() {
        return parametrizedCallback;
    }

    public T waitAndGet() {
        waiters.put(typeClass, this);

        try {
            return future.get(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            return null;
        }
        return null;
    }

    public void complete(T value) {
        future.complete(value);
    }
}
