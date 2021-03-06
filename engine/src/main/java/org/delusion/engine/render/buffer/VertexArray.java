package org.delusion.engine.render.buffer;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class VertexArray {
    private int handle;

    private int nextBinding = 0;
    private int nextAttribute = 0;

    public VertexArray() {
        handle = glCreateVertexArrays();
    }

    public VertexArray bindVertexBuffer(VertexBuffer vbo, int... attribSizes) {
        int vsize = 0;
        int binding = nextBinding++;
        for (int asize : attribSizes) {
            vsize += asize;
        }

        glVertexArrayVertexBuffer(handle, binding, vbo.getHandle(), 0,vsize * Float.BYTES);


        int off = 0;
        for (int asize : attribSizes) {
            glVertexArrayAttribFormat(handle, nextAttribute, asize, GL_FLOAT, false, off * Float.BYTES);
            off += asize;
            glVertexArrayAttribBinding(handle, nextAttribute, binding);
            glEnableVertexArrayAttrib(handle, nextAttribute++);
        }

        return this;
    }

    public VertexArray elementBuffer(ElementBuffer ebo) {
        glVertexArrayElementBuffer(handle, ebo.getHandle());
        return this;
    }

    public VertexArray enableAttrib(int idx) {
        glEnableVertexArrayAttrib(handle, idx);
        return this;
    }

    public VertexArray disableAttrib(int idx) {
        glDisableVertexArrayAttrib(handle, idx);
        return this;
    }

    public void bind() {
        glBindVertexArray(handle);
    }

    public VertexArray bindVertexBuffer(VertexBuffer vbo, List<Integer> attribSizes) {
        int vsize = 0;
        int binding = nextBinding++;
        for (int asize : attribSizes) {
            vsize += asize;
        }

        glVertexArrayVertexBuffer(handle, binding, vbo.getHandle(), 0,vsize * Float.BYTES);


        int off = 0;
        for (int asize : attribSizes) {
            glVertexArrayAttribFormat(handle, nextAttribute, asize, GL_FLOAT, false, off * Float.BYTES);
            off += asize;
            glVertexArrayAttribBinding(handle, nextAttribute, binding);
            glEnableVertexArrayAttrib(handle, nextAttribute++);
        }

        return this;

    }

    public void rebindVBO(VertexBuffer vbo, int binding, int vsize) {
        glVertexArrayVertexBuffer(handle, binding, vbo.getHandle(), 0, vsize * Float.BYTES);
    }

    public int getHandle() {
        return handle;
    }

    public void dispose() {
        glDeleteVertexArrays(handle);
    }
}
