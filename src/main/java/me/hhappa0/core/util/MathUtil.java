package me.hhappa0.core.util;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Provides static utility methods for common mathematical operations, particularly those involving Bukkit/Paper objects.
 */
public class MathUtil {

    /**
     * Approximates the square root of a number N using the Babylonian method.
     * @param n The number whose square root is to be found. Must be non-negative.
     * @return The approximate square root of N.
     */
    public static double approxSqrt(double n) {
        if (n < 0) throw new IllegalArgumentException("Cannot calculate square root of a negative number.");
        if (n == 0 || n == 1) return n;

        double xCurrent = n;
        double xNext = 0;
        int iteration = 0;

        do {
            xNext = 0.5 * (xCurrent + n / xCurrent);

            if (Math.abs(xCurrent - xNext) < 1E-16) return xNext;

            xCurrent = xNext;
        } while (true);
    }

    /**
     * Calculates the horizontal distance (2D distance, ignoring Y-axis) between two locations.
     * @param location1 The first location.
     * @param location2 The second location.
     * @return The horizontal distance. Returns -1 if worlds are different.
     */
    public static double distance2D(Location location1, Location location2) {
        if (!location1.getWorld().equals(location2.getWorld())) return -1;

        double dx = location1.getX() - location2.getX();
        double dz = location1.getZ() - location2.getZ();


        return MathUtil.approxSqrt(dx * dx + dz * dz);
    }

    /**
     * Gets a safe {@link Location} to teleport a player to by finding the highest
     * suitable block and centering the location.
     * @param world The world to search within.
     * @param x The X coordinate.
     * @param z The Z coordinate.
     * @return A safe Location centered at X/Z, placed just above the highest block.
     */
    public static Location getSafeTeleportLocation(World world, int x, int z) {
        return new Location(world, x + 0.5, world.getHighestBlockYAt(x, z) + 1.0, z + 0.5);
    }
}
