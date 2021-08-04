package org.delusion.game.world.tilemap;

import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.texture.Tileset;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.utils.TriConsumer;
import org.delusion.game.tiles.BackgroundTileType;
import org.delusion.game.tiles.TileType;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.function.BiFunction;

public class Chunk {
    public static final int SIZE = 64;
    public static final int PIXELS_PER_TILE = 16;
    public static final int PIXELS_PER_CHUNK = PIXELS_PER_TILE * SIZE;

    private ChunkManager.ChunkPos position;
    private TileType[][] data = new TileType[SIZE][SIZE];
    private BackgroundTileType[][] backgroundData = new BackgroundTileType[SIZE][SIZE];
    private boolean dirty = true;
    private Batch batch;

    public Chunk(ChunkManager.ChunkPos position) {
        this.position = position;
    }

    public void set(int x, int y, TileType t) {
        data[x][y] = t;
        dirty = true;
    }

    public void setBG(int x, int y, BackgroundTileType t) {
        backgroundData[x][y] = t;
        dirty = true;
    }

    public TileType get(int x, int y) {
        return data[x][y];
    }
    public BackgroundTileType getBG(int x, int y) {
        return backgroundData[x][y];
    }

    public void forEach(TriConsumer<Integer,Integer,TileType> f) {
        for (int x = 0 ; x < SIZE ; x++) {
            for (int y = 0 ; y < SIZE ; y++) {
                f.apply(x, y, data[x][y]);
            }
        }
    }

    public void forEachBG(TriConsumer<Integer,Integer,BackgroundTileType> f) {
        for (int x = 0 ; x < SIZE ; x++) {
            for (int y = 0 ; y < SIZE ; y++) {
                f.apply(x, y, backgroundData[x][y]);
            }
        }
    }


    public void markDirty() {
        dirty = true;
    }

    public void save() {
    }

    public void compute(BiFunction<Integer,Integer, TileType> computeF) {
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
            batch = new Batch(SIZE * SIZE);
            System.out.println("Created batch for chunk");
        }
        if (dirty) {
            batch.begin();
            forEach((x,y,t) -> {
                if (t != TileType.Air && t != null) {
                    batch.batch(new Vector2f(
                                    x * Chunk.PIXELS_PER_TILE + position.x * Chunk.PIXELS_PER_CHUNK,
                                    y * Chunk.PIXELS_PER_TILE + position.y * Chunk.PIXELS_PER_CHUNK),
                            new Vector2f(Chunk.PIXELS_PER_TILE, Chunk.PIXELS_PER_TILE), tileset.tileUVs(t.getID()));
                }
            });
            batch.end();
            dirty = false;
        }


        batch.draw(renderer);
    }

    public TileType get(Vector2i rel) {
        return get(rel.x,rel.y);
    }

    public void dispose() {
        this.batch.dispose();
    }
}
