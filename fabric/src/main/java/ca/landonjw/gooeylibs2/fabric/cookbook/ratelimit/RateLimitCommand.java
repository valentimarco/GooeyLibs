package ca.landonjw.gooeylibs2.fabric.cookbook.ratelimit;

import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.fabric.cookbook.GooeyPageRegistration;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class RateLimitCommand
{
    public static LiteralArgumentBuilder<CommandSourceStack> command()
    {
        return GooeyPageRegistration.pageCommand("ratelimit", RateLimitCommand::page);
    }

    private static Page page() {
        return new RateLimitPage();
    }
}
