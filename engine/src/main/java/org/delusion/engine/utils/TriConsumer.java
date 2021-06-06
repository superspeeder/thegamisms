package org.delusion.engine.utils;

@FunctionalInterface
public interface TriConsumer<T, U, V> {

    void apply(T t, U u, V v);
}
