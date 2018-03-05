package pl.socketbyte.opensectors.system.packet;

import pl.socketbyte.opensectors.system.json.JSONConfig;

public class PacketConfigurationInfo extends Packet {

    private JSONConfig jsonConfig;

    public PacketConfigurationInfo() {

    }

    public JSONConfig getJsonConfig() {
        return jsonConfig;
    }

    public void setJsonConfig(JSONConfig jsonConfig) {
        this.jsonConfig = jsonConfig;
    }

    @Override
    public String toString() {
        return "PacketConfigurationInfo{" +
                ", jsonConfig=" + jsonConfig +
                '}';
    }
}
