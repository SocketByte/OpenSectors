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

    public boolean isIn(Location loc) {
        for (Sector sector : sectorMap.values())
            if (sector.isIn(loc))
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

    public Sector getNear(Location location) {
        Map<Sector, Double> sectors = new HashMap<>();
        for (Sector sector : sectorMap.values()) {
            sectors.put(sector, sector.howCloseCenter(location));
        }
        Map.Entry<Sector, Double> min = null;
        for (Map.Entry<Sector, Double> entry : sectors.entrySet())
            if (min == null || min.getValue() > entry.getValue())
                min = entry;
        if (min == null)
            return null;
        return min.getKey();
    }

    public Sector getAt(Location loc) {
        for (Sector sector : sectorMap.values())
            if (sector.isIn(loc))
                return sector;
        return null;
    }

    public void load() {
        JSONConfig jsonConfig = OpenSectorLinker.getConfiguration();
        ServerController[] controllers = jsonConfig.serverControllers;
        int size = jsonConfig.sectorSize;
        int sectors = jsonConfig.sectors;

        for (int i = 0; i < sectors; i++) {
            ServerController controller = controllers[i];

            Location center = new Location(Bukkit.getWorlds().get(0),
                    controller.x, 0, controller.z);
            Sector sector = new Sector(controller, center, size);

            sectorMap.put(controller.id, sector);
        }
    }
}
