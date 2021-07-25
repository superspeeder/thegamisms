package org.delusion.engine.render.ui;

import org.delusion.engine.render.Color;
import org.delusion.engine.render.Renderer;
import org.joml.Vector2f;

public class ColoredRect extends Rect {
    private Color color;

    public ColoredRect(Vector2f pos, Vector2f size, boolean fill, Color color) {
        super(pos, size, fill);
        this.color = color;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.setForegroundColor(color);
        super.render(renderer);
    }
}
