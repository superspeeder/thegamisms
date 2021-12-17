package org.delusion.engine.render.framebuffer;

public interface IFramebufferAttachable {
    int getHandle();
    int getWidth();
    int getHeight();
    int getFormat();
}
