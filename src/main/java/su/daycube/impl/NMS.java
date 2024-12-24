package su.daycube.impl;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

/**
 * NMS Util for {@link net.minecraft.server} related actions
 * Support 1.7.10 ~ 1.21.3
 */
public class NMS {


    /**
     * Just call this method and automatically run static{} to init
     */
    public static void init() {
    }

    /**
     * @return Simple version number like 1.20.6
     */
    private static String getVersionNumber() {
        if (simpleVersionNumber != null) return simpleVersionNumber;
        String strVer = Bukkit.getServer().getBukkitVersion();
        strVer = strVer.substring(0, strVer.indexOf("-"));
        return simpleVersionNumber = strVer;
    }

    private static String simpleVersionNumber = null;
    private static String rVersion = null;
    private static int subVer = -1;
    private static int subRVer = -1;

    public static final Map<String, String> rVerMap = new HashMap<String, String>() {{
        put("1.20.5", "1_20_R4");
        put("1.20.6", "1_20_R4");
        put("1.21", "1_21_R1");
        put("1.21.1", "1_21_R1");
        put("1.21.2", "1_21_R2");
        put("1.21.3", "1_21_R2");
        put("1.21.4", "1_21_R3");
    }};

    public static final boolean VER_1_17, VER_1_20_R4, VER_1_21_R2;

    /**
     * Using spigot mapping (paper 1.20.4-) or Mojang mapping (paper 1.20.5+).
     */
    public static final boolean SP = Double.parseDouble(getVersionNumber().split("\\.", 2)[1]) >= 20.5 && Package.getPackage("com.destroystokyo.paper") == null;

    /**
     * get NMS version string (e.g. 1_12_R1)
     */
    public static String getMcVer() {
        if (rVersion != null) return rVersion;

        String versionString = Bukkit.getServer().getClass().getPackage().getName(); // e.g. org.bukkit.craftbukkit.v1_20_R2.CraftServer
        if (versionString.contains("R")) {
            return rVersion = versionString.split("\\.")[3].substring(1); // -> 1_20_R2
        } else if ((rVersion = rVerMap.get(getVersionNumber())) != null) {
            return rVersion;
        } else {
            throw new RuntimeException("Can't get CraftBukkit revision number! Only got '" + versionString + "' instead.");
        }
    }

    /**
     * Get big version number (v1_12_R1 -> 12)
     */
    public static int getSubVer() {
        return subVer == -1 ? subVer = Integer.parseInt(getMcVer().split("_")[1]) : subVer;
    }

    /**
     * Get revision number (v1_12_R1 -> 1)
     */
    public static int getSubRVer() {
        return subRVer == -1 ? subRVer = Integer.parseInt(getMcVer().split("_")[2].substring(1)) : subRVer;
    }

    // ClassSimpleName - Class
    private static final Map<String, Class<?>> mcClassMap = new HashMap<>();

    static {
        int subVer = getSubVer();
        int subRVer = getSubRVer();

        VER_1_17 = subVer >= 17;
        VER_1_20_R4 = subVer > 20 || (subVer == 20 && subRVer >= 4);
        VER_1_21_R2 = subVer > 21 || (subVer == 21 && subRVer >= 2);
    }

    private static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getCraftClass(String name) {
        return getClass("org.bukkit.craftbukkit.v" + getMcVer() + "." + name);
    }

    public static Class<?> getMcClass(String newName, String legacyName) {
        return getSubVer() <= 16 ? getMcClassLegacy(legacyName) : getMcClassNew(newName);
    }

    public static Class<?> getMcClassNew(String name) {
        Class<?> clazz = mcClassMap.get(name);
        if (clazz == null) {
            clazz = getClass("net.minecraft." + name);
            mcClassMap.put(name, clazz);
        }
        return clazz;
    }

    // "Legacy" refers to version < 1.17, because the path of the corresponding class of NMS has been greatly changed after 1.17.
    public static Class<?> getMcClassLegacy(String name) {
        Class<?> clazz = mcClassMap.get(name);
        if (clazz == null) {
            clazz = getClass("net.minecraft.server.v" + getMcVer() + "." + name);
            mcClassMap.put(name, clazz);
        }
        return clazz;
    }
}
