package org.delusion.engine.tilemap;

import org.delusion.engine.render.texture.Tileset;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ChunkManager {
    private static final int MAX_DIST = 8;
    private ConcurrentHashMap<Vector2i, Chunk> chunks = new ConcurrentHashMap<>();
    private ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private Tileset tileset;

    public ChunkManager(Tileset tileset) {
        this.tileset = tileset;
    }

    public void update(Vector2i centerChunk) {
        poolExecutor.execute(() -> {
            // Validate chunks
            List<Vector2i> invalidCs = new ArrayList<>();
            for (Map.Entry<Vector2i, Chunk> entry : chunks.entrySet()) {
                if (Utils.mrdist(centerChunk, entry.getKey()) > MAX_DIST) {
                    invalidCs.add(entry.getKey());
                }
            }
            for (Vector2i v : invalidCs) {
                chunks.get(v).save();
                chunks.remove(v);
            }

            for (int x = -MAX_DIST ; x < MAX_DIST ; x++) {
                for (int y = -MAX_DIST ; y < MAX_DIST ; y++) {
                    poolExecutor.execute(this.chunkGenerator(x + centerChunk.x, y + centerChunk.y));
                }
            }

        });
    }

    private Runnable chunkGenerator(final int x, final int y) {
        return () -> this.genChunk(x,y);
    }

    private void genChunk(int x, int y) {
        Chunk c = new Chunk(new Vector2i(x,y));
        if (x % 2 == 0 && y % 2 == 0) {
            c.compute((xx,yy) -> 0);
        } else {
            c.compute((xx,yy) -> 150);
        }
        chunks.put(c.getPos(), c);
    }

    public void renderToBatch(Batch batch) {
        for (Map.Entry<Vector2i, Chunk> entry : chunks.entrySet()) {
            entry.getValue().forEach((x,y,t) -> {
                batch.batch(new Vector2f(
                        x * Chunk.PIXELS_PER_TILE + entry.getKey().x * Chunk.PIXELS_PER_CHUNK,
                        y * Chunk.PIXELS_PER_TILE + entry.getKey().y * Chunk.PIXELS_PER_CHUNK),
                        new Vector2f(Chunk.PIXELS_PER_TILE, Chunk.PIXELS_PER_TILE), tileset.tileUVs(t));
            });
        }
    }

    public static Vector2i chunkPosFromPixel(Vector2f pixel) {
        Vector2f f = new Vector2f(pixel).div(Chunk.PIXELS_PER_CHUNK);
        return new Vector2i((int) Math.floor(f.x), (int) Math.floor(f.y));
    }

    public static Vector2i tilePosFromPixel(Vector2f pixel) {
        Vector2f f = new Vector2f(pixel).div(Chunk.PIXELS_PER_TILE);
        return new Vector2i((int) Math.floor(f.x), (int) Math.floor(f.y));
    }

    public static Vector2i chunkPosFromTile(Vector2f tile) {
        Vector2f f = new Vector2f(tile).div(Chunk.SIZE);
        return new Vector2i((int) Math.floor(f.x), (int) Math.floor(f.y));
    }

    public static Vector2i relative(Vector2f tile) {
        return new Vector2i(Math.floorMod((int) tile.x,Chunk.SIZE), Math.floorMod((int) tile.y,Chunk.SIZE));
    }
}
