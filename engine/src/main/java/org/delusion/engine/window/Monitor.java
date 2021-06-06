package org.delusion.engine.window;

import org.delusion.engine.math.Rect2i;
import org.delusion.engine.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;

public class Monitor {
    private long handle;

    /**
     * @param handle the handle of the monitor returned by getMonitors
     */
    Monitor(long handle) {
        this.handle = handle;
    }

    public static Monitor getMonitor(int index) {
        PointerBuffer monitorset = Objects.requireNonNull(glfwGetMonitors(),
                "There are no valid connected monitors");

        if (index <= monitorset.limit()) {
            return new Monitor(monitorset.get(index));
        } else {
            throw new IllegalStateException(index + " is not a valid monitor index");
        }
    }

    public static Monitor getMonitorOrPrimary(int index) {
        try {
            return getMonitor(index);
        } catch (IllegalStateException | IndexOutOfBoundsException e) {
            return getPrimaryMonitor();
        }
    }

    public static Monitor getPrimaryMonitor() {
        return new Monitor(glfwGetPrimaryMonitor());
    }

    public static List<Monitor> getMonitors() {
        PointerBuffer monitorset = Objects.requireNonNull(glfwGetMonitors(),
                "Couldn't obtain monitors");
        List<Monitor> monitors = new ArrayList<>();
        while (monitorset.hasRemaining()) {
            monitors.add(new Monitor(monitorset.get()));
        }
        return monitors;
    }

    public VideoMode getVideoMode() {
        GLFWVidMode vidMode = glfwGetVideoMode(handle);
        return new VideoMode(Objects.requireNonNull(vidMode, "Failed to obtain the video mode of monitor " + handle));
    }

    public List<VideoMode> getVideoModes() {
        GLFWVidMode.Buffer glfwVidModes = Objects.requireNonNull(glfwGetVideoModes(handle), "Failed to obtain a list of video modes for monitor " + handle);
        List<VideoMode> videoModes = new ArrayList<>();
        glfwVidModes.forEach(vm -> videoModes.add(new VideoMode(vm)));
        return videoModes;
    }

    public Vector2i getPhysicalSize() {
        IntBuffer[] buffers = Utils.createIntBuffers(1,2);
        glfwGetMonitorPhysicalSize(handle, buffers[0], buffers[1]);
        return new Vector2i(buffers[0].rewind().get(), buffers[1].rewind().get());
    }

    public Vector2f getContentScale() {
        FloatBuffer[] buffers = Utils.createFloatBuffers(1,2);
        glfwGetMonitorContentScale(handle, buffers[0], buffers[1]);
        return new Vector2f(buffers[0].rewind().get(), buffers[1].rewind().get());
    }

    public Vector2i getPos() {
        IntBuffer[] buffers = Utils.createIntBuffers(1,2);
        glfwGetMonitorPos(handle, buffers[0], buffers[1]);
        return new Vector2i(buffers[0].rewind().get(), buffers[1].rewind().get());
    }

    public Rect2i getWorkarea() {
        IntBuffer[] buffers = Utils.createIntBuffers(1,4);
        glfwGetMonitorWorkarea(handle, buffers[0], buffers[1], buffers[2], buffers[3]);
        return new Rect2i(
                buffers[0].rewind().get(),buffers[1].rewind().get(),
                buffers[2].rewind().get(),buffers[3].rewind().get());
    }

    public String getName() {
        return glfwGetMonitorName(handle);
    }

    @Override
    public String toString() {
        return "<Monitor " + getName() + ">";
    }

    public void setGamma(float gamma) {
        glfwSetGamma(handle, gamma);
    }

    long handle() {
        return handle;
    }
}
