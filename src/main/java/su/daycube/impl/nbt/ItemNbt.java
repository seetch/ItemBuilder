package su.daycube.impl.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ItemNbt {

    private static final NbtWrapper nbt = selectNbt();

    public static ItemStack setString(@NotNull ItemStack itemStack, @NotNull String key, @NotNull String value) {
        return nbt.setString(itemStack, key, value);
    }

    public static String getString(@NotNull ItemStack itemStack, @NotNull String key) {
        return nbt.getString(itemStack, key);
    }

    public static ItemStack setBoolean(@NotNull ItemStack itemStack, @NotNull String key, boolean value) {
        return nbt.setBoolean(itemStack, key, value);
    }

    public static ItemStack removeTag(@NotNull ItemStack itemStack, @NotNull String key) {
        return nbt.removeTag(itemStack, key);
    }

    private static NbtWrapper selectNbt() {
        return new Pdc();
    }
}
