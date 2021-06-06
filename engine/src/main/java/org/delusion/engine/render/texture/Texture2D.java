package org.delusion.engine.render.texture;


import org.delusion.engine.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Texture2D {
    private int handle;
    private int width;
    private int height;

    public static void initTextures() {
        STBImage.stbi_set_flip_vertically_on_load(true);
    }

    public Texture2D(int width, int height, TexParams params) {
        this.width = width;
        this.height = height;
        handle = glCreateTextures(GL_TEXTURE_2D);
        bind();
        applySettings(params);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, NULL);
    }

    public Texture2D(String path, TexParams params) throws IOException {
        IntBuffer[] ibs = Utils.createIntBuffers(1,3);
        ByteBuffer data = STBImage.stbi_load_from_memory(Objects.requireNonNull(Utils.loadByteBufferResource(path), "Texture '" + path + "' is not valid. (no data exists in file)"), ibs[0], ibs[1], ibs[2], 4);

        if (data == null) {
            throw new IOException("Failed to load texture '" + path + "'");
        }

        handle = glCreateTextures(GL_TEXTURE_2D);
        bind();
        applySettings(params);
        width = ibs[0].rewind().get();
        height = ibs[0].rewind().get();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

        STBImage.stbi_image_free(data);
    }

    private void applySettings(TexParams params) {
        glTextureParameteri(handle, GL_TEXTURE_WRAP_S, params.wrapS.getVal());
        glTextureParameteri(handle, GL_TEXTURE_WRAP_S, params.wrapT.getVal());
        glTextureParameteri(handle, GL_TEXTURE_MIN_FILTER, params.minFilter.getVal());
        glTextureParameteri(handle, GL_TEXTURE_MAG_FILTER, params.magFilter.getVal());
    }

    public Vector2f pixelToUVs(Vector2f pixelCoords) {
        return new Vector2f(pixelCoords.x / (float)width, pixelCoords.y / (float)height);
    }


    public void bind() {
        glBindTexture(GL_TEXTURE_2D, handle);
    }

    public int getHandle() {
        return handle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector4f pixelToUVs(float u1, float v1, float u2, float v2) {
        Vector2f uv1 = pixelToUVs(new Vector2f(u1,v1));
        Vector2f uv2 = pixelToUVs(new Vector2f(u2,v2));
        return new Vector4f(uv1.x,uv1.y,uv2.x,uv2.y);
    }

    public enum WrapMode {

        Repeat(GL_REPEAT),
        MirroredRepeat(GL_MIRRORED_REPEAT),
        ClampToEdge(GL_CLAMP_TO_EDGE),
        ClampToBorder(GL_CLAMP_TO_BORDER)
        ;

        private final int val;

        WrapMode(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public enum Filter {
        Nearest(GL_NEAREST),
        Linear(GL_LINEAR)
        ;

        private final int val;

        Filter(int val) {

            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public static class TexParams {
        private WrapMode wrapS = WrapMode.Repeat, wrapT = WrapMode.Repeat;
        private Filter minFilter = Filter.Nearest, magFilter = Filter.Nearest;

        public WrapMode getWrapS() {
            return wrapS;
        }

        public TexParams setWrapS(WrapMode wrapS) {
            this.wrapS = wrapS;
            return this;
        }

        public WrapMode getWrapT() {
            return wrapT;
        }

        public TexParams setWrapT(WrapMode wrapT) {
            this.wrapT = wrapT;
            return this;
        }

        public TexParams setWrap(WrapMode wrapS, WrapMode wrapT) {
            this.wrapS = wrapS;
            this.wrapT = wrapT;
            return this;
        }

        public Filter getMinFilter() {
            return minFilter;
        }

        public TexParams setMinFilter(Filter minFilter) {
            this.minFilter = minFilter;
            return this;
        }

        public Filter getMagFilter() {
            return magFilter;
        }

        public TexParams setMagFilter(Filter magFilter) {
            this.magFilter = magFilter;
            return this;
        }

        public TexParams setFilter(Filter minFilter, Filter magFilter) {
            this.minFilter = minFilter;
            this.magFilter = magFilter;
            return this;
        }
    }

}
