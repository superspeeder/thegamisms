package org.delusion.game.world;

import org.delusion.engine.tilemap.ChunkManager;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class World {
    private final ChunkManager chunkManager;

    public World(ChunkManager chunkManager) {

        this.chunkManager = chunkManager;
    }

    public boolean isSolid(int x, int y) {
        return chunkManager.getTile(x,y) != -1;
    }

    public Vector2f getGravity() {
        return new Vector2f(0.f,-0.2f);
    }
}
