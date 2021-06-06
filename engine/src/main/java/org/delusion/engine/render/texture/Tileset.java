package org.delusion.engine.render.texture;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class Tileset {
    private Texture2D tex;
    private Vector2f tileSize;
    private int rowSize;

    public Tileset(Texture2D tex, Vector2f tileSize) {
        this.tex = tex;
        this.tileSize = tileSize;
        rowSize = (int) (tex.getWidth() / tileSize.x);
    }

    public Vector4f tileUVs(int i) {
        int x = Math.floorMod(i, rowSize);
        int y = Math.floorDiv(i, rowSize);

        return tex.pixelToUVs(x * tileSize.x, y * tileSize.y, (x + 1) * tileSize.x, (y + 1) * tileSize.y);
    }
}
