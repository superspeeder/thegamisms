package org.delusion.engine.window;

import org.lwjgl.glfw.GLFW;

public enum OpenGLProfile {
    Core(GLFW.GLFW_OPENGL_CORE_PROFILE),
    Compat(GLFW.GLFW_OPENGL_COMPAT_PROFILE),
    Any(GLFW.GLFW_OPENGL_ANY_PROFILE)
    ;

    private final int val;

    OpenGLProfile(final int val) {

        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
