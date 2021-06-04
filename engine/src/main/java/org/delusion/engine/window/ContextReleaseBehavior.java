package org.delusion.engine.window;

import org.lwjgl.glfw.GLFW;

public enum ContextReleaseBehavior {
    Any(GLFW.GLFW_ANY_RELEASE_BEHAVIOR),
    Flush(GLFW.GLFW_RELEASE_BEHAVIOR_FLUSH),
    None(GLFW.GLFW_RELEASE_BEHAVIOR_NONE)
    ;

    private final int val;

    ContextReleaseBehavior(final int val) {

        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
