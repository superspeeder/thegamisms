package org.delusion.game;

import org.delusion.engine.App;
import org.delusion.engine.render.Color;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.window.input.InputHandler;
import org.delusion.engine.window.input.Key;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class InputManager implements InputHandler {

    private final Main app;
    private final Renderer renderer;

    public InputManager(Main app, Renderer renderer) {
        this.app = app;
        this.renderer = renderer;
    }

    @Override
    public void onKeyPressed(Key key, int mods, int scancode) {
//        if (key == Key.Escape) {
//            app.closeWindow();
//        }

        switch (key) {
            case Escape -> app.closeWindow();
            case K1 -> app.getPlayer().getHotbar().select(0);
            case K2 -> app.getPlayer().getHotbar().select(1);
            case K3 -> app.getPlayer().getHotbar().select(2);
            case K4 -> app.getPlayer().getHotbar().select(3);
            case K5 -> app.getPlayer().getHotbar().select(4);
            case K6 -> app.getPlayer().getHotbar().select(5);
            case K7 -> app.getPlayer().getHotbar().select(6);
            case K8 -> app.getPlayer().getHotbar().select(7);
            case K9 -> app.getPlayer().getHotbar().select(8);
            case K0 -> app.getPlayer().getHotbar().select(9);
            case Enter -> System.out.println("pos: " + app.getPlayer().getPosition());
            case I -> app.getPlayer().toggleInv();
        }

        app.getPlayer().onKeyPress(key);
    }

    @Override
    public void onKeyReleased(Key key, int mods, int scancode) {
        app.getPlayer().onKeyRelease(key);

    }

    @Override
    public void onCharTyped(String text) {

    }

    @Override
    public void onCharTypedMods(String text, int mods) {

    }

    @Override
    public void onClick(int button, int mods) {
        app.getRootUI().onClick(app.unprojectUI(app.getWindow().getMousePos()),button, mods);
    }

    @Override
    public void onClickRelease(int button, int mods) {

    }

    @Override
    public void onMouseMotion(Vector2d pos) {
//        Vector2i ws = app.getWindow().getSize();
//        app.remakeModel((float) pos.x * (1920.0f / ws.x), (float) pos.y * (1080.0f / ws.y));
    }
}

