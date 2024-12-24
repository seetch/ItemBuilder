package su.daycube;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.daycube.impl.NMS;
import su.daycube.impl.pdc.PdcBuilder;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class BaseItemBuilder<B extends BaseItemBuilder<B>> {

    private static final EnumSet<Material> LEATHER_ARMOR = EnumSet.of(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS);

    private static final GsonComponentSerializer GSON = GsonComponentSerializer.gson();

    private static final Field DISPLAY_NAME_FIELD;
    private static final Field LORE_FIELD;

    private ItemStack itemStack;
    private ItemMeta meta;

    static {
        try {
            Class<?> metaClass = NMS.getCraftClass("inventory.CraftMetaItem");
            DISPLAY_NAME_FIELD = metaClass.getDeclaredField("displayName");
            DISPLAY_NAME_FIELD.setAccessible(true);
            LORE_FIELD = metaClass.getDeclaredField("lore");
            LORE_FIELD.setAccessible(true);
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
            try {
                throw new Exception("Could not retrieve displayName nor lore field for ItemBuilder.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected BaseItemBuilder(@NotNull ItemStack itemStack) {
        Preconditions.checkNotNull(itemStack, "Item can't be null!");
        this.itemStack = itemStack;
        this.meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    @NotNull
    @Contract("_ -> this")
    public B name(@NotNull Component name) {
        if (this.meta == null)
            return (B) this;
        try {
            DISPLAY_NAME_FIELD.set(this.meta, GSON.serialize(name));
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B amount(int amount) {
        this.itemStack.setAmount(amount);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B lore(@Nullable Component... lore) {
        return lore(Arrays.asList(lore));
    }

    @NotNull
    @Contract("_ -> this")
    public B lore(@NotNull List<Component> lore) {
        if (this.meta == null)
            return (B) this;
        Objects.requireNonNull(GSON);
        List<String> jsonLore = lore.stream().filter(Objects::nonNull).map(GSON::serialize).collect(Collectors.toList());
        try {
            LORE_FIELD.set(this.meta, jsonLore);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B lore(@NotNull Consumer<List<Component>> lore) {
        List<Component> components;
        if (this.meta == null)
            return (B) this;
        try {
            List<String> jsonLore = (List<String>) LORE_FIELD.get(this.meta);
            Objects.requireNonNull(GSON);
            components = (jsonLore == null) ? new ArrayList<>() : jsonLore.stream().map(GSON::deserialize).collect(Collectors.toList());
        } catch (IllegalAccessException exception) {
            components = new ArrayList<>();
            exception.printStackTrace();
        }
        lore.accept(components);
        return lore(components);
    }

    @NotNull
    @Contract("_, _, _ -> this")
    public B enchant(@NotNull Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        this.meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return (B) this;
    }

    @NotNull
    @Contract("_, _ -> this")
    public B enchant(@NotNull Enchantment enchantment, int level) {
        return enchant(enchantment, level, true);
    }

    @NotNull
    @Contract("_ -> this")
    public B enchant(@NotNull Enchantment enchantment) {
        return enchant(enchantment, 1, true);
    }

    @NotNull
    @Contract("_, _ -> this")
    public B enchant(@NotNull Map<Enchantment, Integer> enchantments, boolean ignoreLevelRestriction) {
        enchantments.forEach((enchantment, level) -> enchant(enchantment, level.intValue(), ignoreLevelRestriction));
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B enchant(@NotNull Map<Enchantment, Integer> enchantments) {
        return enchant(enchantments, true);
    }

    @NotNull
    @Contract("_ -> this")
    public B disenchant(@NotNull Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B flags(@NotNull ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return (B) this;
    }

    @NotNull
    @Contract(" -> this")
    public B unbreakable() {
        return unbreakable(true);
    }

    @NotNull
    @Contract("_ -> this")
    public B unbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return (B) this;
    }

    @NotNull
    @Contract(" -> this")
    public B glow() {
        return glow(true);
    }

    @NotNull
    @Contract("_ -> this")
    public B glow(boolean glow) {
        if (glow) {
            this.meta.addEnchant(Enchantment.LURE, 1, false);
            this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return (B) this;
        }
        for (Enchantment enchantment : this.meta.getEnchants().keySet())
            this.meta.removeEnchant(enchantment);
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B pdc(@NotNull Consumer<PersistentDataContainer> consumer) {
        consumer.accept(this.meta.getPersistentDataContainer());
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B model(int modelData) {
        this.meta.setCustomModelData(Integer.valueOf(modelData));
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B color(@NotNull Color color) {
        if (LEATHER_ARMOR.contains(this.itemStack.getType())) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) getMeta();
            leatherArmorMeta.setColor(color);
            setMeta((ItemMeta) leatherArmorMeta);
        }
        return (B) this;
    }

    @NotNull
    @Contract("_, _ -> this")
    public B setPdc(@NotNull String key, @NotNull String value) {
        this.itemStack.setItemMeta(this.meta);
        this.itemStack = PdcBuilder.from(this.itemStack).setString(key, value).build();
        this.meta = this.itemStack.getItemMeta();
        return (B) this;
    }

    @NotNull
    @Contract("_, _ -> this")
    public B setPdc(@NotNull String key, boolean value) {
        this.itemStack.setItemMeta(this.meta);
        this.itemStack = PdcBuilder.from(this.itemStack).setBoolean(key, value).build();
        this.meta = this.itemStack.getItemMeta();
        return (B) this;
    }

    @NotNull
    @Contract("_ -> this")
    public B removePdc(@NotNull String key) {
        this.itemStack.setItemMeta(this.meta);
        this.itemStack = PdcBuilder.from(this.itemStack).remove(key).build();
        this.meta = this.itemStack.getItemMeta();
        return (B) this;
    }

    @NotNull
    public ItemStack build() {
        this.itemStack.setItemMeta(this.meta);
        return this.itemStack;
    }

    @NotNull
    protected ItemStack getItemStack() {
        return this.itemStack;
    }

    protected void setItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @NotNull
    protected ItemMeta getMeta() {
        return this.meta;
    }

    protected void setMeta(@NotNull ItemMeta meta) {
        this.meta = meta;
    }

    public B setName(@NotNull String name) {
        getMeta().setDisplayName(name);
        return (B) this;
    }

    public B setAmount(int amount) {
        getItemStack().setAmount(amount);
        return (B) this;
    }

    public B addLore(@NotNull String... lore) {
        return addLore(Arrays.asList(lore));
    }

    public B addLore(@NotNull List<String> lore) {
        List<String> newLore = getMeta().hasLore() ? getMeta().getLore() : new ArrayList<>();
        newLore.addAll(lore);
        return setLore(newLore);
    }

    public B setLore(@NotNull String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public B setLore(@NotNull List<String> lore) {
        getMeta().setLore(lore);
        return (B) this;
    }

    public B addEnchantment(@NotNull Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        getMeta().addEnchant(enchantment, level, ignoreLevelRestriction);
        return (B) this;
    }

    public B addEnchantment(@NotNull Enchantment enchantment, int level) {
        return addEnchantment(enchantment, level, true);
    }

    public B addEnchantment(@NotNull Enchantment enchantment) {
        return addEnchantment(enchantment, 1, true);
    }

    public B removeEnchantment(@NotNull Enchantment enchantment) {
        getItemStack().removeEnchantment(enchantment);
        return (B) this;
    }

    public B addItemFlags(@NotNull ItemFlag... flags) {
        getMeta().addItemFlags(flags);
        return (B) this;
    }

    public B setUnbreakable(boolean unbreakable) {
        return unbreakable(unbreakable);
    }
}
