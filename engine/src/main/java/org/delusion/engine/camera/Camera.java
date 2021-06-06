package org.delusion.engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Camera {
    protected Matrix4f projection, view;
    protected Vector2f position;

    protected boolean dirty = true;

    public Camera(Matrix4f projection, Vector2f position) {
        this.projection = projection;
        this.position = position;
    }

    public Camera(Matrix4f projection) {
        this.projection = projection;
        position = new Vector2f(0.0f, 0.0f);
    }

    public Matrix4f getView() {
        if (dirty) {
            view = new Matrix4f().translate(new Vector3f(position, 0.0f).negate());
            dirty = false;
        }

        return view;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getCombined() {
        return new Matrix4f(projection).mul(getView());
    }

    public Camera setPosition(float x, float y) {
        position = new Vector2f(x, y);
        dirty = true;
        return this;
    }

    public Camera setPosition(Vector2f pos) {
        this.position = pos;
        dirty = true;
        return this;
    }

    public Camera translate(float dx, float dy) {
        position.add(new Vector2f(dx, dy));
        dirty = true;
        return this;
    }

    public Camera translate(Vector2f delta) {
        this.position.add(delta);
        dirty = true;
        return this;
    }

}
