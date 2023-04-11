package ca.landonjw.gooeylibs2.fabric.cookbook.pagination;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import ca.landonjw.gooeylibs2.fabric.cookbook.GooeyPageRegistration;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class PaginationCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> command()
    {
        return GooeyPageRegistration.pageCommand("pagination", PaginationCommand::page);
    }

    private static Page page() {
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Button button = GooeyButton.builder()
                    .display(new ItemStack(Item.byId(100 + i)))
                    .build();
            buttons.add(button);
        }

        GooeyButton filler = GooeyButton.builder()
                .display(new ItemStack(Blocks.BLUE_STAINED_GLASS_PANE))
                .build();

        LinkedPageButton previous = LinkedPageButton.builder()
                .title("Previous")
                .display(new ItemStack(Items.DIAMOND))
                .linkType(LinkType.Previous)
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .title("Next")
                .display(new ItemStack(Items.DIAMOND))
                .linkType(LinkType.Next)
                .build();

        PlaceholderButton placeholder = new PlaceholderButton();

        ChestTemplate template = ChestTemplate.builder(6)
                .rectangle(1, 1, 4, 7, placeholder)
                .fill(filler)
                .set(5, 3, previous)
                .set(5, 5, next)
                .build();

        return PaginationHelper.createPagesFromPlaceholders(template, buttons, null);
    }
}
