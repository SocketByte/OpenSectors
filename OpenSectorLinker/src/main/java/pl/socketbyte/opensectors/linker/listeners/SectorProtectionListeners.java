package pl.socketbyte.opensectors.linker.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.sector.SectorManager;
import pl.socketbyte.opensectors.linker.util.Util;

public class SectorProtectionListeners implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SectorManager.INSTANCE.isNear(location, 45)) {
            player.sendMessage(Util.fixColors(OpenSectorLinker.getInstance()
                    .getConfig().getString("sector-break-message")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SectorManager.INSTANCE.isNear(location, 45)) {
            player.sendMessage(Util.fixColors(OpenSectorLinker.getInstance()
                    .getConfig().getString("sector-place-message")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Player player = event.getPlayer();
        if (player == null)
            return;
        if (event.getBlock() == null)
            return;
        Location location = event.getBlock().getLocation();

        if (SectorManager.INSTANCE.isNear(location, 45)) {
            player.sendMessage(Util.fixColors(OpenSectorLinker.getInstance()
                    .getConfig().getString("sector-ignite-message")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlockClicked().getLocation();

        if (SectorManager.INSTANCE.isNear(location, 45)) {
            player.sendMessage(Util.fixColors(OpenSectorLinker.getInstance()
                    .getConfig().getString("sector-bucket-message")));
            event.setCancelled(true);
        }
    }
}
