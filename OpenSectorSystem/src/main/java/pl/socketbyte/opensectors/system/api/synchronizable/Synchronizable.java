package pl.socketbyte.opensectors.system.api.synchronizable;

public abstract class Synchronizable<T> {

    private long id;

    public Synchronizable() {

    }

    public Synchronizable(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Deprecated
    public abstract T getData();
}
