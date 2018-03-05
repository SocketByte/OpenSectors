package pl.socketbyte.opensectors.linker.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerTransferHolder {

    private static List<UUID> transfering = new ArrayList<>();

    public static List<UUID> getTransfering() {
        return transfering;
    }
}
