package pl.socketbyte.opensectors.system;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import pl.socketbyte.opensectors.system.api.IPacketAdapter;
import pl.socketbyte.opensectors.system.cryptography.Cryptography;
import pl.socketbyte.opensectors.system.functionality.BukkitTimeCalculator;
import pl.socketbyte.opensectors.system.json.JSONConfig;
import pl.socketbyte.opensectors.system.json.JSONManager;
import pl.socketbyte.opensectors.system.json.controllers.SQLController;
import pl.socketbyte.opensectors.system.json.controllers.ServerController;
import pl.socketbyte.opensectors.system.logging.ByteInformator;
import pl.socketbyte.opensectors.system.logging.StackTraceHandler;
import pl.socketbyte.opensectors.system.logging.StackTraceSeverity;
import pl.socketbyte.opensectors.system.packet.*;
import pl.socketbyte.opensectors.system.packet.serializable.SerializablePotionEffect;
import pl.socketbyte.opensectors.system.packet.serializable.SerializableResultSet;
import pl.socketbyte.opensectors.system.util.ExecutorScheduler;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class OpenSectorSystem extends Plugin {

    public static final String VERSION = "0.0.12a";

    private static final Server server = new Server(8192, 8192);
    private static OpenSectorSystem instance;
    private static JSONConfig config;

    public static JSONConfig getConfig() {
        return config;
    }

    public static ServerController getServerController(int serverId) {
        for (ServerController serverController : config.serverControllers)
            if (serverController.id == serverId)
                return serverController;
        return null;
    }

    public static Server getServer() {
        return server;
    }

    public static OpenSectorSystem getInstance() {
        return instance;
    }

    protected static String getPassword() {
        return Cryptography.sha256(config.password);
    }

    public static Logger log() {
        return getInstance().getLogger();
    }

    @Override
    public void onEnable() {
        instance = this;

        Logger logger = getLogger();

        // Disable Kryonet Logging
        Log.set(Log.LEVEL_NONE);

        logger.info("Loading JSON configuration file...");
        JSONManager.INSTANCE.create();
        config = JSONManager.INSTANCE.getConfig();

        logger.info("Loading byte informator...");
        ByteInformator.runScheduledTask();

        logger.info("Connecting to the MySQL server...");
        Database.connect(config.sqlController.host, config.sqlController.port,
                config.sqlController.database, config.sqlController.user, config.sqlController.password);

        logger.info("Starting kryonet server instance...");
        server.start();
        try {
            logger.info("Binding TCP & UDP ports...");
            server.bind(config.portTCP, config.portUDP);
        } catch (IOException e) {
            StackTraceHandler.handle(OpenSectorSystem.class, e, StackTraceSeverity.FATAL);
            return;
        }
        logger.info("Registering kryo classes...");
        Kryo kryo = server.getKryo();
        kryo.register(Packet.class);
        kryo.register(Object.class);
        kryo.register(Object[].class);
        kryo.register(ServerController[].class);
        kryo.register(SQLController.class);
        kryo.register(IPacketAdapter.class);
        kryo.register(PacketPlayerTransferRequest.class);
        kryo.register(PacketLinkerAuthRequest.class);
        kryo.register(PacketCustomPayload.class);
        kryo.register(JSONConfig.class);
        kryo.register(ServerController.class);
        kryo.register(PacketConfigurationInfo.class);
        kryo.register(PacketTimeInfo.class);
        kryo.register(SerializablePotionEffect.class);
        kryo.register(SerializablePotionEffect[].class);
        kryo.register(PacketPlayerInfo.class);
        kryo.register(Map.class);
        kryo.register(HashMap.class);
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(ResultSet.class);
        kryo.register(SerializableResultSet.class);
        kryo.register(PacketQuery.class);
        kryo.register(PacketQueryExecute.class);
        kryo.register(PacketUpdatePlayerSession.class);

        logger.info("Registering server adapter...");
        server.addListener(new ServerAdapter());

        logger.info("Registering event adapter...");
        BungeeCord.getInstance().getPluginManager()
                .registerListener(this, new EventAdapter());

        logger.info("Running schedulers...");
        ExecutorScheduler.runScheduler(new BukkitTimeCalculator(), getConfig().bukkitTimeFrequency);
        logger.info("Ready!");
    }

    public void close() {
        server.close();

        // Based on (it could not work properly though)
        // https://github.com/SpigotMC/BungeeCord/blob/master/proxy/src/main/java/net/md_5/bungee/BungeeCord.java#L413
        this.onDisable();
        for (Handler handler : getLogger().getHandlers())
            handler.close();
        BungeeCord.getInstance().getScheduler().cancel(this);
        getExecutorService().shutdownNow();
    }

}
