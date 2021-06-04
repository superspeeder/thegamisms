package org.delusion.engine.window;

import org.delusion.engine.utils.Utils;
import org.lwjgl.glfw.GLFWVidMode;

public class VideoMode {
    private int width;
    private int height;
    private int redBits;
    private int greenBits;
    private int blueBits;
    private int refreshRate;

    VideoMode(GLFWVidMode vidMode) {
        width = vidMode.width();
        height = vidMode.height();
        redBits = vidMode.redBits();
        greenBits = vidMode.greenBits();
        blueBits = vidMode.blueBits();
        refreshRate = vidMode.refreshRate();
    }

    private VideoMode(int width, int height, int redBits, int greenBits, int blueBits, int refreshRate) {
        this.width = width;
        this.height = height;
        this.redBits = redBits;
        this.greenBits = greenBits;
        this.blueBits = blueBits;
        this.refreshRate = refreshRate;
    }

    public VideoMode withSize(int width, int height) {
        Utils.requirePositive(width, "Cannot create video mode with negative width");
        Utils.requirePositive(height, "Cannot create video mode with negative height");

        VideoMode copy = cpy();
        copy.width = width;
        copy.height = height;
        return copy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRedBits() {
        return redBits;
    }

    public int getGreenBits() {
        return greenBits;
    }

    public int getBlueBits() {
        return blueBits;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public VideoMode cpy()  {
        return new VideoMode(width,height,redBits,greenBits,blueBits,refreshRate);
    }


}
