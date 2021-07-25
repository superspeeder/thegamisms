package org.delusion.engine.render.ui;

import org.delusion.engine.render.RenderQueue;
import org.delusion.engine.sprite.Batch;

public abstract class Node {

    private Node parent;

    public Node() {
    }

    public void setParent(Node p) {
        parent = p;
    }

    public abstract void draw(RenderQueue rq, Batch batch);
}