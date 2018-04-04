package pl.socketbyte.opensectors.linker.sector;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;

public class Sector {

    private final ServerController serverController;
    private World world;

    private int offset;

    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;

    private Location lower;
    private Location upper;

    public Sector(ServerController serverController, int minX, int minZ, int maxX, int maxZ, int offset) {
        this.world = Bukkit.getWorlds().get(0);
        this.minX = minX - offset;
        this.minZ = minZ - offset;
        this.maxX = maxX - offset;
        this.maxZ = maxZ - offset;
        this.offset = offset;
        this.serverController = serverController;
        setPositions();
    }

    public void setPositions() {
        Vector low = new Vector(minX, 0, minZ);
        Vector up = new Vector(maxX, 256, maxZ);
        this.lower = low.toLocation(world);
        this.upper = up.toLocation(world);
    }

    public boolean isNear(Location location) {
        return !isIn(location) && howClose(location) <= 20;
    }

    public boolean isNear(Location location, int additional) {
        return !isIn(location) && howClose(location) <= additional;
    }

    public double howClose(Location location) {
        if (isIn(location))
            return Double.MAX_VALUE;

        double x = location.getX();
        double z = location.getZ();
        double distWest = Math.abs(minX - x);
        double distEast = Math.abs(maxX - x);
        double distNorth = Math.abs(minZ - z);
        double distSouth = Math.abs(maxZ - z);
        double distX = (distWest < distEast) ? distWest : distEast;
        double distZ = (distNorth < distSouth) ? distNorth : distSouth;
        double distance = distX > distZ ? distZ : distX;
        return distance + OpenSectorLinker.getCurrentServer().offset;
    }

    public boolean isIn(Location location) {
        return !(this.lower == null || this.upper == null || location == null)
                && (location.getBlockX() > this.lower.getBlockX())
                && (location.getBlockX() < this.upper.getBlockX())
                && (location.getBlockY() > this.lower.getBlockY())
                && (location.getBlockY() < this.upper.getBlockY())
                && (location.getBlockZ() > this.lower.getBlockZ())
                && (location.getBlockZ() < this.upper.getBlockZ());
    }

    public ServerController getServerController() {
        return serverController;
    }

    public Location getLower() {
        return lower;
    }

    public void setLower(Location lower) {
        this.lower = lower;
    }

    public Location getUpper() {
        return upper;
    }

    public void setUpper(Location upper) {
        this.upper = upper;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "serverController=" + serverController +
                ", minX=" + minX +
                ", minZ=" + minZ +
                ", maxX=" + maxX +
                ", maxZ=" + maxZ +
                ", lower=" + lower +
                ", upper=" + upper +
                '}';
    }
}
