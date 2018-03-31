package pl.socketbyte.opensectors.system.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import pl.socketbyte.opensectors.system.json.controllers.SQLController;
import pl.socketbyte.opensectors.system.json.controllers.ServerController;
import pl.socketbyte.opensectors.system.logging.StackTraceHandler;
import pl.socketbyte.opensectors.system.logging.StackTraceSeverity;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public enum JSONManager {
    INSTANCE;

    private final File dir = new File("plugins" + File.separator + "OpenSectorSystem");
    private final File file = new File("plugins" + File.separator + "OpenSectorSystem" +
            File.separator + "config.json");

    public void create() {
        if (!file.exists()) {
            if (!dir.mkdirs())
                return;

            try {
                ClassLoader classLoader = JSONManager.class.getClassLoader();
                InputStream stream = classLoader.getResourceAsStream("config.json");

                if (!file.createNewFile())
                    return;

                PrintWriter pw = new PrintWriter(new FileWriter(file));
                InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader);
                for (String line; (line = reader.readLine()) != null;)
                    pw.println(line);

                pw.close();
                reader.close();
                streamReader.close();
                stream.close();
            } catch (IOException e) {
                StackTraceHandler.handle(JSONManager.class, e, StackTraceSeverity.FATAL);
            }
        }
    }

    public JSONConfig getConfig() {
        try {
            JsonReader reader = new JsonReader(new FileReader(file.getPath()));
            Gson gson = new GsonBuilder().create();

            return gson.fromJson(reader, JSONConfig.class);
        } catch (IOException e) {
            StackTraceHandler.handle(JSONManager.class, e, StackTraceSeverity.FATAL);
        }
        return null;
    }

}
