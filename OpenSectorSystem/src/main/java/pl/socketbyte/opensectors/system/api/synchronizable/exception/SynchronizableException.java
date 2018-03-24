package pl.socketbyte.opensectors.system.api.synchronizable.exception;

public class SynchronizableException extends RuntimeException {

    public SynchronizableException() {
        super("Type is not extending Serializable class");
    }
}
