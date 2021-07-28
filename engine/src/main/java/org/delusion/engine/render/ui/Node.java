package org.delusion.engine.render.ui;

import org.delusion.engine.render.RenderQueue;
import org.delusion.engine.sprite.Batch;
import org.joml.Vector2f;

public abstract class Node {

    private Node parent;
    protected boolean dirty;

    public Node() {
    }

    public void markDirty() {
        dirty = true;
    }

    public void setParent(Node p) {
        parent = p;
    }

    public abstract void draw(RenderQueue rq, Batch batch);

    public void onClick(Vector2f position, int button, int mods) {
    }

    public boolean pointWithin(Vector2f pos) {
        return true;
    }

    public Node getParent() {
        return parent;
    }
}
