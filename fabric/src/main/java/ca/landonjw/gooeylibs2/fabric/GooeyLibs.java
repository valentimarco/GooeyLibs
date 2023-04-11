package ca.landonjw.gooeylibs2.fabric;

import ca.landonjw.gooeylibs2.fabric.cookbook.animated.AnimatedPageCommand;
import ca.landonjw.gooeylibs2.fabric.cookbook.cycle.CycleCommand;
import ca.landonjw.gooeylibs2.fabric.cookbook.inventory.InventoryPageCommand;
import ca.landonjw.gooeylibs2.fabric.cookbook.pagination.PaginationCommand;
import ca.landonjw.gooeylibs2.fabric.cookbook.ratelimit.RateLimitCommand;
import ca.landonjw.gooeylibs2.fabric.cookbook.shooter.ShooterCommand;
import ca.landonjw.gooeylibs2.fabric.cookbook.snake.SnakeCommand;
import ca.landonjw.gooeylibs2.fabric.cookbook.synchronization.SynchronizedCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class GooeyLibs implements ModInitializer {

    private final FabricBootstrapper bootstrapper = new FabricBootstrapper();

    @Override
    public void onInitialize() {
        this.bootstrapper.bootstrap();
        CommandRegistrationCallback.EVENT.register((dispatcher, registries, environment) -> {
            dispatcher.register(AnimatedPageCommand.command());
            dispatcher.register(InventoryPageCommand.command());
            dispatcher.register(RateLimitCommand.command());
            dispatcher.register(ShooterCommand.command());
            dispatcher.register(SnakeCommand.command());
            dispatcher.register(CycleCommand.command());
            dispatcher.register(SynchronizedCommand.command());
            dispatcher.register(PaginationCommand.command());
        });
    }
}
