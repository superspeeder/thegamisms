package org.delusion.engine.tilemap;

import org.delusion.engine.utils.TriConsumer;
import org.joml.Vector2i;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Chunk {
    public static final int SIZE = 32;
    public static final int PIXELS_PER_TILE = 16;
    public static final int PIXELS_PER_CHUNK = PIXELS_PER_TILE * SIZE;

    private Vector2i position;
    private int[][] data = new int[SIZE][SIZE];

    public Chunk(Vector2i position) {
        this.position = position;
    }

    public void set(int x, int y, int t) {
        data[x][y] = t;
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

    public void save() {

    }

    public void compute(BiFunction<Integer,Integer,Integer> computeF) {
        for (int x = 0 ; x < SIZE ; x++) {
            for (int y = 0 ; y < SIZE ; y++) {
                set(x,y,computeF.apply(x,y));
            }
        }
    }

    public Vector2i getPos() {
        return position;
    }
}
