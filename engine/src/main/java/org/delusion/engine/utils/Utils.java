package org.delusion.engine.utils;

import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class Utils {
    public static void requirePositive(int value, String message) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static IntBuffer[] createIntBuffers(int capacity, int count) {
        IntBuffer[] buffers = new IntBuffer[count];
        for (int i = 0 ; i < count ; i++) {
            buffers[i] = BufferUtils.createIntBuffer(capacity);
        }
        return buffers;
    }

    public static FloatBuffer[] createFloatBuffers(int capacity, int count) {
        FloatBuffer[] buffers = new FloatBuffer[count];
        for (int i = 0 ; i < count ; i++) {
            buffers[i] = BufferUtils.createFloatBuffer(capacity);
        }
        return buffers;
    }

    public static DoubleBuffer[] createDoubleBuffers(int capacity, int count) {
        DoubleBuffer[] buffers = new DoubleBuffer[count];
        for (int i = 0 ; i < count ; i++) {
            buffers[i] = BufferUtils.createDoubleBuffer(capacity);
        }
        return buffers;
    }

    public static float clamp(float v, float min, float max) {
        return Math.min(Math.max(v, min), max);
    }

    public static float[] listToArrayf(List<Float> l) {
        final float[] arr = new float[l.size()];
        int index = 0;
        for (final Float value: l) {
            arr[index++] = value;
        }
        return arr;
    }

    public static int[] listToArrayi(List<Integer> l) {
        final int[] arr = new int[l.size()];
        int index = 0;
        for (final Integer value: l) {
            arr[index++] = value;
        }
        return arr;
    }
}
