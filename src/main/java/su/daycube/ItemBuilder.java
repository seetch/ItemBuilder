package su.daycube;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemBuilder extends BaseItemBuilder<ItemBuilder> {

    ItemBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
    }

    @NotNull
    @Contract("_ -> new")
    public static ItemBuilder from(@NotNull ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    @NotNull
    @Contract("_ -> new")
    public static ItemBuilder from(@NotNull Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    @NotNull
    @Contract(" -> new")
    public static BannerBuilder banner() {
        return new BannerBuilder();
    }

    @NotNull
    @Contract("_ -> new")
    public static BannerBuilder banner(@NotNull ItemStack itemStack) {
        return new BannerBuilder(itemStack);
    }

    @NotNull
    @Contract("_ -> new")
    public static BookBuilder book(@NotNull ItemStack itemStack) {
        return new BookBuilder(itemStack);
    }

    @NotNull
    @Contract(" -> new")
    public static FireworkBuilder firework() {
        return new FireworkBuilder(new ItemStack(Material.FIREWORK_ROCKET));
    }

    @NotNull
    @Contract("_ -> new")
    public static FireworkBuilder firework(@NotNull ItemStack itemStack) {
        return new FireworkBuilder(itemStack);
    }

    @NotNull
    @Contract(" -> new")
    public static MapBuilder map() {
        return new MapBuilder();
    }

    @NotNull
    @Contract("_ -> new")
    public static MapBuilder map(@NotNull ItemStack itemStack) {
        return new MapBuilder(itemStack);
    }

    @NotNull
    @Contract(" -> new")
    public static SkullBuilder skull() {
        return new SkullBuilder();
    }

    @NotNull
    @Contract("_ -> new")
    public static SkullBuilder skull(@NotNull ItemStack itemStack) {
        return new SkullBuilder(itemStack);
    }

    @NotNull
    @Contract(" -> new")
    public static FireworkBuilder star() {
        return new FireworkBuilder(new ItemStack(Material.FIREWORK_STAR));
    }

    @NotNull
    @Contract("_ -> new")
    public static FireworkBuilder star(@NotNull ItemStack itemStack) {
        return new FireworkBuilder(itemStack);
    }
}
