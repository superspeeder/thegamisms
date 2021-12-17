package org.delusion.game.world.tilemap;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.texture.Tileset;
import org.delusion.game.tiles.TileType;
import org.delusion.game.world.World;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.*;
import java.util.concurrent.*;

public class ChunkManager {
    private static final int MAX_DIST = 2;
    private static final int MAX_LOAD_DIST = 3;
    private static final int MAX_CHUNKS = 200;
    private final AsyncLoadingCache<ChunkPos, Chunk> chunks = Caffeine.newBuilder()
            .maximumSize(MAX_CHUNKS)
            .removalListener((key, value, cause) -> {
                if (value == null) return;
                Chunk c = (Chunk)value;
                c.save();
            })
            .buildAsync(this::generateChunk);
    private ChunkPos curCenterChunk = new ChunkPos(0, 0);

    private Chunk generateChunk(ChunkPos key) {
        return new Chunk(key, world);
    }


    private Tileset tileset;
    private ChunkPos oldCenterChunk;
    private World world;

    public ChunkManager(Tileset tileset) {
        this.tileset = tileset;
    }

    public void update(ChunkPos centerChunk) {
        curCenterChunk = centerChunk;

        chunks.getAll(getLoadChunkRange());
    }

    public void draw(Renderer renderer) {
        for (ChunkPos cp : getVisibleChunkRange()) {
            getChunkOptional(cp, true).ifPresent(chunk -> chunk.draw(renderer));
        }
    }

    private ChunkPos.Range getVisibleChunkRange() {
        return new ChunkPos.Range(curCenterChunk.x - MAX_DIST, curCenterChunk.y - MAX_DIST, curCenterChunk.x + MAX_DIST + 1, curCenterChunk.y + MAX_DIST + 1);
    }

    public static ChunkPos chunkPosFromPixel(Vector2f pixel) {
        Vector2f f = new Vector2f(pixel).div(Chunk.PIXELS_PER_CHUNK);
        return new ChunkPos((int) Math.floor(f.x), (int) Math.floor(f.y));
    }

    public static Vector2i tilePosFromPixel(Vector2f pixel) {
        Vector2f f = new Vector2f(pixel).div(Chunk.PIXELS_PER_TILE);
        return new Vector2i((int) Math.floor(f.x), (int) Math.floor(f.y));
    }

    public static ChunkPos chunkPosFromTile(Vector2f tile) {
        Vector2f f = new Vector2f(tile).div(Chunk.SIZE);
        return new ChunkPos((int) Math.floor(f.x), (int) Math.floor(f.y));
    }

    public static Vector2i relative(Vector2f tile) {
        return new Vector2i(Math.floorMod((int) tile.x,Chunk.SIZE), Math.floorMod((int) tile.y,Chunk.SIZE));
    }

    public TileType getTile(int x, int y) {
        ChunkPos chunk = chunkPosFromTile(new Vector2f(x,y));
        Vector2i rel = relative(new Vector2f(x,y));
        TileType tt = getChunk(chunk).get(rel);
        if (tt == null) {
            return TileType.Air;
        }
        return tt;
    }

    public Chunk getChunk(ChunkPos chunk) {
        try {
            return chunks.get(chunk).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Optional<Chunk> getChunkOptional(ChunkPos chunk) {
        return getChunkOptional(chunk, false);
    }

    public Optional<Chunk> getChunkOptional(ChunkPos chunk, boolean gen) {
        if (gen) {
            CompletableFuture<Chunk> cf = chunks.get(chunk);
            return Optional.ofNullable(cf.getNow(null));
        }
        CompletableFuture<Chunk> cf = chunks.getIfPresent(chunk);
        if (cf != null) {
            return Optional.ofNullable(cf.getNow(null));
        }
        return Optional.empty();
    }



    public boolean isChunkVisible(ChunkPos c) {
        return getVisibleChunkRange().contains(c);
    }


    public void setWorld(World world) {
        this.world = world;
    }

    public static class ChunkPos {
        public final int x;
        public final int y;

        public ChunkPos(int x, int y) {

            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ChunkPos chunkPos = (ChunkPos) o;

            if (getX() != chunkPos.getX()) return false;
            return getY() == chunkPos.getY();
        }

        @Override
        public int hashCode() {
            int result = getX();
            result = 31 * result + getY();
            return result;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        public Vector2i vec() {
            return new Vector2i(x,y);
        }

        public static class Range implements Iterator<ChunkPos>, Iterable<ChunkPos> {
            private int ci = 0;
            private final int x1;
            private final int x2;
            private final int y1;
            private final int y2;
            private final int w;
            private final int h;
            private final int end;

            public Range(int x1, int y1, int x2, int y2) {
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;

                w = x2 - x1;
                h = y2 - y1;
                end = w * h - 1;
            }

            private ChunkPos fromIndex(int i) {
                int row = Math.floorDiv(i, w) + x1;
                int col = Math.floorMod(i, w) + y1;
                return new ChunkPos(row, col);
            }

            @Override
            public boolean hasNext() {
                return ci <= end;
            }

            @Override
            public ChunkPos next() {
                return fromIndex(ci++);
            }

            public boolean contains(ChunkPos other) {
                return contains(other.x, other.y);
            }

            public boolean contains(int x, int y) {
                return x1 <= x && x <= x2 && y1 <= y && y <= y2;
            }

            @Override
            public Iterator<ChunkPos> iterator() {
                return this;
            }
        }
    }


    private ChunkPos.Range getLoadChunkRange() {
        return new ChunkPos.Range(curCenterChunk.x - MAX_LOAD_DIST, curCenterChunk.y - MAX_LOAD_DIST, curCenterChunk.x + MAX_LOAD_DIST + 1, curCenterChunk.y + MAX_LOAD_DIST + 1);
    }
}
