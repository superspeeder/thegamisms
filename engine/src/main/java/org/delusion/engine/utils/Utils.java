package org.delusion.engine.utils;

import org.delusion.engine.math.AABB;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    public static int mrdist(Vector2i v1, Vector2i v2) {
        return Math.max(Math.abs(v1.x-v2.x), Math.abs(v1.y-v2.y));
    }

    public static boolean rectContains(Vector2f rectpos, Vector2f size, Vector2f ppos) {
        return new AABB(rectpos, size).containsPoint(ppos);
    }

    public static ByteBuffer loadByteBufferResource(String path, int size) throws IOException {
        InputStream istream = Utils.class.getResourceAsStream(path);
        byte[] bytes = istream.readAllBytes();
        if (bytes.length == 0) {
            return null;
        }

        ByteBuffer bb = BufferUtils.createByteBuffer(size);
        bb.put(bytes);
        bb.rewind();
        return bb;
    }

    public static Collection<Integer> offsetAll(int i, int... vals) {
        List<Integer> out = new ArrayList<>();
        for (int x : vals) {
            out.add(x + i);
        }
        return out;
    }

    public static FloatBuffer makeFloatBuffer(List<Float> floats) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(floats.size());
        fb.put(listToArrayf(floats));
        return fb;
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
