package org.delusion.engine.render.buffer;

import org.delusion.engine.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;
import static org.lwjgl.opengl.GL45.glNamedBufferData;

public class ElementBuffer {

    private int handle;
    private List<Integer> values;
    private BufferMode mode;

    public ElementBuffer(List<Integer> values, BufferMode mode) {
        this.values = values;
        this.mode = mode;

        handle = glCreateBuffers();
        bind(); unbind();
        glNamedBufferData(handle, Utils.listToArrayi(values), mode.getValue());
    }

    public ElementBuffer(List<Integer> values) {
        this(values, BufferMode.StaticDraw);
    }

    public ElementBuffer(BufferMode mode) {
        values = new ArrayList<>();

        handle = glCreateBuffers();
        bind(); unbind();
        glNamedBufferData(handle, 0, mode.getValue());
    }

    public ElementBuffer(int size, BufferMode mode) {
        values = new ArrayList<>(Collections.nCopies(size, 0));
        this.mode = mode;

        handle = glCreateBuffers();
        bind(); unbind();
        glNamedBufferData(handle, size * Integer.BYTES, mode.getValue());
    }


    public ElementBuffer(int size) {
        this(size, BufferMode.StaticDraw);
    }

    public ElementBuffer(int[] eboD) {
        this(eboD, BufferMode.StaticDraw);
    }

    public ElementBuffer(int[] eboD, BufferMode mode) {
        this.values = Arrays.stream(eboD).boxed().collect(Collectors.toList());
        this.mode = mode;

        handle = glCreateBuffers();
        bind(); unbind();
        glNamedBufferData(handle, eboD, mode.getValue());
    }


    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,handle);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
    }

    public int getHandle() {
        return handle;
    }
}
