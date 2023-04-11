package ca.landonjw.gooeylibs2.fabric.cookbook.shooter;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.data.UpdateEmitter;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.page.PageAction;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import ca.landonjw.gooeylibs2.api.template.types.InventoryTemplate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public class ShooterPage extends UpdateEmitter<Page> implements Page {

    private final ShooterController controller = new ShooterController();
    private final ChestTemplate template;
    private final InventoryTemplate inventoryTemplate;

    private final Button ship = GooeyButton.builder()
            .title("")
            .display(new ItemStack(Items.EXPERIENCE_BOTTLE))
            .build();

    private final Button projectile = GooeyButton.builder()
            .title("")
            .display(new ItemStack(Items.FIREWORK_ROCKET))
            .build();

    public ShooterPage() {
        controller.subscribe(this, this::refresh);

        Button moveLeft = GooeyButton.builder()
                .title(ChatFormatting.GOLD + "Move Left")
                .display(new ItemStack(Items.EMERALD))
                .onClick(() -> controller.movePlayer(ShooterController.Direction.LEFT))
                .build();

        Button shoot = GooeyButton.builder()
                .title(ChatFormatting.GOLD + "Shoot")
                .display(new ItemStack(Items.FIREWORK_ROCKET))
                .onClick(controller::shoot)
                .build();

        Button moveRight = GooeyButton.builder()
                .title(ChatFormatting.GOLD + "Move Right")
                .display(new ItemStack(Items.EMERALD))
                .onClick(() -> controller.movePlayer(ShooterController.Direction.RIGHT))
                .build();

        this.inventoryTemplate = InventoryTemplate.builder()
                .set(3, 3, moveLeft)
                .set(3, 4, shoot)
                .set(3, 5, moveRight)
                .build();

        this.template = ChestTemplate.builder(6).build();
    }

    private void refresh() {
        // If the game is over, Fill the page with all red panes and change title.
        if (controller.isGameOver()) {
            Button redFiller = GooeyButton.builder()
                    .display(new ItemStack(Blocks.RED_STAINED_GLASS_PANE))
                    .title("")
                    .build();
            for (int i = 0; i < template.getSize(); i++)
                template.getSlot(i).setButton(redFiller);
            for (int i = 0; i < inventoryTemplate.getSize(); i++)
                inventoryTemplate.getSlot(i).setButton(redFiller);
            update();
            return;
        }

        // Flush last tick's contents on the page
        for (int i = 0; i < template.getSize(); i++)
            template.getSlot(i).setButton(null);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                inventoryTemplate.getSlot(row, col).setButton(null);
            }
        }

        // Set all projectiles in the template
        for (ShooterProjectile projectile : controller.getProjectiles()) {
            if (projectile.getRow() >= 6) {
                inventoryTemplate.getSlot(projectile.getRow() - 6, projectile.getCol()).setButton(this.projectile);
            } else {
                template.getSlot(projectile.getRow(), projectile.getCol()).setButton(this.projectile);
            }
        }

        // Set all enemies in the template
        for (ShooterEnemy enemy : controller.getEnemies()) {
            Button enemyDisplay = GooeyButton.builder()
                    .display(new ItemStack(enemy.getDisplay()))
                    .title("")
                    .build();
            if (enemy.getRow() >= 6) {
                inventoryTemplate.getSlot(enemy.getRow() - 6, enemy.getCol()).setButton(enemyDisplay);
            } else {
                template.getSlot(enemy.getRow(), enemy.getCol()).setButton(enemyDisplay);
            }
        }

        // Set player in the template
        PlayerShip player = controller.getPlayer();
        inventoryTemplate.getSlot(1, player.getCol()).setButton(ship);

        // If the controller is dirty, update the container to change the title.
        if (controller.isHealthDirty()) {
            update();
            controller.setHealthClean();
        }
    }

    @Override
    public void onClose(PageAction action) {
        if (!controller.isGameOver()) {
            controller.setGameOver();
        }
    }

    @Override
    public Template getTemplate() {
        return template;
    }

    @Override
    public Optional<InventoryTemplate> getInventoryTemplate() {
        return Optional.of(inventoryTemplate);
    }

    @Override
    public Component getTitle() {
        if (controller.isGameOver()) {
            return Component.literal(ChatFormatting.BOLD + "GAME OVER");
        }
        return Component.literal(ChatFormatting.BOLD + "Health: " + ChatFormatting.RED + "" + ChatFormatting.BOLD + controller.getHealth());
    }

}