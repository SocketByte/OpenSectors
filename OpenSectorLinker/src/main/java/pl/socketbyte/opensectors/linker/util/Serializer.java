package pl.socketbyte.opensectors.linker.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Serializer {

    public static String serializeInventory(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(items);
            String encoded = Base64Coder.encodeLines(outputStream.toByteArray());
            outputStream.close();
            dataOutput.close();
            return encoded;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save itemstack array", e);
        }
    }

    public static ItemStack[] deserializeInventory(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] read = (ItemStack[]) dataInput.readObject();
            inputStream.close();
            dataInput.close();
            return read;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to read class type", e);
        }
    }

}
