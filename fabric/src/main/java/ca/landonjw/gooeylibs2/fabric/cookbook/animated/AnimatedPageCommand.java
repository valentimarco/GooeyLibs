package ca.landonjw.gooeylibs2.fabric.cookbook.animated;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.moveable.MovableButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import ca.landonjw.gooeylibs2.api.template.types.InventoryTemplate;
import ca.landonjw.gooeylibs2.fabric.cookbook.GooeyPageRegistration;
import ca.landonjw.gooeylibs2.fabric.cookbook.animated.AnimatedPage;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AnimatedPageCommand
{
    public static LiteralArgumentBuilder<CommandSourceStack> command()
    {
        return GooeyPageRegistration.pageCommand("animated", AnimatedPageCommand::page);
    }

    private static Page page() {
//        return new AnimatedPage();
        return pageA();
    }

    private static Page pageA() {
        Button button = GooeyButton.builder()
                .display(new ItemStack(Items.DIAMOND))
                .onClick((action) -> {
                    UIManager.openUIForcefully(action.getPlayer(), pageB());
                })
                .build();

        Template template = ChestTemplate.builder(6)
                .fill(button)
                .build();

        return GooeyPage.builder()
                .template(template)
                .build();
    }

    private static Page pageB() {
        Button button = GooeyButton.builder()
                .display(new ItemStack(Items.EMERALD))
                .onClick((action) -> {
                    UIManager.openUIForcefully(action.getPlayer(), pageA());
                })
                .build();

        Template template = ChestTemplate.builder(6)
                .fill(button)
                .build();

        return GooeyPage.builder()
                .template(template)
                .build();
    }
}
