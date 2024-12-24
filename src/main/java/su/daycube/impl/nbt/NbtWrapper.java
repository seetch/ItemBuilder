package su.daycube.impl.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NbtWrapper {

    ItemStack setString(@NotNull ItemStack paramItemStack, String paramString1, String paramString2);

    ItemStack removeTag(@NotNull ItemStack paramItemStack, String paramString);

    ItemStack setBoolean(@NotNull ItemStack paramItemStack, String paramString, boolean paramBoolean);

    @Nullable
    String getString(@NotNull ItemStack paramItemStack, String paramString);
}
