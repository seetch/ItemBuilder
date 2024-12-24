package su.daycube;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapBuilder extends BaseItemBuilder<MapBuilder> {

    private static final Material MAP = Material.MAP;

    MapBuilder() {
        super(new ItemStack(MAP));
    }

    MapBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
        if (itemStack.getType() != MAP)
            try {
                throw new Exception("MapBuilder requires the material to be a MAP!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    @NotNull
    @Contract("_ -> this")
    public MapBuilder color(@Nullable Color color) {
        MapMeta mapMeta = (MapMeta) getMeta();
        mapMeta.setColor(color);
        setMeta((ItemMeta) mapMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public MapBuilder locationName(@Nullable String name) {
        MapMeta mapMeta = (MapMeta) getMeta();
        mapMeta.setLocationName(name);
        setMeta((ItemMeta) mapMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public MapBuilder scaling(boolean scaling) {
        MapMeta mapMeta = (MapMeta) getMeta();
        mapMeta.setScaling(scaling);
        setMeta((ItemMeta) mapMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public MapBuilder view(@NotNull MapView view) {
        MapMeta mapMeta = (MapMeta) getMeta();
        mapMeta.setMapView(view);
        setMeta((ItemMeta) mapMeta);
        return this;
    }
}
