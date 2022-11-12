package ca.landonjw.gooeylibs2.bootstrap;

public interface GooeyBootstrapper {

    static GooeyBootstrapper instance() {
        return GooeyServiceProvider.get();
    }

    void bootstrap();

    InstanceProvider provider();

    BuilderProvider builders();

}
