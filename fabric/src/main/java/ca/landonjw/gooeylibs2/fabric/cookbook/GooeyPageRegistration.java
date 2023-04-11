package ca.landonjw.gooeylibs2.fabric.cookbook;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.page.Page;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Supplier;

import static net.minecraft.commands.Commands.literal;

public class GooeyPageRegistration
{
    public static LiteralArgumentBuilder<CommandSourceStack> pageCommand(String pageName, Supplier<Page> page)
    {
        return literal("gooey")
                .then(literal(pageName).executes(context -> openPage(page.get()).run(context)));
    }

    private static Command<CommandSourceStack> openPage(Page page)
    {
        return context ->
        {
            final CommandSourceStack source = context.getSource();
            UIManager.openUIForcefully(source.getPlayerOrException(), page);
            return 1;
        };
    }
}
