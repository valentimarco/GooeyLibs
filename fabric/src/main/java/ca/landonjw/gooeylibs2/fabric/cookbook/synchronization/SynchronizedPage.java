package ca.landonjw.gooeylibs2.fabric.cookbook.synchronization;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.data.UpdateEmitter;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class SynchronizedPage extends UpdateEmitter<Page> implements Page {

    private final ChestTemplate template;
    private String title;

    private final GooeyButton setInactiveButton = GooeyButton.builder()
            .display(new ItemStack(Blocks.RED_WOOL))
            .title("Set Inactive")
            .onClick(() -> setActive(false))
            .build();

    private final GooeyButton setActiveButton = GooeyButton.builder()
            .display(new ItemStack(Blocks.GREEN_WOOL))
            .title("Set Active")
            .onClick(() -> setActive(true))
            .build();

    public SynchronizedPage() {
        GooeyButton filler = GooeyButton.builder()
                .display(new ItemStack(Blocks.GRAY_STAINED_GLASS_PANE))
                .build();

        this.template = ChestTemplate.builder(6)
                .fill(filler)
                .build();
        setActive(false);
    }

    private void setActive(boolean state) {
        // Toggles the button that is used to set the page active/inactive
        template.getSlot(3, 4).setButton((state) ? setInactiveButton : setActiveButton);
        this.title = state ? ChatFormatting.GREEN + "Active Page!" : ChatFormatting.RED + "Inactive Page!";
        // Refreshes the container for all viewers
        update();
    }

    @Override
    public Template getTemplate() {
        return template;
    }

    @Override
    public Component getTitle() {
        return Component.literal(title);
    }

}