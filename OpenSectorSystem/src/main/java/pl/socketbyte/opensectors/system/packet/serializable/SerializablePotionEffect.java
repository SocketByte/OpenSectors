package pl.socketbyte.opensectors.system.packet.serializable;

import java.io.Serializable;

public class SerializablePotionEffect implements Serializable {

    private String potionEffectType;
    private int amplifier;
    private int duration;

    public SerializablePotionEffect() {

    }

    public String getPotionEffectType() {
        return potionEffectType;
    }

    public void setPotionEffectType(String potionEffectType) {
        this.potionEffectType = potionEffectType;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SerializablePotionEffect{" +
                "potionEffectType='" + potionEffectType + '\'' +
                ", amplifier=" + amplifier +
                ", duration=" + duration +
                '}';
    }
}
