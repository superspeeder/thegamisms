package org.delusion.engine.sprite;

import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.buffer.ElementBuffer;
import org.delusion.engine.render.buffer.VertexArray;
import org.delusion.engine.render.buffer.VertexBuffer;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.List;

public class Batch {
    public static int VERTEX_SIZE = 5;
    public static int MAX_QUADS = 10000;
    public static int VERTICES_PER_QUAD = 4;
    public static int INDICES_PER_QUAD = 6;
    public static int MAX_VERTICES = VERTICES_PER_QUAD * MAX_QUADS;
    public static int MAX_INDICES = INDICES_PER_QUAD * MAX_QUADS;
    public static int MAX_VBO_SIZE = MAX_VERTICES * VERTEX_SIZE * Float.BYTES;
    public static int MAX_EBO_SIZE = MAX_INDICES * Integer.BYTES;

    private static ElementBuffer ebo;
    private VertexBuffer vbo;
    private VertexArray vao;
    private int quadsBatched, verticesBatched;

    public static void initStatic() {
        int[] eboD = new int[MAX_INDICES];
        for (int i = 0 ; i < MAX_QUADS ; i++) {
            eboD[i * INDICES_PER_QUAD] = i * VERTICES_PER_QUAD;
            eboD[i * INDICES_PER_QUAD + 1] = i * VERTICES_PER_QUAD + 1;
            eboD[i * INDICES_PER_QUAD + 2] = i * VERTICES_PER_QUAD + 2;

            eboD[i * INDICES_PER_QUAD + 3] = i * VERTICES_PER_QUAD + 3;
            eboD[i * INDICES_PER_QUAD + 4] = i * VERTICES_PER_QUAD + 2;
            eboD[i * INDICES_PER_QUAD + 5] = i * VERTICES_PER_QUAD + 1;
        }
        ebo =  new ElementBuffer(eboD);
        System.out.println("Created EBO with " + eboD.length + " indices (" + ((float)(eboD.length * Integer.BYTES) / 1e6) + " mb)");
    }

    public Batch() {
        vbo = new VertexBuffer(MAX_VBO_SIZE);
        vao = new VertexArray().bindVertexBuffer(vbo, 3, 2).elementBuffer(ebo);
        quadsBatched = 0;
        verticesBatched = 0;
    }

    public Batch begin() {
        quadsBatched = 0;
        verticesBatched = 0;
        return this;
    }

    public void end() {
        vbo.update();
    }

    public void draw(Renderer renderer) {
        if (quadsBatched < 0) return;
        renderer.drawElements(Renderer.PrimitiveType.Triangles, vao, quadsBatched * 6);
    }

    public Batch batch(Vector2f position, Vector2f size, Vector4f uvs) {
        if (quadsBatched >= MAX_QUADS) {
            System.out.println("Fuck"); return this; }
        addVertices(
                position.x,position.y,0.0f, uvs.x, uvs.y,
                position.x + size.x, position.y, 0.0f, uvs.z, uvs.y,
                position.x, position.y + size.y, 0.0f, uvs.x, uvs.w,
                position.x + size.x, position.y + size.y, 0.0f, uvs.z, uvs.w);

        quadsBatched += 1;

        return this;
    }

    private void addVertices(float... values) {
        for (float v : values) {
            vbo.set(verticesBatched++, v);
        }
    }


}
