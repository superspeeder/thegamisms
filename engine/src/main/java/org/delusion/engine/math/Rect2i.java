package org.delusion.engine.math;

import org.joml.Vector2i;

public class Rect2i {

    private final int x;
    private final int y;
    private final int w;
    private final int h;

    public Rect2i(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public boolean containsPoint(Vector2i pt) {
        return (x <= pt.x && pt.x <= x + w) && (y <= pt.y && pt.y <= y + h);
    }
}
