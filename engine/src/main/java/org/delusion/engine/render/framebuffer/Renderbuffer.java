package org.delusion.engine.render.framebuffer;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderbuffer implements IFramebufferAttachable{

    private int width;
    private int height;
    private int format;
    private int samples;
    private int handle;

    public Renderbuffer(int width, int height, int format) {
        this(width, height, format, 0);
    }

    public Renderbuffer(int width, int height, int format, int samples) {
        this.width = width;
        this.height = height;
        this.format = format;
        this.samples = samples;
        handle = glCreateRenderbuffers();

        glNamedRenderbufferStorageMultisample(handle, samples, format, width, height);

    }

    public int getHandle() {
        return handle;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getFormat() {
        return format;
    }

    public int getSamples() {
        return samples;
    }
}
