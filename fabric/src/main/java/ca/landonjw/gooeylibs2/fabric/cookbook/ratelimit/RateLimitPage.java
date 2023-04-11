package ca.landonjw.gooeylibs2.fabric.cookbook.ratelimit;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.RateLimitedButton;
import ca.landonjw.gooeylibs2.api.data.UpdateEmitter;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.tasks.Task;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import ca.landonjw.gooeylibs2.api.template.types.InventoryTemplate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RateLimitPage extends UpdateEmitter<Page> implements Page {

    private final ChestTemplate template;
    private final InventoryTemplate playerTemplate;
    private String title = "Rate Limit Demonstration";

    private final Button greenPane = GooeyButton.builder()
            .display(new ItemStack(Blocks.GREEN_STAINED_GLASS_PANE))
            .build();

    private final Button yellowPane = GooeyButton.builder()
            .display(new ItemStack(Blocks.YELLOW_STAINED_GLASS_PANE))
            .build();

    private final Button redPane = GooeyButton.builder()
            .display(new ItemStack(Blocks.RED_STAINED_GLASS_PANE))
            .build();

    private final RateLimitedButton rateLimitedButton;

    private static int tracker = 0;

    public RateLimitPage() {
        Button clickMe = GooeyButton.builder()
                .display(new ItemStack(Items.SLIME_BALL))
                .title("Click me!")
                .onClick(() -> System.out.println(tracker++))
                .build();

        this.rateLimitedButton = RateLimitedButton.builder()
                .button(clickMe)
                .limit(9)
                .interval(3, TimeUnit.SECONDS)
                .build();

        this.template = ChestTemplate.builder(6).build();
        this.playerTemplate = InventoryTemplate.builder()
                .set(3, 4, rateLimitedButton)
                .build();
        Task.builder()
                .execute(this::updateBar)
                .infinite()
                .interval(1)
                .build();
    }

    private void updateBar() {
        int clicksUsed = 9 - rateLimitedButton.getAllowedRemainingClicks();
        int rowsFilled = (int) Math.ceil((clicksUsed / 9.0) * 9);
        int rowsNotFilled = 9 - rowsFilled;
        for (int row = 0; row < Math.min(rowsNotFilled, 6); row++) {
            for (int col = 0; col < 9; col++) {
                template.getSlot(row, col).setButton(null);
            }
        }
        if (rowsNotFilled > 6) {
            for (int row = 0; row < Math.min(rowsNotFilled - 6, 4); row++) {
                for (int col = 0; col < 9; col++) {
                    playerTemplate.getSlot(row, col).setButton(null);
                }
            }
        }
        for (int row = 0; row < Math.min(rowsFilled, 3); row++) {
            for (int col = 0; col < 9; col++) {
                if (playerTemplate.getSlot(2 - row, col).getButton().isEmpty()) {
                    playerTemplate.getSlot(2 - row, col).setButton(greenPane);
                }
            }
        }
        if (rowsFilled > 3) {
            for (int row = 0; row < Math.min(rowsFilled - 3, 3); row++) {
                for (int col = 0; col < 9; col++) {
                    if (template.getSlot(5 - row, col).getButton().isEmpty()) {
                        template.getSlot(5 - row, col).setButton(yellowPane);
                    }
                }
            }
        }
        if (rowsFilled > 6) {
            for (int row = 0; row < Math.min(rowsFilled - 6, 3); row++) {
                for (int col = 0; col < 9; col++) {
                    if (template.getSlot(2 - row, col).getButton().isEmpty()) {
                        template.getSlot(2 - row, col).setButton(redPane);
                    }
                }
            }
        }
        if (clicksUsed == 9 && !title.equals(ChatFormatting.RED + "Currently rate limited!")) {
            this.title = ChatFormatting.RED + "Currently rate limited!";
            update();
        } else if (clicksUsed != 9 && title.equals(ChatFormatting.RED + "Currently rate limited!")) {
            this.title = "Rate Limit Demonstration";
            update();
        }
    }

    @Override
    public Template getTemplate() {
        return template;
    }

    @Override
    public Component getTitle() {
        return Component.literal(title);
    }

    @Override
    public Optional<InventoryTemplate> getInventoryTemplate() {
        return Optional.of(playerTemplate);
    }

}