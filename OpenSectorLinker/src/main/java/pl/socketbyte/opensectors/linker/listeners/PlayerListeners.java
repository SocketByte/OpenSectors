package pl.socketbyte.opensectors.linker.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;
import pl.socketbyte.opensectors.linker.logging.StackTraceHandler;
import pl.socketbyte.opensectors.linker.logging.StackTraceSeverity;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerInfo;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerTransfer;
import pl.socketbyte.opensectors.linker.packet.PacketUpdatePlayerSession;
import pl.socketbyte.opensectors.linker.packet.serializable.SerializablePotionEffect;
import pl.socketbyte.opensectors.linker.util.NetworkManager;
import pl.socketbyte.opensectors.linker.util.PlayerInfoHolder;
import pl.socketbyte.opensectors.linker.util.Serializer;
import pl.socketbyte.opensectors.linker.util.Util;

import java.io.IOException;
import java.util.UUID;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // Works? Then don't touch.
        Bukkit.getPluginManager().callEvent(
                new PlayerMoveEvent(event.getPlayer(), event.getFrom(), event.getTo()));
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        PacketPlayerTransfer transferRequest = new PacketPlayerTransfer();
        transferRequest.setServerId(0);
        transferRequest.setPlayerUniqueId(player.getUniqueId().toString());

        PacketPlayerInfo playerInfo = new PacketPlayerInfo();
        playerInfo.setX(OpenSectorLinker.getInstance().getConfig().getInt("spawn.x"));
        playerInfo.setY(OpenSectorLinker.getInstance().getConfig().getInt("spawn.y"));
        playerInfo.setY(OpenSectorLinker.getInstance().getConfig().getInt("spawn.z"));
        playerInfo.setHealth(20);
        playerInfo.setFood(20);
        playerInfo.setGameMode("SURVIVAL");
        playerInfo.setFly(false);
        playerInfo.setPlayerUniqueId(player.getUniqueId().toString());

        transferRequest.setPlayerInfo(playerInfo);

        NetworkManager.sendTCP(transferRequest);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);
        PacketPlayerInfo packet = PlayerInfoHolder.getPlayerInfos().get(player.getUniqueId());

        // It probably means that joined the game without pending transfer.
        if (packet == null)
            return;

        try {
            if (packet.getInventory() != null)
                player.getInventory().setContents(
                        Serializer.deserializeInventory(packet.getInventory()));
            else player.getInventory().clear();

            if (packet.getArmorContents() != null)
                player.getInventory().setArmorContents(
                        Serializer.deserializeInventory(packet.getArmorContents()));

            if (packet.getEnderContents() != null)
                player.getEnderChest().setContents(
                        Serializer.deserializeInventory(packet.getEnderContents()));

        } catch (IOException e) {
            StackTraceHandler.handle(OpenSectorLinker.class, e, StackTraceSeverity.ERROR);
        }
        SerializablePotionEffect[] potionEffects = packet.getPotionEffects();
        Location destination = new Location(player.getWorld(), packet.getX(), packet.getY(), packet.getZ(),
                packet.getYaw(), packet.getPitch());
        player.teleport(Util.getValidLocation(destination, packet.getY()));

        if (potionEffects != null) {
            for (SerializablePotionEffect potionEffect : potionEffects) {
                PotionEffect effect = new PotionEffect(
                        PotionEffectType.getByName(potionEffect.getPotionEffectType()),
                        potionEffect.getDuration(),
                        potionEffect.getAmplifier());

                player.addPotionEffect(effect);
            }
        }

        player.setHealth(packet.getHealth());
        player.setFoodLevel((int) packet.getFood());
        player.setExp((float) packet.getExp());
        player.setLevel((int) packet.getLevel());
        player.setAllowFlight(packet.isFly());
        player.setGameMode(GameMode.valueOf(packet.getGameMode()));
        player.getInventory().setHeldItemSlot(packet.getHeldSlot());

        ServerController controller = OpenSectorLinker.getServerController(OpenSectorLinker.getServerId());
        for (String str : OpenSectorLinker.getInstance().getConfig().getStringList("sector-welcome-message")) {
            player.sendMessage(Util.fixColors(
                    str
                            .replace("%id%", String.valueOf(controller.id))
                            .replace("%name%", controller.name)));
        }

        PlayerInfoHolder.getPlayerInfos().remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        if (PlayerInfoHolder.getPlayerInfos().containsKey(event.getPlayer().getUniqueId()))
            PlayerInfoHolder.getPlayerInfos().remove(event.getPlayer().getUniqueId());

        UUID playerUniqueId = event.getPlayer().getUniqueId();

        PacketUpdatePlayerSession session = new PacketUpdatePlayerSession();
        session.setPlayerUniqueId(playerUniqueId.toString());
        session.setServerId(OpenSectorLinker.getServerId());

        NetworkManager.sendTCP(session);
    }
}
