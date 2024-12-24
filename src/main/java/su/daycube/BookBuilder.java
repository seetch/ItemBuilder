package su.daycube;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.daycube.impl.Legacy;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class BookBuilder extends BaseItemBuilder<BookBuilder> {

    private static final EnumSet<Material> BOOKS = EnumSet.of(Material.WRITABLE_BOOK, Material.WRITTEN_BOOK);

    BookBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
        if (!BOOKS.contains(itemStack.getType()))
            try {
                throw new Exception("BookBuilder requires the material to be a WRITABLE_BOOK/WRITTEN_BOOK!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    @NotNull
    @Contract("_ -> this")
    public BookBuilder author(@Nullable Component author) {
        BookMeta bookMeta = (BookMeta) getMeta();
        if (author == null) {
            bookMeta.setAuthor(null);
            setMeta((ItemMeta) bookMeta);
            return this;
        }
        bookMeta.setAuthor(Legacy.SERIALIZER.serialize(author));
        setMeta((ItemMeta) bookMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public BookBuilder generation(@Nullable BookMeta.Generation generation) {
        BookMeta bookMeta = (BookMeta) getMeta();
        bookMeta.setGeneration(generation);
        setMeta((ItemMeta) bookMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public BookBuilder page(@NotNull Component... pages) {
        return page(Arrays.asList(pages));
    }

    @NotNull
    @Contract("_ -> this")
    public BookBuilder page(@NotNull List<Component> pages) {
        BookMeta bookMeta = (BookMeta) getMeta();
        for (Component page : pages) {
            bookMeta.addPage(new String[]{Legacy.SERIALIZER.serialize(page)});
        }
        setMeta((ItemMeta) bookMeta);
        return this;
    }

    @NotNull
    @Contract("_, _ -> this")
    public BookBuilder page(int page, @NotNull Component data) {
        BookMeta bookMeta = (BookMeta) getMeta();
        bookMeta.setPage(page, Legacy.SERIALIZER.serialize(data));
        setMeta((ItemMeta) bookMeta);
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public BookBuilder title(@Nullable Component title) {
        BookMeta bookMeta = (BookMeta) getMeta();
        if (title == null) {
            bookMeta.setTitle(null);
            setMeta((ItemMeta) bookMeta);
            return this;
        }
        bookMeta.setTitle(Legacy.SERIALIZER.serialize(title));
        setMeta((ItemMeta) bookMeta);
        return this;
    }
}
