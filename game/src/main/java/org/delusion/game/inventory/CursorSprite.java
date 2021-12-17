package org.delusion.game.inventory;

import org.delusion.engine.render.Color;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.shape.Shape;
import org.delusion.engine.sprite.QuadSprite;
import org.delusion.game.Main;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class CursorSprite extends QuadSprite implements Shape {

    private Vector4f uvs;
    private boolean doRender = false;

    public CursorSprite() {
        super(Main.getCurrent().getWindow().getMousePos(), new Vector2f(48, 48));
        uvs = new Vector4f(0,0,0,0);
//        setMode(Renderer.PrimitiveType.TriangleFan);
    }

    @Override
    public void draw(Renderer renderer) {
        if (doRender) {
            renderer.useShader(Main.getCurrent().getAssetLibrary().getShader("ui.textured_uv"));
            renderer.setUVs(uvs);
            renderer.setTex(Main.getCurrent().getTexManager().getTileset("item").getTexture());
            setPosition(Main.getCurrent().getWindow().getMousePos().sub(960 + 24, 540 - 24).mul(1, -1));
            renderer.setModel(getModel());
            renderer.setTint(Color.WHITE);
            super.draw(renderer);
        }
    }

    @Override
    public void render(Renderer renderer) {
        draw(renderer);
    }

    public void update(Stack cursorStack) {
        if (cursorStack == null || cursorStack.isEmpty()) { doRender = false; } else {
            doRender = true;
            uvs = cursorStack.getItem().getTexUVs();
        }
    }
}
