package org.delusion.game;

import org.delusion.engine.App;
import org.delusion.engine.assets.AssetLibrary;
import org.delusion.engine.camera.Camera;
import org.delusion.engine.camera.OrthoCamera;
import org.delusion.engine.render.Color;
import org.delusion.engine.render.RenderQueue;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.shader.Shader;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.texture.Texture2D;
import org.delusion.engine.render.texture.Tileset;
import org.delusion.engine.render.ui.*;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.sprite.QuadSprite;
import org.delusion.engine.window.input.InputHandler;
import org.delusion.game.inventory.*;
import org.delusion.game.tiles.TileType;
import org.delusion.engine.render.PackedTextureManager;
import org.delusion.game.world.tilemap.Chunk;
import org.delusion.game.world.tilemap.ChunkManager;
import org.delusion.engine.utils.Utils;
import org.delusion.engine.window.Window;
import org.delusion.game.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

/*
To-Do List
 TODO: UI System
 TODO: Inventory
 TODO: Background Layer
 TODO: Lighting (terraria style (starbound was too complex, maybe later))
 TODO: Small Tile Data (a single 32bit integer per tile
 TODO: Better World Generation
 TODO: Interactable Tiles
 TODO: EntityTiles
 TODO: Solid Bitmap
 TODO: Main Menu
 TODO: Pause Menu
 TODO: Character Sprites
 TODO: Inventory Tiles (chests, crates, etc)
 TODO: Placement Logic
 TODO: Structure Generation
 TODO: Entities
 TODO: HP System
 TODO: ToolItems
 TODO: WeaponItems
 TODO: Projectiles
 TODO: Procedurally Generated Loot
 TODO: Trees


 */


public class Main extends App {

    private AssetLibrary assetLibrary;
    private ShaderProgram uiTexturedUVProgram;

    public static Main getCurrent() {
        App a = App.getCurrent();
        if (a instanceof Main) {
            return (Main)a;
        } else {
            throw new IllegalStateException("Currently running app is not an instance of " + Main.class.getName());
        }
    }

    private Renderer renderer;
    private Camera camera;
    private ShaderProgram program, playerProgram;
    private Texture2D tex, tex2;
    private Batch batch;
    private ChunkManager chunkManager;
    private Tileset ts;
    private World world;
    private PlayerSprite player;
    private QuadSprite overlay;
    private RenderQueue rq;
    private ShaderProgram uiProgram;
    private ShaderProgram textProgram;
    private Group rootUI;
    private ShaderProgram uiTexturedProgram;
    private PackedTextureManager textureManager = new PackedTextureManager();
    private Items items;
    private Font font;

    public Main(Settings settings) {
        super(settings);
    }

    public void initTextures() {
        Texture2D.TexParams texParams = new Texture2D.TexParams()
                .setWrap(Texture2D.WrapMode.ClampToEdge, Texture2D.WrapMode.ClampToEdge)
                .setFilter(Texture2D.Filter.Nearest, Texture2D.Filter.Nearest);

        Slot.registerTextures(textureManager, texParams);
        Items.registerTextures(textureManager, texParams);
    }

    @Override
    public void create() {
        // Load Assets
        assetLibrary = new AssetLibrary();

        TileType.init();

        renderer = new Renderer(this);
        renderer.setPackedTextureManager(textureManager);

        try {
            font = new Font("/fonts/Orbitron-ExtraBold.ttf", 24);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stack.setFont(font);
        Item.setRenderer(renderer);

        program = new ShaderProgram(new Shader(Shader.Type.Vertex, "/shaders/tile.vert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/tile.frag.glsl"));
        playerProgram = new ShaderProgram(new Shader(Shader.Type.Vertex,"/shaders/player.vert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/player.frag.glsl"));
        uiProgram = new ShaderProgram(new Shader(Shader.Type.Vertex, "/shaders/ui.vert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/ui.frag.glsl"));
        textProgram = new ShaderProgram(new Shader(Shader.Type.Vertex, "/shaders/ui.text.vert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/ui.text.frag.glsl"));
        uiTexturedProgram = new ShaderProgram(new Shader(Shader.Type.Vertex, "/shaders/ui.textured.vert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/ui.textured.frag.glsl"));
        uiTexturedUVProgram = new ShaderProgram(new Shader(Shader.Type.Vertex, "/shaders/ui.textured.vert.glsl"), new Shader(Shader.Type.Fragment, "/shaders/ui.textured_uv.frag.glsl"));

        assetLibrary.putShader("tile", program);
        assetLibrary.putShader("player", program);
        assetLibrary.putShader("ui.basic", uiProgram);
        assetLibrary.putShader("ui.textured", uiTexturedProgram);
        assetLibrary.putShader("ui.text", textProgram);
        assetLibrary.putShader("ui.textured_uv", uiTexturedUVProgram);


        Texture2D.TexParams texParams = new Texture2D.TexParams()
                .setWrap(Texture2D.WrapMode.ClampToEdge, Texture2D.WrapMode.ClampToEdge)
                .setFilter(Texture2D.Filter.Nearest, Texture2D.Filter.Nearest);
        tex = Utils.ignoreErrors(() -> new Texture2D("/textures/tmap.png", texParams));
        tex2 = Utils.ignoreErrors(() -> new Texture2D("/textures/cursor.png", texParams.setFilter(Texture2D.Filter.Linear, Texture2D.Filter.Nearest)));


        initTextures();
        items = new Items(renderer);

        ts = new Tileset(tex, new Vector2f(16, 16));
        textureManager.addTileset("tile", ts);
        chunkManager = new ChunkManager(ts);

        world = new World(chunkManager);

        player = new PlayerSprite(new Vector2f(0,256), world);
        camera = new OrthoCamera(-960, 960, -540, 540);
        batch = new Batch();

        overlay = new QuadSprite();

        rq = new RenderQueue();
        rootUI = new Group();

        rootUI.addAll(
            player.getHUD()
        );

        player.getHotbar().getSlot(0).setContents(new Stack(items.WOOD_PLANKS, 1));
        player.getHotbar().getSlot(1).setContents(new Stack(items.GEM1, 4));
        player.getHotbar().getSlot(2).setContents(new Stack(items.WOOD_LOGS, 43));
        player.getHotbar().getSlot(3).setContents(new Stack(items.WOOD_STICKS, 100));
        player.getHotbar().getSlot(5).setContents(new Stack(items.GEM1, 22));

        setInputHandler(new InputManager(this, renderer));
        rootUI.draw(rq, batch);
        rq.queue(player.getCursorSprite());
    }

    double aaaa = 0;
    int aaaa_ = 0;

    @Override
    public void render(double delta) {
        renderer.clearScreen();
        aaaa_++;
        double fps = 1.0f / delta;
        aaaa += fps;

        player.update((float) delta);
        camera.setPosition(player.getPosition());


        overlay.setPosition(player.getBoundingBoxTile().getPos1()).setScale(player.getBoundingBoxTile().getSize());


        renderer.useShader(program);
        program.uniform1f("uTime", getWindow().getTime());

        renderer.setTex(tex,0);
        renderer.setModel(new Matrix4f().identity());
        renderer.setViewProjection(camera.getCombined());

        chunkManager.update(ChunkManager.chunkPosFromPixel(camera.getPosition()));

        program.uniform2f("lpos", player.getBoundingBox().getCenter());

        chunkManager.draw(renderer);

        renderer.useShader(playerProgram);
        renderer.setModel(player.getModel());
        renderer.setViewProjection(camera.getCombined());
        renderer.setTex(tex2, 0);
        playerProgram.uniform1f("uTime", getWindow().getTime());
        player.draw(renderer);

        renderer.setViewProjection(camera.getProjection());

        rq.draw(renderer);

        if (rootUI.isDirty()) {
            rq.reset();
            rootUI.draw(rq, batch);
            rq.queue(player.getCursorSprite());
        }

        if (!cleanupBtchs.isEmpty())
            cleanupBtchs.pop().dispose();

    }

    @Override
    public void destroy() {
        System.out.println("Avg FPS: " + (aaaa / (double)aaaa_));
    }

    public static void main(String[] args) {
        new Main(new Settings(new Window.Settings()
                .setResizable(true).setMaximized(true).setOpenglDebugContext(true))
                .setWindowTitle("The Game!")
//                .enableFullscreen(0)
        ).run();
    }

    public PlayerSprite getPlayer() {
        return player;
    }

    public Node getRootUI() {
        return rootUI;
    }

    public Vector2f unproject(Vector2f screen) {
        int[] viewport = getWindow().getViewport();
        Vector3f vec = new Vector3f();
        new Matrix4f(camera.getCombined()).unproject(new Vector3f(screen, 0.0f), viewport, vec);
//        System.out.println(vec);
        return new Vector2f(vec.x,vec.y);
    }

    public Vector2f unprojectUI(Vector2f screen) {
        int[] viewport = getWindow().getViewport();
        Vector2f homo = new Vector2f(screen).mul(2.0f / viewport[2], 2.0f / viewport[3]);
        homo.x -= 1.0f;
        homo.y = (2 - homo.y) - 1.0f;
        Vector2f vector2f = new Vector2f(homo.x * 960, homo.y * 540);
//        System.out.println(vector2f.x + ", " + vector2f.y + " " + screen.x + ", " + screen.y + " " + homo.x + ", " + homo.y);
        return vector2f;
    }

    public AssetLibrary getAssetLibrary() {
        return assetLibrary;
    }

    public PackedTextureManager getTexManager() {
        return textureManager;
    }

    ConcurrentLinkedDeque<Batch> cleanupBtchs = new ConcurrentLinkedDeque<>();

    public void addToCleanupBatches(Batch batch) {
        cleanupBtchs.add(batch);
    }
}
