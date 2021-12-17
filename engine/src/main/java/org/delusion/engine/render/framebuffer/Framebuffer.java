package org.delusion.engine.render.framebuffer;

import org.delusion.engine.math.Rect2i;
import org.delusion.engine.render.texture.Texture2D;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL46.*;

public class Framebuffer {
    private static int boundDraw = 0;
    private static int boundRead = 0;

    private int handle;

    private boolean status = false;

    private Map<Attachment, IFramebufferAttachable> attachments = new HashMap<>();

    public static Framebuffer DEFAULT = new Framebuffer(0); // default fbo

    private Framebuffer(int i) {
        handle = i;
        if (isDefault()) status = true;
        else checkStatus();
    }

    public boolean isDefault() {
        return handle == 0;
    }


    public Framebuffer() {
        handle = glCreateFramebuffers();
    }

    public Framebuffer attachTexture(Texture2D tex, Attachment attachment) {
        attachments.put(attachment, tex);
        glNamedFramebufferTexture(handle, attachment.getVal(),tex.getHandle(), 0);
        return this;
    }

    public Framebuffer attachRenderbuffer(Renderbuffer rb, Attachment attachment) {
        attachments.put(attachment, rb);
        glNamedFramebufferRenderbuffer(handle, attachment.getVal(), GL_RENDERBUFFER, rb.getHandle());
        return this;
    }

    public IFramebufferAttachable getAttachment(Attachment atc) {
        return attachments.get(atc);
    }

    public void bind() {
        if (boundDraw != handle || boundRead != handle)
            glBindFramebuffer(GL_FRAMEBUFFER, handle);
        boundDraw = handle;
        boundRead = handle;
    }

    public void bindDraw() {
        if (boundDraw != handle)
            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, handle);
        boundDraw = handle;
    }

    public void bindRead() {
        if (boundRead != handle)
            glBindFramebuffer(GL_READ_FRAMEBUFFER, handle);
        boundRead = handle;
    }

    public boolean checkStatus() {
        if (!status)
            status = glCheckNamedFramebufferStatus(handle, GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE;
        return status;
    }

    public void blit(Framebuffer target, Rect2i src, Rect2i dst, int filter) {
        glBlitNamedFramebuffer(handle, target.handle, src.getX(), src.getY(), src.getX2(), src.getY2(), dst.getX(), dst.getY(), dst.getX2(), dst.getY2(),
                GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT, filter);
    }

    public void blit(Framebuffer target, Rect2i src, Rect2i dst, int filter, int mask) {
        glBlitNamedFramebuffer(handle, target.handle, src.getX(), src.getY(), src.getX2(), src.getY2(), dst.getX(), dst.getY(), dst.getX2(), dst.getY2(),
                mask, filter);
    }


    public enum Attachment {
        Color0(GL_COLOR_ATTACHMENT0),
        Color1(GL_COLOR_ATTACHMENT1),
        Color2(GL_COLOR_ATTACHMENT2),
        Color3(GL_COLOR_ATTACHMENT3),
        Color4(GL_COLOR_ATTACHMENT4),
        Color5(GL_COLOR_ATTACHMENT5),
        Color6(GL_COLOR_ATTACHMENT6),
        Color7(GL_COLOR_ATTACHMENT7),
        Color8(GL_COLOR_ATTACHMENT8),
        Color9(GL_COLOR_ATTACHMENT9),
        Color10(GL_COLOR_ATTACHMENT10),
        Color11(GL_COLOR_ATTACHMENT11),
        Color12(GL_COLOR_ATTACHMENT12),
        Color13(GL_COLOR_ATTACHMENT13),
        Color14(GL_COLOR_ATTACHMENT14),
        Color15(GL_COLOR_ATTACHMENT15),
        Color16(GL_COLOR_ATTACHMENT16),
        Color17(GL_COLOR_ATTACHMENT17),
        Color18(GL_COLOR_ATTACHMENT18),
        Color19(GL_COLOR_ATTACHMENT19),
        Color20(GL_COLOR_ATTACHMENT20),
        Color21(GL_COLOR_ATTACHMENT21),
        Color22(GL_COLOR_ATTACHMENT22),
        Color23(GL_COLOR_ATTACHMENT23),
        Color24(GL_COLOR_ATTACHMENT24),
        Color25(GL_COLOR_ATTACHMENT25),
        Color26(GL_COLOR_ATTACHMENT26),
        Color27(GL_COLOR_ATTACHMENT27),
        Color28(GL_COLOR_ATTACHMENT28),
        Color29(GL_COLOR_ATTACHMENT29),
        Color30(GL_COLOR_ATTACHMENT30),
        Color31(GL_COLOR_ATTACHMENT31),
        Depth(GL_DEPTH_ATTACHMENT),
        DepthStencil(GL_DEPTH_STENCIL_ATTACHMENT),
        Stencil(GL_STENCIL_ATTACHMENT)
        ;

        private final int val;

        Attachment(int val) {

            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }
}
