package pl.socketbyte.opensectors.linker.api.synchronizable;

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
    public abstract void synchronize();
    public abstract void update();
}

