package org.delusion.engine;

import org.delusion.engine.render.texture.Texture2D;
import org.delusion.engine.render.ui.Rect;
import org.delusion.engine.render.ui.TexturedRect;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.sprite.QuadSprite;
import org.delusion.engine.utils.Utils;
import org.delusion.engine.window.Monitor;
import org.delusion.engine.window.Window;
import org.delusion.engine.window.input.InputHandler;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.IMGFilterCubic;
import org.lwjgl.vulkan.VkApplicationInfo;

public abstract class App {

    private final Window window;

    public App(Settings settings) {
        Window.init();
        if (settings.width == -1) {
            settings.width = Monitor.getMonitorOrPrimary(settings.defaultMonitor).getVideoMode().getWidth();
        }

        if (settings.height == -1) {
            settings.height = Monitor.getMonitorOrPrimary(settings.defaultMonitor).getVideoMode().getHeight();
        }

        if (settings.fullscreen) {
            window = new Window(settings.windowSettings, settings.width, settings.height, settings.windowTitle, Monitor.getMonitorOrPrimary(settings.defaultMonitor));
        } else {
            window = new Window(settings.windowSettings, settings.width, settings.height, settings.windowTitle);
        }

        window.makeContextCurrent();

        window.setVSyncEnabled(settings.enableVSync);

        if (settings.windowSettings.isOpenglDebugContext()) {
            GLUtil.setupDebugMessageCallback();
        }

        Texture2D.initTextures();
        Utils.ignoreErrors(QuadSprite::initMesh);
        Batch.initStatic();
        Rect.init();
        TexturedRect.init();
    }

    public void run() {
        create();

        long time = System.nanoTime();
        while (window.isOpen()) {
            Window.pollEvents();
            long nextTime = System.nanoTime();
            long deltatime = nextTime - time;
            time = nextTime;
            double deltatime_s = (double)deltatime * 1e-9;
            render(deltatime_s);
            window.swapBuffers();
        }
        destroy();
        Window.terminate();
    }

    public void setInputHandler(InputHandler handler) {
        window.setInputHandler(handler);
    }

    public void closeWindow() {
        window.close();
    }

    public Window getWindow() {
        return window;
    }


    public abstract void create();
    public abstract void render(double delta);
    public abstract void destroy();




    public static class Settings {
        private Window.Settings windowSettings;
        private boolean enableVSync = false;
        private String windowTitle = "Window";
        private int defaultMonitor = 0;
        private int width = -1, height = -1;
        private boolean fullscreen = false;


        public Settings(Window.Settings windowSettings) {
            this.windowSettings = windowSettings;
        }

        public Window.Settings getWindowSettings() {
            return windowSettings;
        }

        public Settings setWindowSettings(Window.Settings windowSettings) {
            this.windowSettings = windowSettings;
            return this;
        }

        public boolean usesVSync() {
            return enableVSync;
        }

        public Settings enableVSync(boolean enableVSync) {
            this.enableVSync = enableVSync;
            return this;
        }

        public String getWindowTitle() {
            return windowTitle;
        }

        public Settings setWindowTitle(String windowTitle) {
            this.windowTitle = windowTitle;
            return this;
        }

        public int getDefaultMonitor() {
            return defaultMonitor;
        }

        public Settings setDefaultMonitor(int defaultMonitor) {
            this.defaultMonitor = defaultMonitor;
            return this;
        }

        public int getWidth() {
            return width;
        }

        public Settings setWidth(int width) {
            this.width = width;
            return this;
        }

        public int getHeight() {
            return height;
        }

        public Settings setHeight(int height) {
            this.height = height;
            return this;
        }

        public Settings enableFullscreen(int monitorID) {
            this.defaultMonitor = monitorID;
            this.width = -1;
            this.height = -1;
            this.fullscreen = true;

            return this;
        }

        public Settings enableFullscreen(int monitorID, int width, int height) {
            this.defaultMonitor = monitorID;
            this.width = width;
            this.height = height;
            this.fullscreen = true;
            return this;
        }
    }
}
