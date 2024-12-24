package su.daycube.impl;

import com.google.common.primitives.Ints;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionHelper {

    private static final String NMS_VERSION = getNmsVersion();

    private static final int V1_11 = 1110;
    private static final int V1_13 = 1130;
    private static final int V1_14 = 1140;
    private static final int V1_16_5 = 1165;
    private static final int V1_12_1 = 1121;
    private static final int V1_20_1 = 1201;

    private static final int CURRENT_VERSION = getCurrentVersion();

    private static final boolean IS_PAPER = checkPaper();
    public static final boolean IS_COMPONENT_LEGACY = false;
    public static final boolean IS_ITEM_LEGACY = false;
    public static final boolean IS_UNBREAKABLE_LEGACY = false;
    public static final boolean IS_PDC_VERSION = true;
    public static final boolean IS_SKULL_OWNER_LEGACY = false;
    public static final boolean IS_CUSTOM_MODEL_DATA = true;
    public static final boolean IS_PLAYER_PROFILE_API = true;

    private static boolean checkPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    private static int getCurrentVersion() {
        Matcher matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?").matcher(Bukkit.getBukkitVersion());
        StringBuilder stringBuilder = new StringBuilder();
        if (matcher.find()) {
            stringBuilder.append(matcher.group("version").replace(".", ""));
            String patch = matcher.group("patch");
            if (patch == null) {
                stringBuilder.append("0");
            } else {
                stringBuilder.append(patch.replace(".", ""));
            }
        }
        Integer version = Ints.tryParse(stringBuilder.toString());
        if (version == null)
            try {
                throw new Exception("Could not retrieve server version!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return version.intValue();
    }

    private static String getNmsVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        return version.substring(version.lastIndexOf('.') + 1);
    }

    public static Class<?> craftClass(@NotNull String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + "." + name);
    }
}
