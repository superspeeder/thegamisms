package org.delusion.game.inventory;

import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.buffer.VertexBuffer;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.ui.TexturedRect;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.List;

public class Stack {
    private int count;
    private Item item;

    public static class Renderable extends TexturedRect {

        private Stack stack;

        public Renderable(Vector2f pos, Vector2f size, Stack stack, ShaderProgram sh) {
            super(pos, size, true, stack.getItem().getTexture(), stack.getItem().getTexUVs(), sh);
            this.stack = stack;
        }

        public Renderable(Vector2f pos, Vector2f mul, ShaderProgram sh) {
            super(pos, mul, true, null, new Vector4f(), sh);
            this.stack = null;
        }

        public void update(Stack stack) {
            this.stack = stack;
            if (stack == null || stack.isEmpty()) {
                this.tex = null;
            } else {
                tex = stack.getItem().getTexture();
                setUVs(stack.getItem().getTexUVs());
            }
        }

        @Override
        public void render(Renderer renderer) {
            if (stack == null || stack.isEmpty()) return;
            super.render(renderer);
        }

        // TODO: COUNT TEXT
    }

    public Stack(Item item, int count) {
        this.item = item;
        this.count = Math.max(Math.min(count, item.getMaxStackSize()), 0);
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
