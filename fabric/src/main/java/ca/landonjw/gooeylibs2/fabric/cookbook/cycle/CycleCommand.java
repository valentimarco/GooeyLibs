package ca.landonjw.gooeylibs2.fabric.cookbook.cycle;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.TemplateType;
import ca.landonjw.gooeylibs2.api.template.types.*;
import ca.landonjw.gooeylibs2.fabric.cookbook.GooeyPageRegistration;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CycleCommand
{
    private static int ordinal = 0;

    public static LiteralArgumentBuilder<CommandSourceStack> command()
    {
        return GooeyPageRegistration.pageCommand("cycle", CycleCommand::page);
    }

    private static Page page() {
        Template template = getTemplate();

        return GooeyPage.builder()
                .template(template)
                .build();
    }

    private static Template getTemplate() {
        ordinal = ordinal + 1;
        Button button = GooeyButton.builder()
                .display(new ItemStack(Items.DIAMOND))
                .build();

        switch (ordinal % TemplateType.values().length) {
            case 0:
                return ChestTemplate.builder(5)
                        .border(0, 0, 1, 1, button)
                        .checker(0, 0, 1, 2, button, button)
                        .column(0, button)
                        .row(0, button)
                        .set(0, button)
                        .set(0, 0, button)
                        .rectangle(0, 0, 1, 1, button)
                        .square(0, 0, 0, button)
                        .fill(button)
                        .build();
            case 1:
                return FurnaceTemplate.builder()
                        .fuel(button)
                        .inputMaterial(button)
                        .outputMaterial(button)
                        .build();
            case 2:
                return BrewingStandTemplate.builder()
                        .bottle(0, button)
                        .bottles(button)
                        .fuel(button)
                        .ingredient(button)
                        .build();
            case 3:
                return HopperTemplate.builder()
                        .set(0, button)
                        .build();
            case 4:
                return DispenserTemplate.builder()
                        .set(0, button)
                        .set(0, 1, button)
                        .fill(button)
                        .build();
            case 5:
                return CraftingTableTemplate.builder()
                        .set(0, button)
                        .setGrid(0, 0, button)
                        .setResultItem(button)
                        .fill(button)
                        .build();
        }
        throw new IllegalStateException("shouldn't have reached here");
    }
}
