package org.delusion.game;

import org.delusion.engine.App;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.buffer.ElementBuffer;
import org.delusion.engine.render.buffer.VertexArray;
import org.delusion.engine.render.buffer.VertexBuffer;
import org.delusion.engine.window.Window;
import org.lwjgl.system.MemoryUtil;

import java.util.List;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Main extends App {
    private Renderer renderer;
    private VertexBuffer vbo;
    private ElementBuffer ebo;
    private VertexArray vao;

    public Main(Settings settings) {
        super(settings);
    }

    @Override
    public void create() {
        renderer = new Renderer(this);
        setInputHandler(new InputManager(this, renderer));

        vbo = new VertexBuffer(List.of(0.0f,0.0f,0.0f,1.0f,0.0f,0.0f,0.0f,1.0f,0.0f,1.0f,1.0f,0.0f));
        ebo = new ElementBuffer(List.of(0,1,2,3,2,1));
        vao = new VertexArray().bindVertexBuffer(vbo, 3).elementBuffer(ebo);

    }

    @Override
    public void render(double delta) {
        renderer.clearScreen();
        renderer.drawElements(Renderer.PrimitiveType.Triangles,vao, 6);
    }

    @Override
    public void destroy() {

    }

    public static void main(String[] args) {
        new Main(new Settings(new Window.Settings()
                .setResizable(false))
                .enableFullscreen(0)
                .setWindowTitle("The Game!"))
                .run();
    }
}
