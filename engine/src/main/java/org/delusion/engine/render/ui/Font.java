package org.delusion.engine.render.ui;

import org.delusion.engine.render.mesh.Mesh;
import org.delusion.engine.render.texture.Texture2D;
import org.delusion.engine.utils.Utils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glGetError;

public class Font {

    private Texture2D tex;
    private STBTTBakedChar.Buffer cdata;

    public Font(String file, int height) throws IOException {
        cdata = STBTTBakedChar.malloc(96);
        ByteBuffer data = Utils.loadByteBufferResource(file);
        ByteBuffer pixels = BufferUtils.createByteBuffer(512 * 512);

        STBTruetype.stbtt_BakeFontBitmap(data, height, pixels, 512, 512, 32, cdata);

//        byte[] rev_arr = new byte[512 * 512];
//        int i = 0;
//        while (pixels.hasRemaining()) {
//            int row = Math.floorDiv(i, 512);
//            int col = Math.floorMod(i, 512);
//
//            rev_arr[(512* (512 - row - 1)) + col] = pixels.get();
//            i++;
//        }
//
//        ByteBuffer pixels_reverse = BufferUtils.createByteBuffer(512 * 512);
//        pixels_reverse.put(rev_arr).rewind();

        tex = new Texture2D(512, 512, GL11.GL_RED, new Texture2D.TexParams().setFilter(Texture2D.Filter.Linear, Texture2D.Filter.Linear), pixels.rewind());

        STBImageWrite.stbi_write_bmp("test.png", 512, 512, 1, pixels.rewind());
    }

    public Texture2D getTex() {
        return tex;
    }

    // Quads are organized: bl, br, tr, tl
    // ind should be 0,2,3,0,1,2
    // Vertex is x,y,u,v
    // num quads is verts.size() / 16
    public List<Float> genQuadVertices(String text) {
        FloatBuffer x = BufferUtils.createFloatBuffer(1);
        FloatBuffer y = BufferUtils.createFloatBuffer(1);
        STBTTAlignedQuad q = STBTTAlignedQuad.malloc();
        List<Float> verts = new ArrayList<>();
        for (char c : text.toCharArray()) {
            if (c >= 32) {
                STBTruetype.stbtt_GetBakedQuad(cdata, 512, 512, c - 32, x.rewind(), y.rewind(), q, true);
                System.out.printf("%c: %f %f -> %f %f at %f %f to %f %f\n", c, q.s0(), q.t0(), q.s1(), q.t1(), q.x0(), q.y0(), q.x1(), q.y1());
                verts.addAll(List.of(
                        q.x0(), -q.y1(), q.s0(), q.t1(), // bl
                        q.x1(), -q.y1(), q.s1(), q.t1(), // br
                        q.x1(), -q.y0(), q.s1(), q.t0(), // tr
                        q.x0(), -q.y0(), q.s0(), q.t0() // tl
                ));
            }
        }
        System.out.println(verts.size());
        return verts;
    }
}
