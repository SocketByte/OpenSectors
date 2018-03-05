package pl.socketbyte.opensectors.system;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;

public class AuthorizedConnectionData {

    private static Map<Integer, Connection> authorizedConnectionMap = new HashMap<>();

    public static Map<Integer, Connection> getAuthorizedConnectionMap() {
        return authorizedConnectionMap;
    }
}
