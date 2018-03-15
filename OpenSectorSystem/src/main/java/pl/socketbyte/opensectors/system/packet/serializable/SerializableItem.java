package pl.socketbyte.opensectors.system.packet.serializable;

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
                ", data=" + data +
                ", displayName='" + displayName + '\'' +
                ", lores=" + lores +
                ", enchantments=" + enchantments +
                '}';
    }
}
