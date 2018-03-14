package pl.socketbyte.opensectors.system.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import pl.socketbyte.opensectors.system.json.controllers.SQLController;
import pl.socketbyte.opensectors.system.json.controllers.ServerController;

import java.io.*;
import java.nio.charset.StandardCharsets;

public enum JSONManager {
    INSTANCE;

    private final File dir = new File("plugins" + File.separator + "OpenSectorSystem");
    private final File file = new File("plugins" + File.separator + "OpenSectorSystem" +
            File.separator + "config.json");

    public boolean create() {
        if (!file.exists()) {
            try {
                dir.mkdirs();
                file.createNewFile();

                try (Writer writer = new OutputStreamWriter(new FileOutputStream(file.getPath()), StandardCharsets.UTF_8)){
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JSONConfig config = new JSONConfig();
                    config.password = "CHANGE_THIS_TO_YOUR_PASSWORD___IT_NEEDS_TO_BE_SECURE!";
                    config.sectorSize = 2000;
                    config.sectors = 4;
                    config.portTCP = 23904;
                    config.portUDP = 23905;
                    config.bufferSize = 8192;
                    config.bukkitTimeFrequency = 500;
                    config.bukkitTimeIncremental = 10;
                    config.border = 1500;
                    config.defaultChatFormat = "&7{PLAYER}&8: &f{MESSAGE}";
                    SQLController sqlController = new SQLController();
                    sqlController.host = "localhost";
                    sqlController.port = 3306;
                    sqlController.user = "root";
                    sqlController.password = "";
                    sqlController.database = "opensectors";
                    sqlController.useDefaultSql = true;
                    config.sqlController = sqlController;
                    ServerController example1 = new ServerController();
                    example1.port = 25566;
                    example1.id = 0;
                    example1.name = "s1";
                    example1.x = 0;
                    example1.z = 0;
                    ServerController example2 = new ServerController();
                    example2.port = 25567;
                    example2.id = 1;
                    example2.name = "s2";
                    example1.x = 1000;
                    example1.z = 0;
                    ServerController example3 = new ServerController();
                    example3.port = 25568;
                    example3.id = 2;
                    example3.name = "s3";
                    example1.x = 0;
                    example1.z = 1000;
                    ServerController example4 = new ServerController();
                    example4.port = 25569;
                    example4.id = 3;
                    example4.name = "s4";
                    example1.x = -1000;
                    example1.z = 0;
                    config.serverControllers = new ServerController[] {example1, example2, example3, example4};

                    gson.toJson(config, writer);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public JSONConfig getConfig() {
        try {
            JsonReader reader = new JsonReader(new FileReader(file.getPath()));
            Gson gson = new GsonBuilder().create();

            return gson.fromJson(reader, JSONConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
