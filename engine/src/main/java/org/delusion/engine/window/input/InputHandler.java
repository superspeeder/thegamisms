package org.delusion.engine.window.input;

import org.joml.Vector2d;
import org.joml.Vector2i;

public interface InputHandler {

    void onKeyPressed(Key key, int mods, int scancode);
    void onKeyReleased(Key key, int mods, int scancode);

    void onCharTyped(String text);
    void onCharTypedMods(String text, int mods);

    void onClick(int button, int mods);
    void onClickRelease(int button, int mods);

    void onMouseMotion(Vector2d pos);
}
