package org.delusion.game.inventory;

import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.texture.Texture2D;
import org.joml.Vector4f;

public class Item {
    private static Renderer renderer;

    private int maxStackSize;
    private String name;
    private Texture2D tex;

    @Override
    public String toString() {
        return name;
    }

    private Vector4f uvs;

    public Item(String name, int maxStackSize, Texture2D tex, Vector4f uvs) {
        this.maxStackSize = maxStackSize;
        this.name = name;
        this.tex = tex;
        this.uvs = uvs;
    }

    public Item(String name, int maxStackSize, String tilesetName, String texName) {
        this(name, maxStackSize, renderer.getPackedTextureManager().getTileset(tilesetName).getTexture(), renderer.getPackedTextureManager().getTextureUVs(tilesetName, texName));
    }

    public Item(String name, int maxStackSize) {
        this(name, maxStackSize, "item", name);
    }

    public static void setRenderer(Renderer renderer) {
        Item.renderer = renderer;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public Stack makeStack(int count) {
        return new Stack(this, count);
    }

    public Texture2D getTexture() {
        return tex;
    }

    public Vector4f getTexUVs() {
        return uvs;
    }
}
