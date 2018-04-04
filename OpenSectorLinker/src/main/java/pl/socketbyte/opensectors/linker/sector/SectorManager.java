package pl.socketbyte.opensectors.linker.sector;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.json.JSONConfig;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;

import java.util.HashMap;
import java.util.Map;

public enum SectorManager {
    INSTANCE;

    private final Map<Integer, Sector> sectorMap = new HashMap<>();

    public Map<Integer, Sector> getSectorMap() {
        return sectorMap;
    }


    public double howClose(Location location) {
        for (Sector sector : sectorMap.values())
            if (sector.isNear(location))
                return sector.howClose(location);
        return Double.MAX_VALUE;
    }

    public boolean isIn(Location loc) {
        for (Sector sector : sectorMap.values())
            if (sector.isIn(loc)
                    && sector.getServerController().id != OpenSectorLinker.getServerId())
                return true;
        return false;
    }

    public boolean isNear(Location location) {
        for (Sector sector : sectorMap.values()) {
            if (sector.isNear(location))
                return true;
        }
        return false;
    }

    public boolean isNear(Location location, int additional) {
        for (Sector sector : sectorMap.values()) {
            if (sector.isNear(location, additional))
                return true;
        }
        return false;
    }

    public Sector getAt(Location loc) {
        for (Sector sector : sectorMap.values())
            if (sector.isIn(loc)
                    && sector.getServerController().id != OpenSectorLinker.getServerId())
                return sector;
        return null;
    }

    public void load() {
        JSONConfig jsonConfig = OpenSectorLinker.getConfiguration();
        ServerController[] controllers = jsonConfig.serverControllers;
        int sectors = jsonConfig.sectors;

        for (int i = 0; i < sectors; i++) {
            ServerController controller = controllers[i];
            int offset = controller.offset;
            Sector sector = new Sector(controller, controller.minX, controller.minZ,
                    controller.maxX, controller.maxZ, offset);

            sectorMap.put(controller.id, sector);
        }
    }
}
