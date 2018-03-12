package pl.socketbyte.opensectors.linker.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerInfo;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerTransferRequest;
import pl.socketbyte.opensectors.linker.packet.serializable.SerializablePotionEffect;
import pl.socketbyte.opensectors.linker.sector.Sector;
import pl.socketbyte.opensectors.linker.sector.SectorManager;
import pl.socketbyte.opensectors.linker.util.*;

import java.text.DecimalFormat;
import java.util.Collection;

public class PlayerMoveListener implements Listener {

    private final DecimalFormat df = new DecimalFormat("#.#");

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(OpenSectorLinker.getInstance(), () -> {
            Location from = event.getFrom();
            Location to = event.getTo();

            if (SectorManager.INSTANCE.isNear(to)) {
                Sector sector = SectorManager.INSTANCE.getNear(to);
                if (sector == null)
                    return;

                double howClose = sector.howClose(to);

                ActionBar.send(player, OpenSectorLinker.getInstance().getConfig().getString("sector-border-close")
                        .replace("%distance%", df.format(howClose).replace(",", ".")));

                if (!sector.isAtEdge(to))
                    return;

                if (PlayerTransferHolder.getTransfering().contains(player.getUniqueId()))
                    return;

                int x = player.getLocation().getBlockX(), z = player.getLocation().getBlockZ();
                ServerController current = SectorManager.INSTANCE.getSectorMap()
                        .get(OpenSectorLinker.getServerId())
                        .getServerController();
                int[] destination = Util.getDestinationWithOffset(current,
                        sector.getServerController(), x, z);

                PlayerTransferHolder.getTransfering().add(player.getUniqueId());

                PacketPlayerTransferRequest packet = new PacketPlayerTransferRequest();
                packet.setPlayerUniqueId(player.getUniqueId().toString());
                packet.setServerId(sector.getServerController().id);

                PacketPlayerInfo packetPlayerInfo = new PacketPlayerInfo();
                packetPlayerInfo.setPlayerUniqueId(player.getUniqueId().toString());
                packetPlayerInfo.setInventory(Serializer.serializeInventory(player.getInventory().getContents()));
                packetPlayerInfo.setArmorContents(Serializer.serializeInventory(player.getInventory().getArmorContents()));

                Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
                SerializablePotionEffect[] potionEffects = new SerializablePotionEffect[activePotionEffects.size()];
                int i = 0;
                for (PotionEffect effect : activePotionEffects) {
                    SerializablePotionEffect potionEffect = new SerializablePotionEffect();

                    potionEffect.setPotionEffectType(effect.getType().getName());
                    potionEffect.setAmplifier(effect.getAmplifier());
                    potionEffect.setDuration(effect.getDuration());

                    potionEffects[i] = potionEffect;
                    i++;
                }

                packetPlayerInfo.setPotionEffects(potionEffects);

                packetPlayerInfo.setX(destination[0]);
                packetPlayerInfo.setY(player.getLocation().getBlockY());
                packetPlayerInfo.setZ(destination[1]);
                packetPlayerInfo.setPitch(player.getLocation().getPitch());
                packetPlayerInfo.setYaw(player.getLocation().getYaw());

                packetPlayerInfo.setHealth(player.getHealth());
                packetPlayerInfo.setFood(player.getFoodLevel());
                packetPlayerInfo.setExp(player.getExp());
                packetPlayerInfo.setLevel(player.getLevel());
                packetPlayerInfo.setFly(player.getAllowFlight());
                packetPlayerInfo.setGameMode(player.getGameMode().name());
                packetPlayerInfo.setHeldSlot(player.getInventory().getHeldItemSlot());

                packet.setPlayerInfo(packetPlayerInfo);

                NetworkManager.sendTCP(packet);

                Bukkit.getScheduler().runTaskLater(OpenSectorLinker.getInstance(),
                        () -> PlayerTransferHolder.getTransfering().remove(player.getUniqueId()), 10);
            }
        });
    }
}
