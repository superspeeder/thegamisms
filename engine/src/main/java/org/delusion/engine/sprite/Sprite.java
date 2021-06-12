package org.delusion.engine.sprite;

import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.mesh.Mesh;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Sprite {
    private Mesh mesh;
    protected Vector2f position = new Vector2f(0.0f,0.0f), scale = new Vector2f(0.0f,0.0f);
    protected float rotation = 0;

    protected Matrix4f model;
    protected boolean dirty = true;

    public Sprite(Mesh mesh) {
        this.mesh = mesh;
    }

    public Sprite(Mesh mesh, Vector2f position) {
        this.mesh = mesh;
        this.position = position;
    }

    public Sprite(Mesh mesh, Vector2f position, Vector2f scale) {
        this.mesh = mesh;
        this.position = position;
        this.scale = scale;
    }

    public Sprite(Mesh mesh, Vector2f position, Vector2f scale, float rotation) {
        this.mesh = mesh;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Sprite(Mesh mesh, Vector2f position, float rotation) {
        this.mesh = mesh;
        this.position = position;
        this.rotation = rotation;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Sprite setMesh(Mesh mesh) {
        this.mesh = mesh;
        return this;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Sprite setPosition(Vector2f position) {
        this.position = position;
        dirty = true;
        return this;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Sprite setScale(Vector2f scale) {
        this.scale = scale;
        dirty = true;
        return this;
    }

    public float getRotation() {
        return rotation;
    }

    public Sprite setRotation(float rotation) {
        this.rotation = rotation;
        dirty = true;
        return this;
    }

    public Matrix4f getModel() {
        if (dirty) {
            model = new Matrix4f().translate(position.x, position.y, 0.0f).rotate(rotation,0,0,1).scale(scale.x,scale.y,1.0f);
            dirty = false;
        }
        return model;
    }

    public void draw(Renderer renderer) {
        renderer.drawElements(Renderer.PrimitiveType.Triangles, mesh.getVAO(), mesh.getIndexCount());
    }

    public void setPosition(float x, float y) {
        setPosition(new Vector2f(x, y));
    }

    public void move(float x, float y) {
        position.x += x;
        position.y += y;
        dirty = true;
    }

}
