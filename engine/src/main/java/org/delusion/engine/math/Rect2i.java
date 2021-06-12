package org.delusion.engine.math;

import org.joml.Vector2f;
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

    public static Rect2i fromCorners(int x1, int y1, int x2, int y2) {
        return new Rect2i(x1,y1,x2-x1,y2-y1);
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

    public int getX2() {
        return x + getWidth();
    }

    public int getY2() {
        return y + getHeight();
    }

    public Vector2f getPos1() {
        return new Vector2f(x,y);
    }

    public Vector2f getSize() {
        return new Vector2f(w,h);
    }
}
