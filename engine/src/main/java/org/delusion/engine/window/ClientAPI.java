package org.delusion.engine.window;

import org.lwjgl.glfw.GLFW;

public enum ClientAPI {
    OpenGL(GLFW.GLFW_OPENGL_API),
    OpenGLES(GLFW.GLFW_OPENGL_ES_API),
    None(GLFW.GLFW_NO_API)
    ;

    private final int val;

    ClientAPI(final int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
