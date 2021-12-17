package org.delusion.game.inventory;

import org.delusion.engine.render.Color;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.ui.Font;
import org.delusion.engine.render.ui.Text;
import org.delusion.engine.render.ui.TexturedRect;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Stack {
    private int count;
    private Item item;
    private static Font font;

    public static void setFont(Font font) {
        Stack.font = font;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "count=" + count +
                ", item=" + item +
                '}';
    }

    public static class Renderable extends TexturedRect {

        private Stack stack;
        private Text text;

        public Renderable(Vector2f pos, Vector2f size, Stack stack, ShaderProgram sh, ShaderProgram textSh) {
            super(pos, size, true, stack.getItem().getTexture(), stack.getItem().getTexUVs(), sh);
            this.stack = stack;
            text = new Text(Integer.toString(stack.getCount()), font, new Vector2f(pos.x + (size.x * .75f), pos.y + (size.y * .75f)), textSh);
            add(text);
        }

        public Renderable(Vector2f pos, Vector2f size, ShaderProgram sh, ShaderProgram textSh) {
            super(pos, size, true, null, new Vector4f(), sh);
            this.stack = null;
            text = new Text("", font, new Vector2f(pos.x + (this.size.y * -0.5f), pos.y + (this.size.y * -.5f)), textSh, Color.GREEN);
            add(text);
        }

        public void update(Stack stack) {
            this.stack = stack;
            if (stack == null || stack.isEmpty()) {
                this.tex = null;
                text.setText("");
            } else {
                tex = stack.getItem().getTexture();
                setUVs(stack.getItem().getTexUVs());
                text.setText(Integer.toString(stack.getCount()));
            }
        }

        @Override
        public void render(Renderer renderer) {
            if (stack == null || stack.isEmpty()) return;
            super.render(renderer);

        }
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
