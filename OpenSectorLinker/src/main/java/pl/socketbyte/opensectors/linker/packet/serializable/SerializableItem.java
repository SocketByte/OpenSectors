package pl.socketbyte.opensectors.linker.packet.serializable;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.socketbyte.opensectors.linker.util.Util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SerializableItem {

    private String material;
    private int amount;
    private short data;
    private String displayName;
    private List<String> lores;

    private final Map<String, Integer> enchantments = new LinkedHashMap<>();

    public SerializableItem() {

    }

    public SerializableItem(ItemStack itemStack) {
        this.material = itemStack.getType().toString();
        this.amount = itemStack.getAmount();
        this.data = itemStack.getDurability();
        this.displayName = Util.fixColors(itemStack.getItemMeta().getDisplayName());
        this.lores = Util.fixColors(itemStack.getItemMeta().getLore());
        for (Map.Entry<Enchantment, Integer> enchantment : itemStack.getItemMeta().getEnchants().entrySet())
            this.enchantments.put(enchantment.getKey().getName(), enchantment.getValue());
    }

    public ItemStack deserialize() {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(material), amount, data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lores);
        itemStack.setItemMeta(meta);
        for (Map.Entry<String, Integer> enchantment : enchantments.entrySet())
            itemStack.addEnchantment(Enchantment.getByName(enchantment.getKey()), enchantment.getValue());

        return itemStack;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public short getData() {
        return data;
    }

    public void setData(short data) {
        this.data = data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLores() {
        return lores;
    }

    public void setLores(List<String> lores) {
        this.lores = lores;
    }

    public Map<String, Integer> getEnchantments() {
        return enchantments;
    }

    @Override
    public String toString() {
        return "SerializableItem{" +
                "material='" + material + '\'' +
                ", amount=" + amount +
                ", synchronizable=" + data +
                ", displayName='" + displayName + '\'' +
                ", lores=" + lores +
                ", enchantments=" + enchantments +
                '}';
    }
}
