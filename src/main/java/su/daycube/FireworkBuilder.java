package su.daycube;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class FireworkBuilder extends BaseItemBuilder<FireworkBuilder> {

    private static final Material STAR = Material.FIREWORK_STAR;

    private static final Material ROCKET = Material.FIREWORK_ROCKET;

    FireworkBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
        if (itemStack.getType() != STAR && itemStack.getType() != ROCKET)
            try {
                throw new Exception("FireworkBuilder requires the material to be a FIREWORK_STAR/FIREWORK_ROCKET!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    @NotNull
    @Contract("_ -> this")
    public FireworkBuilder effect(@NotNull FireworkEffect... effects) {
        return effect(Arrays.asList(effects));
    }

    @NotNull
    @Contract("_ -> this")
    public FireworkBuilder effect(@NotNull List<FireworkEffect> effects) {
        if (effects.isEmpty())
            return this;
        if (getItemStack().getType() == STAR) {
            FireworkEffectMeta effectMeta = (FireworkEffectMeta) getMeta();
            effectMeta.setEffect(effects.get(0));
            setMeta((ItemMeta) effectMeta);
            return this;
        }
        FireworkMeta fireworkMeta = (FireworkMeta) getMeta();
        fireworkMeta.addEffects(effects);
        setMeta((ItemMeta) fireworkMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public FireworkBuilder power(int power) {
        if (getItemStack().getType() == ROCKET) {
            FireworkMeta fireworkMeta = (FireworkMeta) getMeta();
            fireworkMeta.setPower(power);
            setMeta((ItemMeta) fireworkMeta);
        }
        return this;
    }
}
