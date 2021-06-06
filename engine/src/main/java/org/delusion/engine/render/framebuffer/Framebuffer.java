package org.delusion.engine.render.framebuffer;

import org.delusion.engine.render.texture.Texture2D;

import static org.lwjgl.opengl.GL46.*;

public class Framebuffer {
    private int handle;

    public Framebuffer() {
        handle = glCreateFramebuffers();
    }

    public Framebuffer attachTexture(Texture2D tex, Attachment attachment) {
        glNamedFramebufferTexture(handle, attachment.getVal(),tex.getHandle(), 0);
        return this;
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, handle);
    }

    public void bindDraw() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, handle);
    }

    public void bindRead() {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, handle);
    }

    public static void bindDefault() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public static void bindDefaultDraw() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    public static void bindDefaultRead() {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
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
