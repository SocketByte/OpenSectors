package pl.socketbyte.opensectors.linker.adapters.tool;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.socketbyte.opensectors.linker.packet.PacketItemTransfer;

import java.util.UUID;

public class ItemTransferListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketItemTransfer))
            return;

        PacketItemTransfer packet = (PacketItemTransfer)object;

        Player player = Bukkit.getPlayer(UUID.fromString(packet.getPlayerUniqueId()));
        ItemStack itemStack = packet.getItemStack().deserialize();

        player.getInventory().addItem(itemStack);
    }
}
