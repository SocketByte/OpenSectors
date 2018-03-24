package pl.socketbyte.opensectors.linker.api.synchronizable;

public abstract class Synchronizable {

    private long id;

    public Synchronizable() {

    }

    public Synchronizable(int id) {
        this.id = id;
        update();
    }

    public long getId() {
        return id;
    }

    public abstract void synchronize();
    public abstract void update();
}
