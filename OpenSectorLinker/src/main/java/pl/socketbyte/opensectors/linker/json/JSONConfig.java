package pl.socketbyte.opensectors.linker.json;

import pl.socketbyte.opensectors.linker.json.controllers.SQLController;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;

public class JSONConfig {

    public String password;
    public int sectorSize;
    public int sectors;
    public int portTCP;
    public int portUDP;
    public int bufferSize;
    public long bukkitTimeFrequency;
    public int bukkitTimeIncremental;
    public int border;
    public String defaultChatFormat;
    public SQLController sqlController;
    public ServerController[] serverControllers;

}
