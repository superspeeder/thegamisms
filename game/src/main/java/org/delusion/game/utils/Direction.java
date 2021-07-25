package org.delusion.game.utils;

import java.util.List;
import java.util.Set;

public enum Direction {
    Up,Down,Left,Right;

    public static Set<Direction> all() {
        return Set.of(Up,Down,Left,Right);
    }

    public static Set<Direction> none() {
        return Set.of();
    }
}
