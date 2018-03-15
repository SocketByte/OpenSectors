package pl.socketbyte.opensectors.system.packet;

import pl.socketbyte.opensectors.system.packet.serializable.SerializablePotionEffect;

import java.util.Arrays;

public class PacketPlayerInfo extends Packet {

    private String playerUniqueId;
    private String inventory;
    private String armorContents;
    private String enderContents;
    private int x;
    private int y;
    private int z;
    private float pitch;
    private float yaw;
    private SerializablePotionEffect[] potionEffects;
    private double health;
    private double food;
    private double exp;
    private double level;
    private String gameMode;
    private boolean fly;
    private int heldSlot;

    public PacketPlayerInfo() {

    }

    public String getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(String playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public int getHeldSlot() {
        return heldSlot;
    }

    public void setHeldSlot(int heldSlot) {
        this.heldSlot = heldSlot;
    }

    public String getArmorContents() {
        return armorContents;
    }

    public String getEnderContents() {
        return enderContents;
    }

    public void setEnderContents(String enderContents) {
        this.enderContents = enderContents;
    }

    public void setArmorContents(String armorContents) {
        this.armorContents = armorContents;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public boolean isFly() {
        return fly;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public SerializablePotionEffect[] getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(SerializablePotionEffect[] potionEffects) {
        this.potionEffects = potionEffects;
    }

    @Override
    public String toString() {
        return "PacketPlayerInfo{" +
                "playerUniqueId='" + playerUniqueId + '\'' +
                ", inventory='" + inventory + '\'' +
                ", armorContents='" + armorContents + '\'' +
                ", enderContents='" + enderContents + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                ", potionEffects=" + Arrays.toString(potionEffects) +
                ", health=" + health +
                ", food=" + food +
                ", exp=" + exp +
                ", level=" + level +
                ", gameMode='" + gameMode + '\'' +
                ", fly=" + fly +
                ", heldSlot=" + heldSlot +
                '}';
    }

}
