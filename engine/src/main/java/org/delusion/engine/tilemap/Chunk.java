package org.delusion.engine.tilemap;

import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.texture.Tileset;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.utils.TriConsumer;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Chunk {
    public static final int SIZE = 64;
    public static final int PIXELS_PER_TILE = 16;
    public static final int PIXELS_PER_CHUNK = PIXELS_PER_TILE * SIZE;

    private ChunkManager.ChunkPos position;
    private int[][] data = new int[SIZE][SIZE];
    private boolean dirty = true;
    private Batch batch;

    public Chunk(ChunkManager.ChunkPos position) {
        this.position = position;
    }

    public void set(int x, int y, int t) {
        data[x][y] = t;
        dirty = true;
    }

    public int get(int x, int y) {
        return data[x][y];
    }

    public void forEach(TriConsumer<Integer,Integer,Integer> f) {
        for (int x = 0 ; x < SIZE ; x++) {
            for (int y = 0 ; y < SIZE ; y++) {
                f.apply(x, y, data[x][y]);
            }
        }
    }

    public void markDirty() {
        dirty = true;
    }

    public void save() {

    }

    public void compute(BiFunction<Integer,Integer,Integer> computeF) {
        for (int x = 0 ; x < SIZE ; x++) {
            for (int y = 0 ; y < SIZE ; y++) {
                data[x][y] = computeF.apply(x,y);
            }
        }
        dirty = true;
    }

    public ChunkManager.ChunkPos getPos() {
        return position;
    }

    public void draw(Renderer renderer, Tileset tileset) {
        if (batch == null) {
            batch = new Batch();
        }
        if (dirty) {
            batch.begin();
            forEach((x,y,t) -> {
                if (t != -1) {
                    batch.batch(new Vector2f(
                                    x * Chunk.PIXELS_PER_TILE + position.x * Chunk.PIXELS_PER_CHUNK,
                                    y * Chunk.PIXELS_PER_TILE + position.y * Chunk.PIXELS_PER_CHUNK),
                            new Vector2f(Chunk.PIXELS_PER_TILE, Chunk.PIXELS_PER_TILE), tileset.tileUVs(t));
                }
            });
            batch.end();
            dirty = false;
        }


        batch.draw(renderer);
    }
}
