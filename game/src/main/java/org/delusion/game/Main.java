package org.delusion.game;

import org.delusion.engine.App;
import org.delusion.engine.camera.Camera;
import org.delusion.engine.camera.OrthoCamera;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.mesh.Mesh;
import org.delusion.engine.render.shader.Shader;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.texture.Texture2D;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.sprite.QuadSprite;
import org.delusion.engine.sprite.Sprite;
import org.delusion.engine.utils.Utils;
import org.delusion.engine.window.Window;
import org.delusion.engine.window.input.Key;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Main extends App {
    private static final float SPEED = 450;
    private Renderer renderer;
    private Mesh mesh;
    private Camera camera;
    private ShaderProgram program;
    private Sprite sprite, sprite2;
    private Texture2D tex, tex2;
    private Batch batch;

    public Main(Settings settings) {
        super(settings);
    }

    @Override
    public void create() {
        renderer = new Renderer(this);

        program = new ShaderProgram(new Shader(Shader.Type.Vertex,"/shaders/testvert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/testfrag.glsl"));

        sprite = new QuadSprite(new Vector2f(0, 0), new Vector2f(256,256));
        sprite2 = new QuadSprite(new Vector2f(0, 0), new Vector2f(64, 64), (float) Math.toRadians(-135));
        camera = new OrthoCamera(0, 1920, 0, 1080);
        batch = new Batch();

        Texture2D.TexParams texParams = new Texture2D.TexParams()
                .setWrap(Texture2D.WrapMode.Repeat, Texture2D.WrapMode.Repeat)
                .setFilter(Texture2D.Filter.Linear, Texture2D.Filter.Linear);

        tex = Utils.ignoreErrors(() -> new Texture2D("/textures/francis.jpg", texParams));
        tex2 = Utils.ignoreErrors(() -> new Texture2D("/textures/beaker.png", texParams.setFilter(Texture2D.Filter.Linear, Texture2D.Filter.Nearest)));

        getWindow().hideCursor();
        setInputHandler(new InputManager(this, renderer));
    }

    @Override
    public void render(double delta) {
        renderer.clearScreen();
        double fps = 1.0f / delta;
//        if (fps < 60) {
//            System.out.println(fps);
//        }


        if (getWindow().getKey(Key.A)) {
            camera.translate((float) (-SPEED * delta), 0);
        }
        if (getWindow().getKey(Key.D)) {
            camera.translate((float) (SPEED * delta), 0);
        }
        if (getWindow().getKey(Key.W)) {
            camera.translate(0, (float) (SPEED * delta));
        }
        if (getWindow().getKey(Key.S)) {
            camera.translate(0, (float) (-SPEED * delta));
        }

        program.use();

        program.uniform1f("uTime", getWindow().getTime());

        tex.bind();
//        program.uniformMat4("model", sprite.getModel());
        program.uniformMat4("model", new Matrix4f().identity());
        program.uniformMat4("viewProjection", camera.getCombined());
//        sprite.draw(renderer);
        batch.begin()
                .batch(new Vector2f(0,0), new Vector2f(128,128), new Vector4f(0,0,1,1))
                .batch(new Vector2f(128,0), new Vector2f(128,128), new Vector4f(0,0,1,1))
                .batch(new Vector2f(0,128), new Vector2f(128,128), new Vector4f(0,0,1,1))
                .batch(new Vector2f(128, 128), new Vector2f(128,128), new Vector4f(0,0,1,1))
                .end();
        batch.draw(renderer);


        tex2.bind();
        program.uniformMat4("model", sprite2.getModel());
        program.uniformMat4("viewProjection", camera.getProjection());
        sprite2.draw(renderer);
    }

    @Override
    public void destroy() {

    }

    public void remakeModel(float x, float y) {
        sprite2.setPosition(x,y);
    }

    public static void main(String[] args) {
        new Main(new Settings(new Window.Settings()
                .setResizable(false))
                .enableFullscreen(1)
                .setWindowTitle("The Game!"))
                .run();
    }
}
