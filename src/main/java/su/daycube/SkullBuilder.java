package su.daycube;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import su.daycube.impl.SkullUtil;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public final class SkullBuilder extends BaseItemBuilder<SkullBuilder> {

    private static final Field PROFILE_FIELD;

    static {
        Field field;
        try {
            SkullMeta skullMeta = (SkullMeta) SkullUtil.skull().getItemMeta();
            field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            field = null;
        }
        PROFILE_FIELD = field;
    }

    SkullBuilder() {
        super(SkullUtil.skull());
    }

    SkullBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
        if (!SkullUtil.isPlayerSkull(itemStack))
            try {
                throw new Exception("SkullBuilder requires the material to be a PLAYER_HEAD/SKULL_ITEM!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    @NotNull
    @Contract("_, _ -> this")
    public SkullBuilder texture(@NotNull String texture, @NotNull UUID profileId) {
        if (!SkullUtil.isPlayerSkull(getItemStack()))
            return this;
        String textureUrl = SkullUtil.getSkinUrl(texture);
        if (textureUrl == null)
            return this;
        SkullMeta skullMeta = (SkullMeta) getMeta();
        PlayerProfile playerProfile = Bukkit.createPlayerProfile(profileId, "");
        PlayerTextures textures = playerProfile.getTextures();
        try {
            textures.setSkin(new URL(textureUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return this;
        }
        playerProfile.setTextures(textures);
        skullMeta.setOwnerProfile((PlayerProfile) playerProfile);
        setMeta((ItemMeta) skullMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public SkullBuilder texture(@NotNull String texture) {
        return texture(texture, UUID.randomUUID());
    }

    @NotNull
    @Contract("_ -> this")
    public SkullBuilder owner(@NotNull OfflinePlayer player) {
        if (!SkullUtil.isPlayerSkull(getItemStack()))
            return this;
        SkullMeta skullMeta = (SkullMeta) getMeta();
        skullMeta.setOwningPlayer(player);
        setMeta((ItemMeta) skullMeta);
        return this;
    }
}
