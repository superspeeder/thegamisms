package org.delusion.engine.render.buffer;

import org.delusion.engine.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class VertexBuffer {
    private int handle;
    private List<Float> values;
    private BufferMode mode;
    private boolean dirty = false;
    private boolean sizeDirty = false;

    public VertexBuffer(List<Float> values, BufferMode mode) {
        this.values = values;
        this.mode = mode;

        handle = glCreateBuffers();
        bind(); unbind();
        glNamedBufferData(handle, Utils.listToArrayf(values), mode.getValue());
    }

    public VertexBuffer(List<Float> values) {
        this(values, BufferMode.StaticDraw);
    }

    public VertexBuffer(BufferMode mode) {
        values = new ArrayList<>();

        handle = glCreateBuffers();
        bind(); unbind();
        glNamedBufferData(handle, 0, mode.getValue());
    }

    public VertexBuffer(int size, BufferMode mode) {
        values = new ArrayList<>(Collections.nCopies(size, 0.0f));
        this.mode = mode;

        handle = glCreateBuffers();
        bind(); unbind();
        glNamedBufferData(handle, size * Float.BYTES, mode.getValue());
    }

    public void update() {
        if (dirty) {
            if (sizeDirty) {
                glNamedBufferData(handle, Utils.listToArrayf(values), mode.getValue());
                sizeDirty = false;
            } else {
                glNamedBufferSubData(handle, 0, Utils.listToArrayf(values));
            }
            dirty = false;
        }
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER,handle);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public VertexBuffer(int size) {
        this(size, BufferMode.StaticDraw);
    }

    public int getHandle() {
        return handle;
    }

    public void set(int i, float v) {
        values.set(i, v);
        dirty = true;
    }

    public void set(List<Float> vals) {
        dirty = true;
        if (values.size() < vals.size()) {
            sizeDirty = true;
        }
        values = vals;
    }
}
