package pl.socketbyte.opensectors.system.json.controllers;

public class ServerController {

    public int id;
    public int offset;
    public int port;
    public String name;
    public int minX;
    public int minZ;
    public int maxX;
    public int maxZ;

    @Override
    public String toString() {
        return "ServerController{" +
                "id=" + id +
                ", offset=" + offset +
                ", port=" + port +
                ", name='" + name + '\'' +
                ", minX=" + minX +
                ", minZ=" + minZ +
                ", maxX=" + maxX +
                ", maxZ=" + maxZ +
                '}';
    }
}
