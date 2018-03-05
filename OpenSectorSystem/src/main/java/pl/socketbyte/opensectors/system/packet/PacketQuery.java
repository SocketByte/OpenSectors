package pl.socketbyte.opensectors.system.packet;

import java.util.Map;

public class PacketQuery extends Packet {

    private String query;
    private Map<Integer, Object> replacements;

    public PacketQuery() {

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<Integer, Object> getReplacements() {
        return replacements;
    }

    public void setReplacements(Map<Integer, Object> replacements) {
        this.replacements = replacements;
    }

    @Override
    public String toString() {
        return "PacketQuery{" +
                "query='" + query + '\'' +
                ", replacements=" + replacements +
                '}';
    }
}
