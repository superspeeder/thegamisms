package org.delusion.engine.render.ui;

import org.delusion.engine.render.RenderQueue;
import org.delusion.engine.sprite.Batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Classic node extension. is able to have children.
 */
public class Group extends Node {

    private List<Node> children = new ArrayList<>();

    public Group() {
    }

    @Override
    public void draw(RenderQueue rq, Batch batch) {
        children.forEach(c -> c.draw(rq, batch));
    }

    public void add(Node child) {
        children.add(child);
        child.setParent(this);
    }

    public void addAll(Node... childs) {
        children.addAll(Arrays.asList(childs));
        for (Node child : childs) {
            child.setParent(this);
        }
    }

    public void addAll(Collection<? extends Node> childs) {
        children.addAll(childs);
        for (Node child : childs) {
            child.setParent(this);
        }
    }

    public List<Node> getChildren() {
        return children;
    }
}
