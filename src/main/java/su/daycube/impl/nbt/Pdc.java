package su.daycube.impl.nbt;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Pdc implements NbtWrapper {

    private static final Plugin PLUGIN = (Plugin) JavaPlugin.getProvidingPlugin(Pdc.class);

    public ItemStack setString(@NotNull ItemStack itemStack, String key, String value) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return itemStack;
        meta.getPersistentDataContainer().set(new NamespacedKey(PLUGIN, key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack removeTag(@NotNull ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return itemStack;
        meta.getPersistentDataContainer().remove(new NamespacedKey(PLUGIN, key));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack setBoolean(@NotNull ItemStack itemStack, String key, boolean value) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return itemStack;
        meta.getPersistentDataContainer().set(new NamespacedKey(PLUGIN, key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Nullable
    public String getString(@NotNull ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return null;
        return (String) meta.getPersistentDataContainer().get(new NamespacedKey(PLUGIN, key), PersistentDataType.STRING);
    }
}
