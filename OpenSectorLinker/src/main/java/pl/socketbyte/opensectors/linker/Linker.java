package pl.socketbyte.opensectors.linker;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;
import pl.socketbyte.opensectors.linker.api.IPacketAdapter;
import pl.socketbyte.opensectors.linker.cryptography.Cryptography;
import pl.socketbyte.opensectors.linker.json.JSONConfig;
import pl.socketbyte.opensectors.linker.json.controllers.SQLController;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;
import pl.socketbyte.opensectors.linker.listeners.PlayerListeners;
import pl.socketbyte.opensectors.linker.listeners.PlayerMoveListener;
import pl.socketbyte.opensectors.linker.listeners.SectorProtectionListeners;
import pl.socketbyte.opensectors.linker.logging.StackTraceHandler;
import pl.socketbyte.opensectors.linker.logging.StackTraceSeverity;
import pl.socketbyte.opensectors.linker.packet.*;
import pl.socketbyte.opensectors.linker.packet.serializable.SerializablePotionEffect;
import pl.socketbyte.opensectors.linker.packet.serializable.SerializableResultSet;
import pl.socketbyte.opensectors.linker.sector.SectorManager;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Linker extends JavaPlugin {

    public static final String VERSION = "0.0.12a";

    private static final Client client = new Client(8192, 8192);
    private static Linker instance;
    private static JSONConfig config;
    private static int serverId;

    public static JSONConfig getConfiguration() {
        return config;
    }

    public static int getServerId() {
        return serverId;
    }

    public static void setServerId(int id) {
        serverId = id;
    }

    public static ServerController getServerController(int serverId) {
        for (ServerController serverController : config.serverControllers)
            if (serverController.id == serverId)
                return serverController;
        return null;
    }

    public static Logger log() {
        return getInstance().getLogger();
    }

    public static void setConfiguration(JSONConfig jsonConfig) {
        config = jsonConfig;
    }

    public static Client getClient() {
        return client;
    }

    public static Linker getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Logger logger = getLogger();
        logger.info("Loading resources...");

        logger.info("Setting kryonet logging level...");
        // Setting kryonet logging level to debug or hide messages.
        switch (getConfig().getString("kryonet-logging")) {
            case "NONE":
                Log.set(Log.LEVEL_NONE);
                break;
            case "DEBUG":
                Log.set(Log.LEVEL_DEBUG);
                break;
            case "INFO":
                Log.set(Log.LEVEL_INFO);
                break;
            case "TRACE":
                Log.set(Log.LEVEL_TRACE);
                break;
            case "WARN":
                Log.set(Log.LEVEL_WARN);
                break;
            case "ERROR":
                Log.set(Log.LEVEL_ERROR);
                break;
        }
        logger.info("Kryonet logging level set to: " + getConfig().getString("kryonet-logging"));

        logger.info("Saving default configuration...");
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
            logger.warning("Configuration file just got created. " +
                    "Please go to plugins/OpenSectorLinker/config.yml " +
                    "and change the default values before proceeding.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Starting client connection
        logger.info("Starting kryonet connection to proxy...");
        client.start();
        try {
            logger.info("Connecting to proxy...");
            client.connect(getConfig().getInt("proxy-connection-timeout"),
                    getConfig().getString("proxy-address"), getConfig().getInt("proxy-port-tcp"),
                    getConfig().getInt("proxy-port-udp"));
        } catch (IOException e) {
            StackTraceHandler.handle(Linker.class, e, StackTraceSeverity.FATAL);
            return;
        }
        logger.info("Registering kryo classes...");
        // Registering kryo classes to send between proxy and linker.
        Kryo kryo = client.getKryo();
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

        logger.info("Registering the client adapter...");
        // Registering the client adapter
        client.addListener(new ClientAdapter());

        logger.info("Reading the linker server id from configuration file...");
        Linker.setServerId(getConfig().getInt("server-id"));

        logger.info("Trying to authorize the linker...");
        // Authorizing the linker with proxy server.
        PacketLinkerAuthRequest packet = new PacketLinkerAuthRequest();
        packet.setPassword(Cryptography.sha256(getConfig().getString("auth-password")));
        packet.setServerId(Linker.getServerId());
        client.sendTCP(packet);
    }

    public static void ready() {
        log().info("Success. Linker is now authorized and ready to use.");
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), Linker.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), Linker.getInstance());
        Bukkit.getPluginManager().registerEvents(new SectorProtectionListeners(), Linker.getInstance());

        log().info("Loading sectors...");
        SectorManager.INSTANCE.load();

        log().info("Applying world border...");
        World world = Bukkit.getWorlds().get(0);

        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(config.border * 2);
        worldBorder.setCenter(0, 0);
        log().info("Ready!");
    }
}
