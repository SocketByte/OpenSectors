package pl.socketbyte.opensectors.linker.json.controllers;

public class ServerController {

    public int id;
    public int port;
    public String name;
    public int x; // center x
    public int z; // center z

    @Override
    public String toString() {
        return "ServerController{" +
                "id=" + id +
                ", port=" + port +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", z=" + z +
                '}';
    }
}
