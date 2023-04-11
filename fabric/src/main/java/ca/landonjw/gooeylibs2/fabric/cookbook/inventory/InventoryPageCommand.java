package ca.landonjw.gooeylibs2.fabric.cookbook.inventory;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.helpers.InventoryHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import ca.landonjw.gooeylibs2.fabric.cookbook.GooeyPageRegistration;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InventoryPageCommand
{
    public static LiteralArgumentBuilder<CommandSourceStack> command()
    {
        return GooeyPageRegistration.pageCommand("inventory", InventoryPageCommand::page);
    }

    private static Page page() {
        Button setButton = GooeyButton.builder()
                .display(new ItemStack(Items.DIAMOND))
                .title("Set diamond in first inventory slot")
                .onClick((action) -> {
                    InventoryHelper.setToInventorySlot(action.getPlayer(), 0, new ItemStack(Items.DIAMOND));
                })
                .build();

        Button setButton2 = GooeyButton.builder()
                .display(new ItemStack(Items.PUMPKIN_PIE))
                .title("Set pumpkin pie in first inventory row and third column")
                .onClick((action) -> {
                    InventoryHelper.setToInventorySlot(action.getPlayer(), 0, 2, new ItemStack(Items.PUMPKIN_PIE));
                })
                .build();

        Button addButton = GooeyButton.builder()
                .display(new ItemStack(Items.EMERALD))
                .title("Add emerald to inventory")
                .onClick((action) -> {
                    InventoryHelper.addToInventorySlot(action.getPlayer(), new ItemStack(Items.EMERALD));
                })
                .build();

        Button getButton = GooeyButton.builder()
                .display(new ItemStack(Items.APPLE))
                .title("Print out first slot in inventory")
                .onClick((action) -> {
                    System.out.println(InventoryHelper.getStackAtSlot(action.getPlayer(), 0));
                })
                .build();

        Template template = ChestTemplate.builder(3)
                .set(0, setButton)
                .set(1, setButton2)
                .set(2, addButton)
                .set(3, getButton)
                .build();

        return GooeyPage.builder()
                .template(template)
                .build();
    }
}
