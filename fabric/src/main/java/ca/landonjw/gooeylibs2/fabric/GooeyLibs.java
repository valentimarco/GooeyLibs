package ca.landonjw.gooeylibs2.fabric;

import net.fabricmc.api.ModInitializer;

public class GooeyLibs implements ModInitializer {

    private final FabricBootstrapper bootstrapper = new FabricBootstrapper();

    @Override
    public void onInitialize() {
        this.bootstrapper.bootstrap();
    }
}
