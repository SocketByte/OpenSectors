package pl.socketbyte.opensectors.linker.sector;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;

public class Sector {

    private final ServerController serverController;

    private Location center;
    private int size;

    private Location lower;
    private Location upper;

    public Sector(ServerController serverController, Location center, int size) {
        this.center = center;
        this.size = size;
        this.serverController = serverController;
        setPositions();
    }

    public void setPositions() {
        Vector low = new Vector(center.getBlockX() - this.size, 0, center.getBlockZ() - this.size);
        Vector up = new Vector(center.getBlockX() + this.size, 256, center.getBlockZ() + this.size);
        this.lower = low.toLocation(center.getWorld());
        this.upper = up.toLocation(center.getWorld());
    }

    public boolean isAtEdge(Location location) {
        return !isIn(location) && isNear(location)
                && (howClose(location) < 2);
    }

    public boolean isNear(Location location) {
        return !isIn(location) && howClose(location) <= 20;
    }

    public boolean isNear(Location location, int additional) {
        return !isIn(location) && howClose(location) <= additional;
    }

    public double howCloseCenter(Location location) {
        if (isIn(location))
            return Double.MAX_VALUE;
        return getCenter().distance(location);
    }

    public double howClose(Location location) {
        if (isIn(location))
            return Double.MAX_VALUE;

        double x = location.getX();
        double z = location.getZ();
        double distWest = Math.abs((center.getBlockX() + this.size) - x);
        double distEast = Math.abs((center.getBlockX() - this.size) - x);
        double distNorth = Math.abs((center.getBlockZ() - this.size) - z);
        double distSouth = Math.abs((center.getBlockZ() + this.size) - z);
        double distX = (distWest < distEast) ? distWest : distEast;
        double distZ = (distNorth < distSouth) ? distNorth : distSouth;
        return distX > distZ ? distZ : distX;
    }

    public boolean isIn(Location location) {
        setPositions();
        return !(this.lower == null || this.upper == null || location == null)
                && (location.getBlockX() > this.lower.getBlockX())
                && (location.getBlockX() < this.upper.getBlockX())
                && (location.getBlockY() > this.lower.getBlockY())
                && (location.getBlockY() < this.upper.getBlockY())
                && (location.getBlockZ() > this.lower.getBlockZ())
                && (location.getBlockZ() < this.upper.getBlockZ());
    }

    public void setSize(int size) {
        this.size = size;
        this.setPositions();
    }

    public void setCenter(Location center) {
        this.center = center;
        this.setPositions();
    }

    public ServerController getServerController() {
        return serverController;
    }

    public Location getCenter() {
        return center;
    }

    public int getSize() {
        return size;
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
                ", center=" + center +
                ", size=" + size +
                ", lower=" + lower +
                ", upper=" + upper +
                '}';
    }
}
