package ca.landonjw.gooeylibs2.bootstrap;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public interface BuilderProvider {

    <T> T provide(Class<T> type) throws NoSuchElementException;

    <T> boolean register(Class<T> type, Supplier<T> supplier);

}
