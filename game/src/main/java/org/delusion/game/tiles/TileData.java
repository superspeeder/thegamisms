package org.delusion.game.tiles;

import org.joml.Vector2i;

public class TileData {
    public Vector2i pos;
    public TileType ty;

    public TileData(Vector2i pos, TileType tile) {
        this.pos = pos;
        this.ty = tile;
    }

    @Override
    public String toString() {
        return "TileData{" +
                "pos=(" + pos.x + ", " + pos.y +
                "), ty=" + ty.name() +
                '}';
    }
}
