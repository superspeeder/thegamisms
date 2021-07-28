package org.delusion.engine.math;

import org.joml.Vector2f;

public class AABB {
    private Vector2f pos, size;

    public AABB(Vector2f position, Vector2f scale) {
        this.pos = new Vector2f(position);
        this.size = new Vector2f(scale);
    }

    public static AABB centerPos(Vector2f pos, Vector2f size) {
        return new AABB(new Vector2f(pos.x - size.x / 2, pos.y - size.y / 2), size);
    }

    public Vector2f getBottomLeft() {
        return pos;
    }

    public Vector2f getTopRight() {
        return new Vector2f(pos).add(size);
    }

    public Vector2f getSize() {
        return size;
    }


    public boolean containsPoint(Vector2f point) {
        return (point.x >= pos.x && point.x <= pos.x + size.x) && (point.y >= pos.y && point.y <= pos.y + size.y);
    }

    public boolean  overlaps(AABB other) { // l2 <= r1 and r2 >= l1 and b2 <= t1 and t2 >= b1
        return other.pos.x < pos.x + size.x && other.pos.x + other.size.x > pos.x && other.pos.y < pos.y + size.y && other.pos.y + other.size.y > pos.y;
    }

    @Override
    public String toString() {
        return "AABB{" +
                "pos=(" + pos.x + ", " + pos.y +
                "), size=(" + size.x + ", " + size.y +
                ")}";
    }

    public Vector2f getCenter() {
        return new Vector2f(pos.x + size.x / 2.f, pos.y + size.y / 2.f);
    }
}
