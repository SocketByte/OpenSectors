package pl.socketbyte.opensectors.linker.json.controllers;

public class SQLController {

    public String host;
    public int port;
    public String user;
    public String password;
    public String database;

    @Override
    public String toString() {
        return "SQLController{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
