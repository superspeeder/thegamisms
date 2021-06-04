package org.delusion.engine.render.buffer;
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
}
