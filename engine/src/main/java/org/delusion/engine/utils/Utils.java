package org.delusion.engine.utils;

import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    public static String readResourceToString(String path) {
        InputStream istream = Utils.class.getResourceAsStream(path);
        return new BufferedReader(new InputStreamReader(istream))
                .lines().parallel().collect(Collectors.joining("\n"));
    }

    public static ByteBuffer loadByteBufferResource(String path) throws IOException {
        InputStream istream = Utils.class.getResourceAsStream(path);
        byte[] bytes = istream.readAllBytes();
        if (bytes.length == 0) {
            return null;
        }

        ByteBuffer bb = BufferUtils.createByteBuffer(bytes.length);
        bb.put(bytes);
        bb.rewind();
        return bb;
    }


    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Throwable;
    }

    @FunctionalInterface
    public interface ThrowingVoid {
        void run() throws Throwable;
    }

    public static <T> T ignoreErrors(ThrowingSupplier<T> f) {
        try {
            return f.get();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void ignoreErrors(ThrowingVoid f) {
        try {
            f.run();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
