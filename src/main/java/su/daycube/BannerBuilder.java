package su.daycube;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public final class BannerBuilder extends BaseItemBuilder<BannerBuilder> {

    private static final Material DEFAULT_BANNER = Material.WHITE_BANNER;

    private static final EnumSet<Material> BANNERS = EnumSet.copyOf(Tag.BANNERS.getValues());

    BannerBuilder() {
        super(new ItemStack(DEFAULT_BANNER));
    }

    BannerBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
        if (!BANNERS.contains(itemStack.getType()))
            try {
                throw new Exception("BannerBuilder requires the material to be a banner!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    @NotNull
    @Contract("_ -> this")
    public BannerBuilder baseColor(@NotNull DyeColor color) {
        BannerMeta bannerMeta = (BannerMeta) getMeta();
        bannerMeta.setBaseColor(color);
        setMeta((ItemMeta) bannerMeta);
        return this;
    }

    @NotNull
    @Contract("_, _ -> this")
    public BannerBuilder pattern(@NotNull DyeColor color, @NotNull PatternType pattern) {
        BannerMeta bannerMeta = (BannerMeta) getMeta();
        bannerMeta.addPattern(new Pattern(color, pattern));
        setMeta((ItemMeta) bannerMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public BannerBuilder pattern(@NotNull Pattern... pattern) {
        return pattern(Arrays.asList(pattern));
    }

    @NotNull
    @Contract("_ -> this")
    public BannerBuilder pattern(@NotNull List<Pattern> patterns) {
        BannerMeta bannerMeta = (BannerMeta) getMeta();
        for (Pattern it : patterns)
            bannerMeta.addPattern(it);
        setMeta((ItemMeta) bannerMeta);
        return this;
    }

    @NotNull
    @Contract("_, _, _ -> this")
    public BannerBuilder pattern(int index, @NotNull DyeColor color, @NotNull PatternType pattern) {
        return pattern(index, new Pattern(color, pattern));
    }

    @NotNull
    @Contract("_, _ -> this")
    public BannerBuilder pattern(int index, @NotNull Pattern pattern) {
        BannerMeta bannerMeta = (BannerMeta) getMeta();
        bannerMeta.setPattern(index, pattern);
        setMeta((ItemMeta) bannerMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public BannerBuilder setPatterns(@NotNull List<Pattern> patterns) {
        BannerMeta bannerMeta = (BannerMeta) getMeta();
        bannerMeta.setPatterns(patterns);
        setMeta((ItemMeta) bannerMeta);
        return this;
    }
}
