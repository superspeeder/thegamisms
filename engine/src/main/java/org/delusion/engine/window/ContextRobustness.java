package org.delusion.engine.window;

import org.lwjgl.glfw.GLFW;

public enum ContextRobustness {
    None(GLFW.GLFW_NO_ROBUSTNESS),
    NoResetNotif(GLFW.GLFW_NO_RESET_NOTIFICATION),
    LoseOnReset(GLFW.GLFW_LOSE_CONTEXT_ON_RESET)
    ;

    private final int val;

    ContextRobustness(final int val) {

        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
