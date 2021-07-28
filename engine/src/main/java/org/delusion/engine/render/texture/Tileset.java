package org.delusion.engine.render.texture;

import org.delusion.engine.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import java.util.Objects;

public class Tileset {
    private Texture2D tex;
    private Vector2f tileSize;
    private int rowSize;

    public Tileset(Texture2D tex, Vector2f tileSize) {
        this.tex = tex;
        this.tileSize = tileSize;
        rowSize = (int) (tex.getWidth() / tileSize.x);
    }

    public Tileset(String path, Texture2D.TexParams texParams, Vector2f tileSize) {
        this(Objects.requireNonNull(Utils.ignoreErrors(() -> new Texture2D(path, texParams)), "Texture does not exist"), tileSize);
    }

    public Vector4f tileUVs(int i) {
        int x = Math.floorMod(i, rowSize);
        int y = Math.floorDiv(i, rowSize);

        return tex.pixelToUVs(x * tileSize.x, y * tileSize.y, (x + 1) * tileSize.x, (y + 1) * tileSize.y);
    }

    public Texture2D getTexture() {
        return tex;
    }
}
