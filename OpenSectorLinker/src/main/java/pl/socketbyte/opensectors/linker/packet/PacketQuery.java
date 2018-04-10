package pl.socketbyte.opensectors.linker.packet;

import java.util.LinkedHashMap;
import java.util.Map;

public class PacketQuery extends Packet {

    private String query;
    private Map<Integer, Object> replacements = new LinkedHashMap<>();

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

    public void addReplacement(int index, Object object) {
        this.replacements.put(index, object);
    }

    @Override
    public String toString() {
        return "PacketQuery{" +
                "query='" + query + '\'' +
                ", replacements=" + replacements +
                '}';
    }
}
