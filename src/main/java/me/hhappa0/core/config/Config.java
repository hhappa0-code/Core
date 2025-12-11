package me.hhappa0.core.config;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A flexible utility class for managing specific YAML configuration files
 * within a plugin's data folder. It supports saving defaults and typed getters.
 */
public class Config {
    private final Plugin plugin;
    private final String fileName;
    private FileConfiguration config;
    private File configFile;

    /**
     * Initializes the configuration manager for a specific file name.
     * <p>The file will be created in the plugin's data folder if it doesn't exist.</p>
     * @param plugin The instance of your main plugin class.
     * @param fileName The name of the configuration file.
     */
    public Config(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), fileName);

        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();

        this.load();
    }

    /**
     * Loads the configuration file from disk. If a default file with the same name
     * exists inside the plugin JAR, it will be saved to the data folder first.
     */
    public void load() {
        if (!configFile.exists()) plugin.saveResource(fileName, false);

        this.reload();
    }

    /**
     * Reloads the configuration file from disk.
     */
    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    /**
     * Saves the currently loaded configuration to the disk.
     */
    public void save() {
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config " + fileName + ": " + e.getMessage());
        }
    }

    /**
     * Sets a value in the configuration.
     * <p>Note: This only sets the value in memory. You must call {@link Config#save()} afterward to write the change to the disk.</p>
     *
     * @param path The path where the value should be set.
     * @param value The value to set (e.g., String, int, List, Map, ItemStack, etc.).
     */
    public void set(String path, Object value) {
        this.config.set(path, value);
    }

    /**
     * Gets the underlying {@code FileConfiguration} instance.
     * @return The FileConfiguration instance.
     */
    public FileConfiguration getConfig() {
        return this.config;
    }

    /**
     * Gets a raw object from the configuration.
     * @param path The path to the value.
     * @return The object at the path, or {@code null} if not found.
     */
    public Object get(String path) {
        return this.get(path, null);
    }

    /**
     * Gets a raw object from the configuration.
     * @param path The path to the value.
     * @param defaultValue The default value if the {@code path} does not exist.
     * @return The object at the path, or the default value if not found.
     */
    public Object get(String path, Object defaultValue) {
        return this.config.get(path, defaultValue);
    }

    /**
     * Gets a String from the configuration.
     * @param path The path to the value.
     * @return The String at the path, or {@code null} if not found.
     */
    public String getString(String path) {
        return this.getString(path, null);
    }

    /**
     * Gets a String from the configuration.
     * @param path The path to the value.
     * @param defaultValue The default value if the {@code path} does not exist.
     * @return The String at the path, or the default value if not found.
     */
    public String getString(String path, String defaultValue) {
        return config.getString(path, defaultValue);
    }

    /**
     * Gets an Integer from the configuration.
     * @param path The path to the value.
     * @return The Integer at the path, or {@code 0} if not found.
     */
    public int getInt(String path) {
        return this.getInt(path, 0);
    }

    /**
     * Gets an Integer from the configuration.
     * @param path The path to the value.
     * @param defaultValue The default value if the {@code path} does not exist.
     * @return The Integer at the path, or the default value if not found.
     */
    public int getInt(String path, int defaultValue) {
        return config.getInt(path, defaultValue);
    }

    /**
     * Gets a Boolean from the configuration.
     * @param path The path to the value.
     * @return The Boolean at the path, or {@code false} if not found.
     */
    public boolean getBoolean(String path) {
        return this.getBoolean(path, false);
    }

    /**
     * Gets a Boolean from the configuration.
     * @param path The path to the value.
     * @param defaultValue The default value if the {@code path} does not exist.
     * @return The Boolean at the path, or the default value if not found.
     */
    public boolean getBoolean(String path, boolean defaultValue) {
        return config.getBoolean(path, defaultValue);
    }

    /**
     * Gets a Double from the configuration.
     * @param path The path to the value.
     * @return The Double at the path, or {@code 0.0} if not found.
     */
    public double getDouble(String path) {
        return this.getDouble(path, 0.0);
    }

    /**
     * Gets a Double from the configuration.
     * @param path The path to the value.
     * @param defaultValue The default value if the {@code path} does not exist.
     * @return The Double at the path, or the default value if not found.
     */
    public double getDouble(String path, double defaultValue) {
        return config.getDouble(path, defaultValue);
    }

    /**
     * Gets a Long from the configuration.
     * @param path The path to the value.
     * @return The Long at the path, or {@code 0L} if not found.
     */
    public long getLong(String path) {
        return this.getLong(path, 0L);
    }

    /**
     * Gets a Long from the configuration.
     * @param path The path to the value.
     * @param defaultValue The default value if the {@code path} does not exist.
     * @return The Long at the path, or the default value if not found.
     */
    public long getLong(String path, long defaultValue) {
        return config.getLong(path, defaultValue);
    }

    /**
     * Gets a Short from the configuration.
     * @param path The path to the value.
     * @return The Short at the path, or {@code 0} if not found.
     */
    public short getShort(String path) {
        return this.getShort(path, (short) 0);
    }

    /**
     * Gets a Short from the configuration.
     *
     * @param path The path to the value.
     * @param defaultValue The default value if the path does not exist.
     * @return The Short at the path, or the default value.
     */
    public short getShort(String path, short defaultValue) {
        return (short) config.getInt(path, (int) defaultValue);
    }

    /**
     * Gets a {@link Map} representing a configuration section.
     * @param path The path to the configuration section.
     * @return The map containing the section data, or an empty map if not found.
     */
    public Map<?, ?> getMap(String path) {
        ConfigurationSection section = this.config.getConfigurationSection(path);
        if (section == null) return Map.of();

        return (Map<?, ?>) section.getValues(true);
    }

    /**
     * Gets a {@link List<String>} from the configuration.
     * @param path The path to the list.
     * @return The list of strings, or an empty list if not found.
     */
    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    /**
     * Gets a {@link List<Integer>} from the configuration.
     * @param path The path to the list.
     * @return The list of integers, or an empty list if not found.
     */
    public List<Integer> getIntList(String path) {
        return this.config.getIntegerList(path);
    }

    /**
     * Gets a {@link List<Boolean>} from the configuration.
     * @param path The path to the list.
     * @return The list of booleans, or an empty list if not found.
     */
    public List<Boolean> getBooleanList(String path) {
        return this.config.getBooleanList(path);
    }

    /**
     * Gets a {@link List<Double>} from the configuration.
     * @param path The path to the list.
     * @return The list of doubles, or an empty list if not found.
     */
    public List<Double> getDoubleList(String path) {
        return this.config.getDoubleList(path);
    }

    /**
     * Gets a {@link List<Long>} from the configuration.
     * @param path The path to the list.
     * @return The list of longs, or an empty list if not found.
     */
    public List<Long> getLongList(String path) {
        return this.config.getLongList(path);
    }

    /**
     * Gets a {@link List<Short>} from the configuration.
     * @param path The path to the list.
     * @return The list of shorts, or an empty list if not found.
     */
    public List<Short> getShortList(String path) {
        return this.config.getShortList(path);
    }

    /**
     * Gets a generic list {@link List} from the configuration.
     * @param path The path to the list.
     * @return The List at the path, or an empty list if not found.
     */
    public List<?> getList(String path) {
        return this.config.getList(path, List.of());
    }

    /**
     * Gets a {@link List<Map>} from the configuration.
     * @param path The path to the list of maps.
     * @return The list of maps, or an empty list if not found or the path is not a list.
     */
    public List<Map<?, ?>> getMapList(String path) {
        return this.config.getMapList(path);
    }

    /**
     * Gets a Bukkit {@link Color} from the configuration.
     * @param path The path to the color value.
     * @return The Color at the path, or {@code null} if not found.
     */
    public Color getColor(String path) {
        return this.getColor(path, null);
    }

    /**
     * Gets a Bukkit {@link Color} from the configuration with a default value.
     *
     * @param path The path to the color value.
     * @param defaultValue The default Color to return if the path does not exist.
     * @return The Color at the path, or the default value.
     */
    public Color getColor(String path, Color defaultValue) {
        return this.config.getColor(path, defaultValue);
    }

    /**
     * Gets an {@link ItemStack} from the configuration.
     * @param path The path to the item stack data.
     * @return The ItemStack at the path, or {@code null} if not found.
     */
    public ItemStack getItemStack(String path) {
        return this.getItemStack(path, null);
    }

    /**
     * Gets an {@link ItemStack} from the configuration with a default value.
     *
     * @param path The path to the item stack data.
     * @param defaultValue The default ItemStack to return if the path does not exist.
     * @return The ItemStack at the path, or the default value.
     */
    public ItemStack getItemStack(String path, ItemStack defaultValue) {
        return this.config.getItemStack(path, defaultValue);
    }

    /**
     * Gets a Bukkit {@link Location} from the configuration.
     * @param path The path to the location data.
     * @return The Location at the path, or {@code null} if not found.
     */
    public Location getLocation(String path) {
        return this.getLocation(path, null);
    }

    /**
     * Gets a Bukkit {@link Location} from the configuration with a default value.
     *
     * @param path The path to the location data.
     * @param defaultValue The default Location to return if the path does not exist.
     * @return The Location at the path, or the default value.
     */
    public Location getLocation(String path, Location defaultValue) {
        return this.config.getLocation(path, defaultValue);
    }

    /**
     * Gets a Bukkit {@link OfflinePlayer} from the configuration.
     * @param path The path to the offline player data.
     * @return The OfflinePlayer at the path, or {@code null} if not found.
     */
    public OfflinePlayer getOfflinePlayer(String path) {
        return this.getOfflinePlayer(path, null);
    }

    /**
     * Gets a Bukkit {@link OfflinePlayer} from the configuration with a default value.
     *
     * @param path The path to the offline player data.
     * @param defaultValue The default OfflinePlayer to return if the path does not exist.
     * @return The OfflinePlayer at the path, or the default value.
     */
    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer defaultValue) {
        return this.config.getOfflinePlayer(path, defaultValue);
    }

    /**
     * Gets a Bukkit {@link Vector} from the configuration.
     * @param path The path to the vector data.
     * @return The Vector at the path, or {@code null} if not found.
     */
    public Vector getVector(String path) {
        return this.getVector(path, null);
    }

    /**
     * Gets a Bukkit {@link Vector} from the configuration with a default value.
     *
     * @param path The path to the vector data.
     * @param defaultValue The default Vector to return if the path does not exist.
     * @return The Vector at the path, or the default value.
     */
    public Vector getVector(String path, Vector defaultValue) {
        return this.config.getVector(path, defaultValue);
    }
}
