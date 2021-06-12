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
import org.delusion.game.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Main extends App {
    private Renderer renderer;
    private Camera camera;
    private ShaderProgram program;
    private Texture2D tex, tex2;
    private Batch batch;
    private ChunkManager chunkManager;
    private Tileset ts;
    private World world;
    private PlayerSprite player;

    private QuadSprite overlay;

    public Main(Settings settings) {
        super(settings);
    }

    @Override
    public void create() {
        renderer = new Renderer(this);

        program = new ShaderProgram(new Shader(Shader.Type.Vertex,"/shaders/testvert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/testfrag.glsl"));

        Texture2D.TexParams texParams = new Texture2D.TexParams()
                .setWrap(Texture2D.WrapMode.ClampToEdge, Texture2D.WrapMode.ClampToEdge)
                .setFilter(Texture2D.Filter.Nearest, Texture2D.Filter.Nearest);
        tex = Utils.ignoreErrors(() -> new Texture2D("/textures/tmap.png", texParams));
        tex2 = Utils.ignoreErrors(() -> new Texture2D("/textures/cursor.png", texParams.setFilter(Texture2D.Filter.Linear, Texture2D.Filter.Nearest)));

        ts = new Tileset(tex, new Vector2f(16, 16));
        chunkManager = new ChunkManager(ts);

        world = new World(chunkManager);

        player = new PlayerSprite(new Vector2f(0,128), world);
        camera = new OrthoCamera(-960, 960, -540, 540);
        batch = new Batch();

        overlay = new QuadSprite();



        getWindow().hideCursor();
        setInputHandler(new InputManager(this, renderer));
    }

    @Override
    public void render(double delta) {
        renderer.clearScreen();
//        double fps = 1.0f / delta;
//
//        if (getWindow().getKey(Key.A)) {
//            camera.translate((float) (-SPEED * delta), 0);
//        }
//        if (getWindow().getKey(Key.D)) {
//            camera.translate((float) (SPEED * delta), 0);
//        }
//        if (getWindow().getKey(Key.W)) {
//            camera.translate(0, (float) (SPEED * delta));
//        }
//        if (getWindow().getKey(Key.S)) {
//            camera.translate(0, (float) (-SPEED * delta));
//        }

        player.update();
        camera.setPosition(player.getPosition());


        overlay.setPosition(player.getBoundingBox().getPos1()).setScale(player.getBoundingBox().getSize());

        program.use();

        program.uniform1f("uTime", getWindow().getTime());

        tex.bind();
        program.uniformMat4("model", new Matrix4f().identity());
        program.uniformMat4("viewProjection", camera.getCombined());
        chunkManager.update(ChunkManager.chunkPosFromPixel(camera.getPosition()));

        chunkManager.draw(renderer);

        tex2.bind();
        program.uniformMat4("model", player.getModel());
//        program.uniformMat4("viewProjection", camera.getProjection());
        player.draw(renderer);

        program.uniformMat4("model", overlay.getModel());
        overlay.draw(renderer);
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

    public PlayerSprite getPlayer() {
        return player;
    }
}
