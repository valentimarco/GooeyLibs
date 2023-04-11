package ca.landonjw.gooeylibs2.fabric.cookbook.synchronization;

import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.fabric.cookbook.GooeyPageRegistration;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class SynchronizedCommand
{
    private static Page page = page();

    public static LiteralArgumentBuilder<CommandSourceStack> command()
    {
        return GooeyPageRegistration.pageCommand("synchronized", () -> page);
    }

    private static Page page() {
        return new SynchronizedPage();
    }
}
