package su.daycube.impl.pdc;

import com.google.common.base.Preconditions;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A wrapper for any persistent data container, providing simpler set... and get... methods
 */
public class PdcBuilder<B extends PdcBuilder<B>> { // ugly wrapper class because pdc bad

    private static final Plugin PLUGIN = JavaPlugin.getProvidingPlugin(PdcBuilder.class);

    private final ItemStack itemStack;

    public PdcBuilder(@NotNull ItemStack itemStack) {
        Preconditions.checkNotNull(itemStack, "Item can't be null!");
        this.itemStack = itemStack;
    }

    @NotNull
    @Contract("_ -> new")
    public static PdcBuilder from(@NotNull ItemStack itemStack) {
        return new PdcBuilder(itemStack);
    }

    public static PersistentDataContainer get(@NotNull ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return null;
        return meta.getPersistentDataContainer();
    }

    @NotNull
    @Contract("_ -> this")
    public B setString(@NotNull String key, @NotNull String value) {
        set(key, PersistentDataType.STRING, value);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B setInteger(@NotNull String key, @NotNull int value) {
        set(key, PersistentDataType.INTEGER, value);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B setDouble(@NotNull String key, @NotNull double value) {
        set(key, PersistentDataType.DOUBLE, value);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B setFloat(@NotNull String key, @NotNull float value) {
        set(key, PersistentDataType.FLOAT, value);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B setLong(@NotNull String key, @NotNull long value) {
        set(key, PersistentDataType.LONG, value);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B setShort(@NotNull String key, @NotNull short value) {
        set(key, PersistentDataType.SHORT, value);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B setByte(@NotNull String key, @NotNull byte value) {
        set(key, PersistentDataType.BYTE, value);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B setBoolean(@NotNull String key, @NotNull boolean value) {
        set(key, PersistentDataType.BYTE, (byte) (value ? 1 : 0));
        return (B) this;
    }

    public String getString(@NotNull String key) {
        return get(key, PersistentDataType.STRING);
    }

    public int getInteger(@NotNull String key) {
        return get(key, PersistentDataType.INTEGER);
    }

    public double getDouble(@NotNull String key) {
        return get(key, PersistentDataType.DOUBLE);
    }

    public float getFloat(@NotNull String key) {
        return get(key, PersistentDataType.FLOAT);
    }

    public long getLong(@NotNull String key) {
        return get(key, PersistentDataType.LONG);
    }

    public short getShort(@NotNull String key) {
        return get(key, PersistentDataType.SHORT);
    }

    public byte getByte(@NotNull String key) {
        return get(key, PersistentDataType.BYTE);
    }

    public boolean getBoolean(@NotNull String key) {
        return get(key, PersistentDataType.BYTE) == 1;
    }

    @Contract("_ -> this")
    public B remove(@NotNull String key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return (B) this;
        meta.getPersistentDataContainer().remove(createKey(key));
        itemStack.setItemMeta(meta);
        return (B) this;
    }

    public boolean has(@NotNull String key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return false;
        return meta.getPersistentDataContainer().getKeys().contains(createKey(key));
    }

    protected NamespacedKey createKey(@NotNull String name) {
        return new NamespacedKey(PLUGIN, name);
    }

    protected <T> void set(@NotNull String key, @NotNull PersistentDataType<T, T> type, @NotNull T value) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return;
        meta.getPersistentDataContainer().set(createKey(key), type, value);
        itemStack.setItemMeta(meta);
    }

    private <T> T get(@NotNull String key, @NotNull PersistentDataType<T, T> type) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return null;
        return meta.getPersistentDataContainer().get(createKey(key), type);
    }

    @NotNull
    public ItemStack build() {
        return this.itemStack;
    }
}
