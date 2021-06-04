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
}
