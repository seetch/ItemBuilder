package su.daycube.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;

public final class SkullUtil {

    private static final Material SKULL = getSkullMaterial();

    private static final Gson GSON = new Gson();

    private static Material getSkullMaterial() {
        return Material.PLAYER_HEAD;
    }

    public static ItemStack skull() {
        return new ItemStack(SKULL);
    }

    public static boolean isPlayerSkull(@NotNull ItemStack item) {
        return (item.getType() == SKULL);
    }

    public static String getSkinUrl(String base64Texture) {
        String decoded = new String(Base64.getDecoder().decode(base64Texture));
        JsonObject object = GSON.fromJson(decoded, JsonObject.class);
        JsonElement textures = object.get("textures");
        if (textures == null)
            return null;
        JsonElement skin = textures.getAsJsonObject().get("SKIN");
        if (skin == null)
            return null;
        JsonElement url = skin.getAsJsonObject().get("url");
        return (url == null) ? null : url.getAsString();
    }
}
