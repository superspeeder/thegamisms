package org.delusion.game;

import org.delusion.engine.App;
import org.delusion.engine.render.Color;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.window.input.InputHandler;
import org.delusion.engine.window.input.Key;
import org.joml.Vector2d;

public class InputManager implements InputHandler {

    private final App app;
    private final Renderer renderer;

    public InputManager(App app, Renderer renderer) {
        this.app = app;
        this.renderer = renderer;
    }

    @Override
    public void onKeyPressed(Key key, int mods, int scancode) {
        if (key == Key.Escape) {
            app.closeWindow();
        }
        if (key == Key.R) {
            renderer.setBackgroundColor(Color.RED);
        }
        if (key == Key.G) {
            renderer.setBackgroundColor(Color.GREEN);
        }
        if (key == Key.B) {
            renderer.setBackgroundColor(Color.BLUE);
        }
    }

    @Override
    public void onKeyReleased(Key key, int mods, int scancode) {

    }

    @Override
    public void onCharTyped(String text) {

    }

    @Override
    public void onCharTypedMods(String text, int mods) {

    }

    @Override
    public void onClick(int button, int mods) {

    }

    @Override
    public void onClickRelease(int button, int mods) {

    }

    @Override
    public void onMouseMotion(Vector2d pos) {

    }
}

