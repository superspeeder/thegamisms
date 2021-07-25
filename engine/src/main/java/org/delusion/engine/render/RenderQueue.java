package org.delusion.engine.render;

import org.delusion.engine.render.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class RenderQueue {
    private List<Shape> shapes = new ArrayList<>();

    public void reset() {
        shapes.forEach(Shape::dispose);
        shapes.clear();
    }

    public void queue(Shape shape) {
        shapes.add(shape);
    }

    public void draw(Renderer renderer) {
        shapes.forEach(s -> {
            s.render(renderer);
        });
    }
}
