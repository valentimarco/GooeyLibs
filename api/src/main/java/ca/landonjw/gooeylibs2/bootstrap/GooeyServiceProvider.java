package ca.landonjw.gooeylibs2.bootstrap;

import org.jetbrains.annotations.NotNull;

public class GooeyServiceProvider {
    private static GooeyBootstrapper instance;

    public static @NotNull GooeyBootstrapper get() {
        if(instance == null) {
            throw new IllegalStateException("The GooeyLibs API is not loaded");
        }

        return instance;
    }

    static void register(GooeyBootstrapper service) {
        instance = service;
    }

    private GooeyServiceProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}
