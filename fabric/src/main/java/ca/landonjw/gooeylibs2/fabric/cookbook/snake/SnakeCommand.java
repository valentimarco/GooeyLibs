package ca.landonjw.gooeylibs2.fabric.cookbook.snake;

import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.fabric.cookbook.GooeyPageRegistration;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class SnakeCommand
{
    public static LiteralArgumentBuilder<CommandSourceStack> command()
    {
        return GooeyPageRegistration.pageCommand("snake", SnakeCommand::page);
    }

    private static Page page() {
        return new SnakePage();
    }
}