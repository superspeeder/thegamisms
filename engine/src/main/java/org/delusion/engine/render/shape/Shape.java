package org.delusion.engine.render.shape;

import org.delusion.engine.render.Renderer;

public interface Shape {
    void render(Renderer renderer);

    default void dispose() {
        // Do nothings
    }
}
