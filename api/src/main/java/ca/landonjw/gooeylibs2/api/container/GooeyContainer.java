package ca.landonjw.gooeylibs2.api.container;

import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.bootstrap.GooeyBootstrapper;
import net.minecraft.server.level.ServerPlayer;

public interface GooeyContainer {

    static GooeyContainer of(ServerPlayer viewer, Page page) {
        return GooeyBootstrapper.instance().provider().provide(GooeyContainerFactory.class).create(viewer, page);
    }

    void open();

    void refresh();

    interface GooeyContainerFactory {

        GooeyContainer create(ServerPlayer viewer, Page page);

    }

}
