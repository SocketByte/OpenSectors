package pl.socketbyte.opensectors.system;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import pl.socketbyte.opensectors.linker.packet.PacketSendMessage;
import pl.socketbyte.opensectors.system.adapters.*;
import pl.socketbyte.opensectors.system.api.IPacketAdapter;
import pl.socketbyte.opensectors.system.api.synchronizable.Synchronizable;
import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedList;
import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedMap;
import pl.socketbyte.opensectors.system.cryptography.Cryptography;
import pl.socketbyte.opensectors.system.database.HikariManager;
import pl.socketbyte.opensectors.system.database.basic.HikariMySQL;
import pl.socketbyte.opensectors.system.adapters.player.PlayerSessionListener;
import pl.socketbyte.opensectors.system.adapters.player.PlayerStateListener;
import pl.socketbyte.opensectors.system.adapters.player.PlayerTeleportListener;
import pl.socketbyte.opensectors.system.adapters.player.PlayerTransferListener;
import pl.socketbyte.opensectors.system.adapters.query.QueryExecuteListener;
import pl.socketbyte.opensectors.system.adapters.query.QueryListener;
import pl.socketbyte.opensectors.system.adapters.tool.ItemTransferListener;
import pl.socketbyte.opensectors.system.adapters.tool.SendMessageListener;
import pl.socketbyte.opensectors.system.packet.serializable.*;
import pl.socketbyte.opensectors.system.packet.types.MessageType;
import pl.socketbyte.opensectors.system.packet.types.Receiver;
import pl.socketbyte.opensectors.system.packet.types.Weather;
import pl.socketbyte.opensectors.system.synchronizers.BukkitTimeSynchronizer;
import pl.socketbyte.opensectors.system.json.JSONConfig;
import pl.socketbyte.opensectors.system.json.JSONManager;
import pl.socketbyte.opensectors.system.json.controllers.SQLController;
import pl.socketbyte.opensectors.system.json.controllers.ServerController;
import pl.socketbyte.opensectors.system.logging.ByteInformator;
import pl.socketbyte.opensectors.system.logging.StackTraceHandler;
import pl.socketbyte.opensectors.system.logging.StackTraceSeverity;
import pl.socketbyte.opensectors.system.packet.*;
import pl.socketbyte.opensectors.system.synchronizers.BukkitWeatherSynchronizer;
import pl.socketbyte.opensectors.system.api.synchronizable.TaskManager;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class OpenSectorSystem extends Plugin {

    public static final String VERSION = "1.1";

    private static Server server;
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

    public static String getPassword() {
        return Cryptography.sha256(config.password);
    }

    public static Logger log() {
        return getInstance().getLogger();
    }

    @Override
    public void onEnable() {
        instance = this;

        Logger logger = getLogger();

        // Kryonet logging to LEVEL_WARN
        Log.set(Log.LEVEL_WARN);

        logger.info("Loading JSON configuration file...");
        JSONManager.INSTANCE.create();
        config = JSONManager.INSTANCE.getConfig();

        logger.info("Loading byte informator...");
        ByteInformator.runScheduledTask();

        if (config.sqlController.useDefaultSql) {
            logger.info("Connecting to the default MySQL server...");

            HikariMySQL hikariMySQL = new HikariMySQL();
            hikariMySQL.setHost(config.sqlController.host);
            hikariMySQL.setDatabase(config.sqlController.database);
            hikariMySQL.setPort(config.sqlController.port);
            hikariMySQL.setPassword(config.sqlController.password);
            hikariMySQL.setUser(config.sqlController.user);
            hikariMySQL.apply();

            hikariMySQL.setTableWork(connection -> {
                try {
                    PreparedStatement statement = connection.prepareStatement(
                            "CREATE TABLE IF NOT EXISTS playerData (" +
                                    "uniqueId CHAR(36) NOT NULL, " +
                                    "serverId INT NOT NULL)");
                    statement.executeUpdate();
                    statement.close();
                } catch (SQLException e) {
                    StackTraceHandler.handle(Database.class, e, StackTraceSeverity.ERROR);
                }
                finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            HikariManager.INSTANCE.connect();
            HikariManager.INSTANCE.createBasicTables();
        }

        logger.info("Starting kryonet server instance...");
        server = new Server(config.bufferSize, config.bufferSize);
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
        kryo.register(PacketPlayerTransfer.class);
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
        kryo.register(Weather.class);
        kryo.register(PacketWeatherInfo.class);
        kryo.register(Receiver.class);
        kryo.register(MessageType.class);
        kryo.register(PacketSendMessage.class);
        kryo.register(LinkedHashMap.class);
        kryo.register(SerializableItem.class);
        kryo.register(PacketItemTransfer.class);
        kryo.register(PacketPlayerState.class);
        kryo.register(PacketPlayerTeleport.class);
        kryo.register(TimeUnit.class);
        kryo.register(PacketTaskCreate.class);
        kryo.register(PacketTaskValidate.class);
        kryo.register(Synchronizable.class);
        kryo.register(SynchronizedList.class);
        kryo.register(SynchronizedMap.class);
        kryo.register(PacketListUpdate.class);
        kryo.register(PacketMapUpdate.class);

        logger.info("Registering server adapter...");
        server.addListener(new ServerAdapter());
        server.addListener(new PlayerSessionListener());
        server.addListener(new PlayerStateListener());
        server.addListener(new PlayerTeleportListener());
        server.addListener(new PlayerTransferListener());
        server.addListener(new QueryExecuteListener());
        server.addListener(new QueryListener());
        server.addListener(new ItemTransferListener());
        server.addListener(new SendMessageListener());
        server.addListener(new CustomPayloadListener());
        server.addListener(new LinkerAuthListener());
        server.addListener(new TaskReceiveListener());
        server.addListener(new ListUpdateListener());
        server.addListener(new MapUpdateListener());

        logger.info("Registering event adapter...");
        ProxyServer.getInstance().getPluginManager()
                .registerListener(this, new EventAdapter());

        logger.info("Running schedulers...");
        TaskManager.getExecutor().scheduleAtFixedRate(
                new BukkitTimeSynchronizer(), 0, getConfig().bukkitTimeFrequency, TimeUnit.MILLISECONDS);
        TaskManager.getExecutor().scheduleAtFixedRate(
                new BukkitWeatherSynchronizer(), 0, getConfig().bukkitTimeFrequency, TimeUnit.MILLISECONDS);
        logger.info("Ready!");
    }

    public void close() {
        server.close();
        HikariManager.INSTANCE.getHikariExtender().getDataSource().close();

        // Based on (it could not work properly though)
        // https://github.com/SpigotMC/BungeeCord/blob/master/proxy/src/main/java/net/md_5/bungee/BungeeCord.java#L413
        this.onDisable();
        for (Handler handler : getLogger().getHandlers())
            handler.close();
        ProxyServer.getInstance().getScheduler().cancel(this);
        getExecutorService().shutdownNow();
    }

}
