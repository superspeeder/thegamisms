package org.delusion.engine.tilemap;

import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.texture.Tileset;
import org.delusion.engine.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ChunkManager {
    private static final int MAX_DIST = 2;
    private ConcurrentHashMap<ChunkPos, Chunk> chunks = new ConcurrentHashMap<>();
//    private ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    private Tileset tileset;
    private ChunkPos oldCenterChunk;

    public ChunkManager(Tileset tileset) {
        this.tileset = tileset;
    }

    public void update(ChunkPos centerChunk) {
        if (!centerChunk.equals(oldCenterChunk)) {
            oldCenterChunk = centerChunk;
            System.out.println("Crossed into new chunk: " + centerChunk);
            new Thread(() -> {
                // Validate chunks
                List<ChunkPos> invalidCs = chunks.keySet().parallelStream().filter(pos -> Utils.mrdist(centerChunk.vec(), pos.vec()) > MAX_DIST).collect(Collectors.toList());
                for (ChunkPos v : invalidCs) {
                    chunks.get(v).save();
                    chunks.remove(v);
                }

                for (int x = -MAX_DIST; x < MAX_DIST; x++) {
                    for (int y = -MAX_DIST; y < MAX_DIST; y++) {
                        if (!chunks.containsKey(new ChunkPos(x + centerChunk.x, y + centerChunk.y))) {
                            new Thread(this.chunkGenerator(x + centerChunk.x, y + centerChunk.y)).start();
                            System.out.printf("Started Generation of chunk (%d, %d)\n", x + centerChunk.x, y + centerChunk.y);
                        }
                    }
                }

            }).start();
        }
    }

    private Runnable chunkGenerator(final int x, final int y) {
        Chunk c = new Chunk(new ChunkPos(x,y));
        chunks.put(c.getPos(), c);
        return () -> this.genChunk(c, x,y);
    }

    private void genChunk(Chunk c, int xx, int yy) {


        c.compute((x,y) -> {
            int truex = x + xx * Chunk.SIZE;
            int truey = y + yy * Chunk.SIZE;
            int h = (int) (Math.sin((double)truex / 32.0) * 32.0f);
            if (truey > h) {
                return -1;
            } else if (truey == h) {
                return 1;
            } else {
                return 0;
            }
        });
        System.out.printf("Finished generation of chunk (%d, %d)\n",xx,yy);
    }

    public void draw(Renderer renderer) {
        for (Map.Entry<ChunkPos, Chunk> entry : chunks.entrySet()) {
            entry.getValue().draw(renderer, tileset);
        }
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
            return "ChunkPos{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        public Vector2i vec() {
            return new Vector2i(x,y);
        }
    }
}
