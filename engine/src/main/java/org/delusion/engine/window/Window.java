package org.delusion.engine.window;

import org.delusion.engine.utils.Utils;
import org.delusion.engine.window.input.InputHandler;
import org.delusion.engine.window.input.Key;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long handle;
    private InputHandler inputHandler = new InputHandler() {
        @Override
        public void onKeyPressed(Key key, int mods, int scancode) {
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
    };
    private int width, height;

    /**
     * Internal use constructor. used to copy glfw window handles into the window class.
     * This is a weak reference to the window, so if the handle is shared by multiple window objects it may be destroyed before it should be
     *
     * @param handle Internal handle returned from {@link org.lwjgl.glfw.GLFW#glfwCreateWindow(int, int, CharSequence, long, long)}
     */
    Window(long handle) {
        this.handle = handle;
    }

    /**
     * Apply settings
     *
     * @param settings
     */
    private static void applySettings(Settings settings) {
        glfwWindowHint(GLFW_RESIZABLE, settings.resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, settings.visible ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, settings.decorated ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUSED, settings.focused ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_AUTO_ICONIFY, settings.autoIconify ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_FLOATING, settings.floating ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, settings.maximized ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CENTER_CURSOR, settings.centerCursor ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, settings.transparentFramebuffer ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, settings.focusOnShow ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_SCALE_TO_MONITOR, settings.scaleToMonitor ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_RED_BITS, settings.redBits);
        glfwWindowHint(GLFW_GREEN_BITS, settings.greenBits);
        glfwWindowHint(GLFW_BLUE_BITS, settings.blueBits);
        glfwWindowHint(GLFW_ALPHA_BITS, settings.alphaBits);
        glfwWindowHint(GLFW_DEPTH_BITS, settings.depthBits);
        glfwWindowHint(GLFW_STENCIL_BITS, settings.stencilBits);
        glfwWindowHint(GLFW_ACCUM_RED_BITS, settings.accumRedBits);
        glfwWindowHint(GLFW_ACCUM_GREEN_BITS, settings.accumGreenBits);
        glfwWindowHint(GLFW_ACCUM_BLUE_BITS, settings.accumBlueBits);
        glfwWindowHint(GLFW_ACCUM_ALPHA_BITS, settings.accumAlphaBits);
        glfwWindowHint(GLFW_REFRESH_RATE, settings.refreshRate);
        glfwWindowHint(GLFW_AUX_BUFFERS, settings.auxBuffers);
        glfwWindowHint(GLFW_SAMPLES, settings.samples);
        glfwWindowHint(GLFW_STEREO, settings.stereo ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_SRGB_CAPABLE, settings.srgbCapable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_DOUBLEBUFFER, settings.doubleBuffer ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CLIENT_API, settings.clientAPI.getVal());
        glfwWindowHint(GLFW_CONTEXT_CREATION_API, settings.contextCreationAPI.getVal());
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, settings.contextVersionMajor);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, settings.contextVersionMinor);
        glfwWindowHint(GLFW_CONTEXT_ROBUSTNESS, settings.contextRobustness.getVal());
        glfwWindowHint(GLFW_CONTEXT_RELEASE_BEHAVIOR, settings.contextReleaseBehavior.getVal());
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, settings.openglForwardCompat ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, settings.openglDebugContext ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, settings.openGLProfile.getVal());
        glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, settings.cocoaRetinaFramebuffer ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHintString(GLFW_COCOA_FRAME_NAME, settings.cocoaFrameName);
        glfwWindowHint(GLFW_COCOA_GRAPHICS_SWITCHING, settings.cocoaGraphicsSwitching ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHintString(GLFW_X11_CLASS_NAME, settings.x11ClassName);
        glfwWindowHintString(GLFW_X11_INSTANCE_NAME, settings.x11InstanceName);
    }

    /**
     * Apply settings and use monitor video mode
     *
     * @param settings
     * @param monitor
     */
    private static void applySettings(Settings settings, Monitor monitor) {
        VideoMode videoMode = monitor.getVideoMode();
        applySettings(settings.withVideoMode(videoMode));
    }

    public Window(Settings settings, int width, int height, String title) {
        glfwDefaultWindowHints();
        applySettings(settings);
        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        this.width = width;
        this.height = height;
        makeContextCurrent();
        initInputs();
    }


    public Window(Settings settings, int width, int height, String title, Monitor monitor) {
        glfwDefaultWindowHints();
        applySettings(settings, monitor);
        handle = glfwCreateWindow(width, height, title, monitor.handle(), NULL);
        this.width = width;
        this.height = height;
        makeContextCurrent();
        initInputs();
    }

    public Window(Settings settings, int width, int height, String title, Window share) {
        glfwDefaultWindowHints();
        applySettings(settings);
        handle = glfwCreateWindow(width, height, title, NULL, share.handle);
        this.width = width;
        this.height = height;
        makeContextCurrent();
        initInputs();
    }

    public Window(Settings settings, int width, int height, String title, Monitor monitor, Window share) {
        glfwDefaultWindowHints();
        applySettings(settings, monitor);
        handle = glfwCreateWindow(width, height, title, monitor.handle(), share.handle);
        this.width = width;
        this.height = height;
        makeContextCurrent();
        initInputs();
    }

    public Window(Settings settings, String title, Monitor monitor) {
        glfwDefaultWindowHints();
        applySettings(settings, monitor);
        VideoMode videoMode = monitor.getVideoMode();
        handle = glfwCreateWindow(videoMode.getWidth(), videoMode.getHeight(), title, monitor.handle(), NULL);
        this.width = videoMode.getWidth();
        this.height = videoMode.getHeight();
        makeContextCurrent();
        initInputs();
    }

    public Window(Settings settings, String title, Monitor monitor, Window share) {
        glfwDefaultWindowHints();
        applySettings(settings, monitor);
        VideoMode videoMode = monitor.getVideoMode();
        handle = glfwCreateWindow(videoMode.getWidth(), videoMode.getHeight(), title, monitor.handle(), share.handle);
        this.width = videoMode.getWidth();
        this.height = videoMode.getHeight();
        makeContextCurrent();
        initInputs();
    }

    private void initInputs() {
        Key.init();
        glfwSetKeyCallback(handle, this::kcbk);
        glfwSetMouseButtonCallback(handle, this::mbcbk);
        glfwSetCharCallback(handle, this::chartypecbk);
        glfwSetCharModsCallback(handle, this::charmodstypecbk);
        glfwSetCursorPosCallback(handle, this::mousemotioncbk);

    }

    private void kcbk(long window, int key,int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            getInputHandler().onKeyPressed(Key.get(key), mods, scancode);
        } else if (action == GLFW_RELEASE) {
            getInputHandler().onKeyReleased(Key.get(key), mods, scancode);
        }
    }

    private void mbcbk(long window, int mb, int action, int mods) {
        if (action == GLFW_PRESS) {
            getInputHandler().onClick(mb, mods);
        } else if (action == GLFW_RELEASE) {
            getInputHandler().onClickRelease(mb, mods);
        }
    }

    private void chartypecbk(long window, int codepoint) {
        StringBuilder sb = new StringBuilder();
        for (char c : Character.toChars(codepoint)) {
            sb.append(c);
        }
        getInputHandler().onCharTyped(sb.toString());
    }

    private void charmodstypecbk(long window, int codepoint, int mods) {
        StringBuilder sb = new StringBuilder();
        for (char c : Character.toChars(codepoint)) {
            sb.append(c);
        }
        getInputHandler().onCharTypedMods(sb.toString(), mods);
    }

    private void mousemotioncbk(long window, double xpos, double ypos) {
        getInputHandler().onMouseMotion(new Vector2d(xpos, height - ypos));
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(handle);
    }

    public void swapBuffers() {
        glfwSwapBuffers(handle);
    }

    public static void pollEvents() {
        glfwPollEvents();
    }

    public void makeContextCurrent() {
        glfwMakeContextCurrent(handle);
        GL.createCapabilities();
    }

    public static void init() {
        glfwInit();
    }

    public static void terminate() {
        glfwTerminate();
    }

    public void setFloating(boolean floating) {
        glfwSetWindowAttrib(handle, GLFW_FLOATING, floating ? GLFW_TRUE : GLFW_FALSE);
    }


    public void close() {
        glfwSetWindowShouldClose(handle, true);
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void setVSyncEnabled(boolean enableVSync) {
        if (enableVSync) {
            glfwSwapInterval(1);
        } else {
            glfwSwapInterval(0);
        }
    }

    public void hideCursor() {
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    public void showCursor() {
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public void disableCursor() {
        glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public Vector2i getSize() {
        IntBuffer[] buffers = Utils.createIntBuffers(1,2);
        glfwGetWindowSize(handle, buffers[0], buffers[1]);
        return new Vector2i(buffers[0].rewind().get(), buffers[1].rewind().get());
    }

    public float getTime() {
        return (float) glfwGetTime();
    }

    public boolean getKey(Key key) {
        return glfwGetKey(handle, key.getID()) == GLFW_PRESS;
    }


    public static class Settings {
        private boolean resizable = true;
        private boolean visible = true;
        private boolean decorated = true;
        private boolean focused = true;
        private boolean autoIconify = true;
        private boolean floating = false;
        private boolean maximized = false;
        private boolean centerCursor = true;
        private boolean transparentFramebuffer = false;
        private boolean focusOnShow = true;
        private boolean scaleToMonitor = false;
        private int redBits = 8;
        private int greenBits = 8;
        private int blueBits = 8;
        private int alphaBits = 8;
        private int depthBits = 24;
        private int stencilBits = 8;
        private int accumRedBits = 0;
        private int accumGreenBits = 0;
        private int accumBlueBits = 0;
        private int accumAlphaBits = 0;
        private int auxBuffers = 0;
        private int samples = 0;
        private int refreshRate = GLFW_DONT_CARE;
        private boolean stereo = false;
        private boolean srgbCapable = false;
        private boolean doubleBuffer = true;
        private ClientAPI clientAPI = ClientAPI.OpenGL;
        private ContextCreationAPI contextCreationAPI = ContextCreationAPI.Native;
        private int contextVersionMajor = 4;
        private int contextVersionMinor = 6;
        private ContextRobustness contextRobustness = ContextRobustness.None;
        private ContextReleaseBehavior contextReleaseBehavior = ContextReleaseBehavior.Any;
        private boolean openglForwardCompat = true;
        private boolean openglDebugContext = false;
        private OpenGLProfile openGLProfile = OpenGLProfile.Core;
        private boolean cocoaRetinaFramebuffer = true;
        private String cocoaFrameName = "";
        private boolean cocoaGraphicsSwitching = false;
        private String x11ClassName = "";
        private String x11InstanceName = "";

        public Settings(boolean resizable, boolean visible, boolean decorated, boolean focused, boolean autoIconify, boolean floating, boolean maximized, boolean centerCursor, boolean transparentFramebuffer, boolean focusOnShow, boolean scaleToMonitor, int redBits, int greenBits, int blueBits, int alphaBits, int depthBits, int stencilBits, int accumRedBits, int accumGreenBits, int accumBlueBits, int accumAlphaBits, int auxBuffers, int samples, int refreshRate, boolean stereo, boolean srgbCapable, boolean doubleBuffer, ClientAPI clientAPI, ContextCreationAPI contextCreationAPI, int contextVersionMajor, int contextVersionMinor, ContextRobustness contextRobustness, ContextReleaseBehavior contextReleaseBehavior, boolean openglForwardCompat, boolean openglDebugContext, OpenGLProfile openGLProfile, boolean cocoaRetinaFramebuffer, String cocoaFrameName, boolean cocoaGraphicsSwitching, String x11ClassName, String x11InstanceName) {
            this.resizable = resizable;
            this.visible = visible;
            this.decorated = decorated;
            this.focused = focused;
            this.autoIconify = autoIconify;
            this.floating = floating;
            this.maximized = maximized;
            this.centerCursor = centerCursor;
            this.transparentFramebuffer = transparentFramebuffer;
            this.focusOnShow = focusOnShow;
            this.scaleToMonitor = scaleToMonitor;
            this.redBits = redBits;
            this.greenBits = greenBits;
            this.blueBits = blueBits;
            this.alphaBits = alphaBits;
            this.depthBits = depthBits;
            this.stencilBits = stencilBits;
            this.accumRedBits = accumRedBits;
            this.accumGreenBits = accumGreenBits;
            this.accumBlueBits = accumBlueBits;
            this.accumAlphaBits = accumAlphaBits;
            this.auxBuffers = auxBuffers;
            this.samples = samples;
            this.refreshRate = refreshRate;
            this.stereo = stereo;
            this.srgbCapable = srgbCapable;
            this.doubleBuffer = doubleBuffer;
            this.clientAPI = clientAPI;
            this.contextCreationAPI = contextCreationAPI;
            this.contextVersionMajor = contextVersionMajor;
            this.contextVersionMinor = contextVersionMinor;
            this.contextRobustness = contextRobustness;
            this.contextReleaseBehavior = contextReleaseBehavior;
            this.openglForwardCompat = openglForwardCompat;
            this.openglDebugContext = openglDebugContext;
            this.openGLProfile = openGLProfile;
            this.cocoaRetinaFramebuffer = cocoaRetinaFramebuffer;
            this.cocoaFrameName = cocoaFrameName;
            this.cocoaGraphicsSwitching = cocoaGraphicsSwitching;
            this.x11ClassName = x11ClassName;
            this.x11InstanceName = x11InstanceName;
        }

        public Settings() {
        }

        public boolean isResizable() {
            return resizable;
        }

        public Settings setResizable(boolean resizable) {
            this.resizable = resizable;
            return this;
        }

        public boolean isVisible() {
            return visible;
        }

        public Settings setVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public boolean isDecorated() {
            return decorated;
        }

        public Settings setDecorated(boolean decorated) {
            this.decorated = decorated;
            return this;
        }

        public boolean isFocused() {
            return focused;
        }

        public Settings setFocused(boolean focused) {
            this.focused = focused;
            return this;
        }

        public boolean isAutoIconify() {
            return autoIconify;
        }

        public Settings setAutoIconify(boolean autoIconify) {
            this.autoIconify = autoIconify;
            return this;
        }

        public boolean isFloating() {
            return floating;
        }

        public Settings setFloating(boolean floating) {
            this.floating = floating;
            return this;
        }

        public boolean isMaximized() {
            return maximized;
        }

        public Settings setMaximized(boolean maximized) {
            this.maximized = maximized;
            return this;
        }

        public boolean isCenterCursor() {
            return centerCursor;
        }

        public Settings setCenterCursor(boolean centerCursor) {
            this.centerCursor = centerCursor;
            return this;
        }

        public boolean isTransparentFramebuffer() {
            return transparentFramebuffer;
        }

        public Settings setTransparentFramebuffer(boolean transparentFramebuffer) {
            this.transparentFramebuffer = transparentFramebuffer;
            return this;
        }

        public boolean isFocusOnShow() {
            return focusOnShow;
        }

        public Settings setFocusOnShow(boolean focusOnShow) {
            this.focusOnShow = focusOnShow;
            return this;
        }

        public boolean isScaleToMonitor() {
            return scaleToMonitor;
        }

        public Settings setScaleToMonitor(boolean scaleToMonitor) {
            this.scaleToMonitor = scaleToMonitor;
            return this;
        }

        public int getRedBits() {
            return redBits;
        }

        public Settings setRedBits(int redBits) {
            this.redBits = redBits;
            return this;
        }

        public int getGreenBits() {
            return greenBits;
        }

        public Settings setGreenBits(int greenBits) {
            this.greenBits = greenBits;
            return this;
        }

        public int getBlueBits() {
            return blueBits;
        }

        public Settings setBlueBits(int blueBits) {
            this.blueBits = blueBits;
            return this;
        }

        public int getAlphaBits() {
            return alphaBits;
        }

        public Settings setAlphaBits(int alphaBits) {
            this.alphaBits = alphaBits;
            return this;
        }

        public int getDepthBits() {
            return depthBits;
        }

        public Settings setDepthBits(int depthBits) {
            this.depthBits = depthBits;
            return this;
        }

        public int getStencilBits() {
            return stencilBits;
        }

        public Settings setStencilBits(int stencilBits) {
            this.stencilBits = stencilBits;
            return this;
        }

        public int getAccumRedBits() {
            return accumRedBits;
        }

        public Settings setAccumRedBits(int accumRedBits) {
            this.accumRedBits = accumRedBits;
            return this;
        }

        public int getAccumGreenBits() {
            return accumGreenBits;
        }

        public Settings setAccumGreenBits(int accumGreenBits) {
            this.accumGreenBits = accumGreenBits;
            return this;
        }

        public int getAccumBlueBits() {
            return accumBlueBits;
        }

        public Settings setAccumBlueBits(int accumBlueBits) {
            this.accumBlueBits = accumBlueBits;
            return this;
        }

        public int getAccumAlphaBits() {
            return accumAlphaBits;
        }

        public Settings setAccumAlphaBits(int accumAlphaBits) {
            this.accumAlphaBits = accumAlphaBits;
            return this;
        }

        public int getAuxBuffers() {
            return auxBuffers;
        }

        public Settings setAuxBuffers(int auxBuffers) {
            this.auxBuffers = auxBuffers;
            return this;
        }

        public int getSamples() {
            return samples;
        }

        public Settings setSamples(int samples) {
            this.samples = samples;
            return this;
        }

        public int getRefreshRate() {
            return refreshRate;
        }

        public Settings setRefreshRate(int refreshRate) {
            this.refreshRate = refreshRate;
            return this;
        }

        public boolean isStereo() {
            return stereo;
        }

        public Settings setStereo(boolean stereo) {
            this.stereo = stereo;
            return this;
        }

        public boolean isSrgbCapable() {
            return srgbCapable;
        }

        public Settings setSrgbCapable(boolean srgbCapable) {
            this.srgbCapable = srgbCapable;
            return this;
        }

        public boolean isDoubleBuffer() {
            return doubleBuffer;
        }

        public Settings setDoubleBuffer(boolean doubleBuffer) {
            this.doubleBuffer = doubleBuffer;
            return this;
        }

        public ClientAPI getClientAPI() {
            return clientAPI;
        }

        public Settings setClientAPI(ClientAPI clientAPI) {
            this.clientAPI = clientAPI;
            return this;
        }

        public ContextCreationAPI getContextCreationAPI() {
            return contextCreationAPI;
        }

        public Settings setContextCreationAPI(ContextCreationAPI contextCreationAPI) {
            this.contextCreationAPI = contextCreationAPI;
            return this;
        }

        public int getContextVersionMajor() {
            return contextVersionMajor;
        }

        public Settings setContextVersionMajor(int contextVersionMajor) {
            this.contextVersionMajor = contextVersionMajor;
            return this;
        }

        public int getContextVersionMinor() {
            return contextVersionMinor;
        }

        public Settings setContextVersionMinor(int contextVersionMinor) {
            this.contextVersionMinor = contextVersionMinor;
            return this;
        }

        public ContextRobustness getContextRobustness() {
            return contextRobustness;
        }

        public Settings setContextRobustness(ContextRobustness contextRobustness) {
            this.contextRobustness = contextRobustness;
            return this;
        }

        public ContextReleaseBehavior getContextReleaseBehavior() {
            return contextReleaseBehavior;
        }

        public Settings setContextReleaseBehavior(ContextReleaseBehavior contextReleaseBehavior) {
            this.contextReleaseBehavior = contextReleaseBehavior;
            return this;
        }

        public boolean isOpenglForwardCompat() {
            return openglForwardCompat;
        }

        public Settings setOpenglForwardCompat(boolean openglForwardCompat) {
            this.openglForwardCompat = openglForwardCompat;
            return this;
        }

        public boolean isOpenglDebugContext() {
            return openglDebugContext;
        }

        public Settings setOpenglDebugContext(boolean openglDebugContext) {
            this.openglDebugContext = openglDebugContext;
            return this;
        }

        public OpenGLProfile getOpenGLProfile() {
            return openGLProfile;
        }

        public Settings setOpenGLProfile(OpenGLProfile openGLProfile) {
            this.openGLProfile = openGLProfile;
            return this;
        }

        public boolean isCocoaRetinaFramebuffer() {
            return cocoaRetinaFramebuffer;
        }

        public Settings setCocoaRetinaFramebuffer(boolean cocoaRetinaFramebuffer) {
            this.cocoaRetinaFramebuffer = cocoaRetinaFramebuffer;
            return this;
        }

        public String getCocoaFrameName() {
            return cocoaFrameName;
        }

        public Settings setCocoaFrameName(String cocoaFrameName) {
            this.cocoaFrameName = cocoaFrameName;
            return this;
        }

        public boolean isCocoaGraphicsSwitching() {
            return cocoaGraphicsSwitching;
        }

        public Settings setCocoaGraphicsSwitching(boolean cocoaGraphicsSwitching) {
            this.cocoaGraphicsSwitching = cocoaGraphicsSwitching;
            return this;
        }

        public String getX11ClassName() {
            return x11ClassName;
        }

        public Settings setX11ClassName(String x11ClassName) {
            this.x11ClassName = x11ClassName;
            return this;
        }

        public String getX11InstanceName() {
            return x11InstanceName;
        }

        public Settings setX11InstanceName(String x11InstanceName) {
            this.x11InstanceName = x11InstanceName;
            return this;
        }

        public Settings withVideoMode(VideoMode videoMode) {
            Settings new_ = cpy();
            new_.redBits = videoMode.getRedBits();
            new_.greenBits = videoMode.getGreenBits();
            new_.blueBits = videoMode.getBlueBits();
            new_.refreshRate = videoMode.getRefreshRate();
            return new_;
        }

        public Settings cpy() {
            return new Settings(
                    resizable, visible, decorated, focused, autoIconify, floating, maximized, centerCursor,
                    transparentFramebuffer, focusOnShow, scaleToMonitor, redBits, greenBits, blueBits, alphaBits,
                    depthBits, stencilBits, accumRedBits, accumGreenBits, accumBlueBits, accumAlphaBits, auxBuffers,
                    samples, refreshRate, stereo, srgbCapable, doubleBuffer, clientAPI, contextCreationAPI,
                    contextVersionMajor, contextVersionMinor, contextRobustness, contextReleaseBehavior,
                    openglForwardCompat, openglDebugContext, openGLProfile, cocoaRetinaFramebuffer, cocoaFrameName,
                    cocoaGraphicsSwitching, x11ClassName, x11InstanceName
            );
        }


    }
}
