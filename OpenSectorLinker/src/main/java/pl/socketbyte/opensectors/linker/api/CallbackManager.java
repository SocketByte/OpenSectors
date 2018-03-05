package pl.socketbyte.opensectors.linker.api;

import java.util.HashMap;
import java.util.Map;

public class CallbackManager {

    private static Map<String, Callback> callbackMap = new HashMap<>();

    public static Map<String, Callback> getCallbackMap() {
        return callbackMap;
    }

    public static void setCallbackMap(Map<String, Callback> callbackMap) {
        CallbackManager.callbackMap = callbackMap;
    }
}
