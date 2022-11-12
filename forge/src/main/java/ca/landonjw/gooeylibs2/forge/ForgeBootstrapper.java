package ca.landonjw.gooeylibs2.forge;

import ca.landonjw.gooeylibs2.api.container.GooeyContainer;
import ca.landonjw.gooeylibs2.api.tasks.Task;
import ca.landonjw.gooeylibs2.bootstrap.BuilderProvider;
import ca.landonjw.gooeylibs2.bootstrap.GooeyBootstrapper;
import ca.landonjw.gooeylibs2.bootstrap.InstanceProvider;
import ca.landonjw.gooeylibs2.forge.implementation.ForgeGooeyContainer;
import ca.landonjw.gooeylibs2.forge.implementation.tasks.ForgeTask;

public class ForgeBootstrapper implements GooeyBootstrapper {

    private final ForgeInstanceProvider provider = new ForgeInstanceProvider();
    private final ForgeBuilderProvider builders = new ForgeBuilderProvider();

    public void bootstrap() {
        APIRegister.register(this);
        this.provider.register(GooeyContainer.GooeyContainerFactory.class, new ForgeGooeyContainer.ForgeGooeyContainerFactory());
        this.builders.register(Task.TaskBuilder.class, ForgeTask.ForgeTaskBuilder::new);
    }

    @Override
    public InstanceProvider provider() {
        return this.provider;
    }

    @Override
    public BuilderProvider builders() {
        return this.builders;
    }

}
