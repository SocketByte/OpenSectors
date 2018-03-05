package pl.socketbyte.opensectors.linker.packet;

import pl.socketbyte.opensectors.linker.json.JSONConfig;

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
