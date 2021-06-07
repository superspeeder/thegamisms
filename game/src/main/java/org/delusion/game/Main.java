package org.delusion.game;

import org.delusion.engine.App;
import org.delusion.engine.camera.Camera;
import org.delusion.engine.camera.OrthoCamera;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.mesh.Mesh;
import org.delusion.engine.render.shader.Shader;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.texture.Texture2D;
import org.delusion.engine.render.texture.Tileset;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.sprite.QuadSprite;
import org.delusion.engine.sprite.Sprite;
import org.delusion.engine.tilemap.ChunkManager;
import org.delusion.engine.utils.Utils;
import org.delusion.engine.window.Window;
import org.delusion.engine.window.input.Key;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import javax.management.ValueExp;

public class Main extends App {
    private static final float SPEED = 450;
    private Renderer renderer;
    private Mesh mesh;
    private Camera camera;
    private ShaderProgram program;
    private Sprite cursorSprite;
    private Texture2D tex, tex2;
    private Batch batch;
    private ChunkManager chunkManager;
    private Tileset ts;

    public Main(Settings settings) {
        super(settings);
    }

    @Override
    public void create() {
        renderer = new Renderer(this);

        program = new ShaderProgram(new Shader(Shader.Type.Vertex,"/shaders/testvert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/testfrag.glsl"));

        cursorSprite = new QuadSprite(new Vector2f(0, 0), new Vector2f(64, 64), (float) Math.toRadians(-135));
        camera = new OrthoCamera(-960, 960, -540, 540);
        batch = new Batch();

        Texture2D.TexParams texParams = new Texture2D.TexParams()
                .setWrap(Texture2D.WrapMode.ClampToEdge, Texture2D.WrapMode.ClampToEdge)
                .setFilter(Texture2D.Filter.Nearest, Texture2D.Filter.Nearest);

        tex = Utils.ignoreErrors(() -> new Texture2D("/textures/tmap.png", texParams));
        tex2 = Utils.ignoreErrors(() -> new Texture2D("/textures/beaker.png", texParams.setFilter(Texture2D.Filter.Linear, Texture2D.Filter.Nearest)));

        ts = new Tileset(tex, new Vector2f(16, 16));

        getWindow().hideCursor();
        setInputHandler(new InputManager(this, renderer));
        chunkManager = new ChunkManager(ts);
    }

    @Override
    public void render(double delta) {
        renderer.clearScreen();
        double fps = 1.0f / delta;

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
        program.uniformMat4("model", new Matrix4f().identity());
        program.uniformMat4("viewProjection", camera.getCombined());
        chunkManager.update(ChunkManager.chunkPosFromPixel(camera.getPosition()));
//        batch.begin();
//                .batch(new Vector2f(0,0), new Vector2f(128,128), ts.tileUVs(150))
//                .batch(new Vector2f(128,0), new Vector2f(128,128), ts.tileUVs(151))
//                .batch(new Vector2f(0,128), new Vector2f(128,128), ts.tileUVs(166))
//                .batch(new Vector2f(128, 128), new Vector2f(128,128), ts.tileUVs(167))
//                .end();
//        batch.end();
//        batch.draw(renderer);

        chunkManager.draw(renderer);

        tex2.bind();
        program.uniformMat4("model", cursorSprite.getModel());
        program.uniformMat4("viewProjection", camera.getProjection());
        cursorSprite.draw(renderer);
    }

    @Override
    public void destroy() {

    }

    public void remakeModel(float x, float y) {
        cursorSprite.setPosition(x - 960,y - 540);
    }

    public static void main(String[] args) {
        new Main(new Settings(new Window.Settings()
                .setResizable(false))
                .enableFullscreen(1)
                .setWindowTitle("The Game!"))
                .run();
    }
}
