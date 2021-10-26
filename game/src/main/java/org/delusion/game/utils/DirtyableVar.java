package org.delusion.game.utils;

import java.util.function.Consumer;

public class DirtyableVar<T> {
    private T value;
    private final Consumer<T> onChange;

    public DirtyableVar(T v, Consumer<T> onChange) {
        value = v;

        this.onChange = onChange;
    }

    public static <T> DirtyableVar<T> create(T v, Consumer<T> onChange) {
        return new DirtyableVar<>(v, onChange);
    }

    public T get() {
        return value;
    }

    public void set(T v) {
        if (value == v) {
            return;
        }

        value = v;
        onChange.accept(value);
    }
}
