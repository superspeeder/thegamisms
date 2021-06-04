package org.delusion.engine.window;

import org.lwjgl.glfw.GLFW;

public enum ContextCreationAPI {
    Native(GLFW.GLFW_NATIVE_CONTEXT_API),
    EGL(GLFW.GLFW_EGL_CONTEXT_API),
    OSMesa(GLFW.GLFW_OSMESA_CONTEXT_API)
    ;

    private final int val;

    ContextCreationAPI(final int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
