package ca.landonjw.gooeylibs2.fabric;

import ca.landonjw.gooeylibs2.fabric.implementation.FabricGooeyContainer;
import ca.landonjw.gooeylibs2.fabric.implementation.tasks.FabricTask;
import ca.landonjw.gooeylibs2.api.container.GooeyContainer;
import ca.landonjw.gooeylibs2.api.tasks.Task;
import ca.landonjw.gooeylibs2.bootstrap.BuilderProvider;
import ca.landonjw.gooeylibs2.bootstrap.InstanceProvider;
import ca.landonjw.gooeylibs2.bootstrap.GooeyBootstrapper;

public class FabricBootstrapper implements GooeyBootstrapper {

    private final FabricInstanceProvider provider = new FabricInstanceProvider();
    private final FabricBuilderProvider builders = new FabricBuilderProvider();

    public void bootstrap() {
        APIRegister.register(this);
        this.provider.register(GooeyContainer.GooeyContainerFactory.class, new FabricGooeyContainer.FabricGooeyContainerFactory());
        this.builders.register(Task.TaskBuilder.class, FabricTask.FabricTaskBuilder::new);
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
