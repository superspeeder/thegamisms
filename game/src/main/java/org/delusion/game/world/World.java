package org.delusion.game.world;

import org.delusion.game.tiles.TileData;
import org.delusion.game.utils.Direction;
import org.delusion.game.world.tilemap.Chunk;
import org.delusion.game.world.tilemap.ChunkManager;
import org.delusion.game.tiles.TileType;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class World {
    private final ChunkManager chunkManager;

    public World(ChunkManager chunkManager) {

        this.chunkManager = chunkManager;
        chunkManager.setWorld(this);
    }

    public boolean isSolid(int x, int y) {
        return getTile(x,y).solid();
    }

    public Vector2f getGravity() {
        return new Vector2f(0.f,-970);
    }

    public TileType computeTile(int x, int y, int xx, int yy) {
        int truex = x + xx * Chunk.SIZE;
        int truey = y + yy * Chunk.SIZE;
        int h = 0;
        if (Math.abs(truex) < 100 && truey >= 8 && truey <= 100) {
            return TileType.Platform;
        }
        if (truey > h) {
            return TileType.Air;
        } else if (truey == h) {
            return TileType.Grass;
        } else if (truey > h - 6) {
            return TileType.Dirt;
        } else {
            return TileType.Stone;
        }
    }

    public boolean collidableFrom(int x, int y, Direction dir) {
        if (!isSolid(x,y)) return false;

        TileType tt = getTile(x,y);

        return tt.solidInDirection(dir);
    }

    public TileType getTile(int x, int y) {
        return chunkManager.getTile(x,y);
    }

    public Collection<TileData> getTiles(int x1, int y1, int x2, int y2) {
        List<TileData> tdl = new ArrayList<>();
        for (int x = x1; x <= x2 ; x++) {
            for (int y = y1; y <= y2; y++) {
                TileType tt = getTile(x,y);
                if (tt.solid()) {
                    tdl.add(new TileData(new Vector2i(x,y), tt));
                }
            }
        }

        return tdl;
    }
}
