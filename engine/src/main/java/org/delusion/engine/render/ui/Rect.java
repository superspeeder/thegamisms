package org.delusion.engine.render.ui;

import org.delusion.engine.render.RenderQueue;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.buffer.ElementBuffer;
import org.delusion.engine.render.buffer.VertexArray;
import org.delusion.engine.render.buffer.VertexBuffer;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.shape.Shape;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.utils.Utils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;


/**
 * Base rectangle. technically would work, but use the subclasses
 */
public abstract class Rect extends Group implements Shape {
    private static VertexBuffer vbo;
    private static ElementBuffer ebo;
    private static VertexArray vao;

    private Vector2f pos, size;
    private Matrix4f model;
    private boolean fill;
    private ShaderProgram sh;

    public static void init() {
        vbo = new VertexBuffer(List.of(
                -0.5f,-0.5f, 0.0f, 0.0f,
                 0.5f,-0.5f, 1.0f, 0.0f,
                 0.5f, 0.5f, 1.0f, 1.0f,
                -0.5f, 0.5f, 0.0f, 1.0f));
        ebo = new ElementBuffer(List.of(0,1,2,3));
        vao = new VertexArray().bindVertexBuffer(vbo, 2, 2).elementBuffer(ebo);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.setModel(model);
        renderer.useShader(sh);
        if (fill) {
            renderer.drawElements(Renderer.PrimitiveType.TriangleFan, vao, 4);
        } else {
            renderer.drawElements(Renderer.PrimitiveType.LineLoop, vao, 4);
        }
    }

    public Rect(Vector2f pos, Vector2f size, boolean fill, ShaderProgram sh) {
        this.pos = pos;
        this.size = size;
        this.fill = fill;
        this.sh = sh;
        model = new Matrix4f().identity().translate(new Vector3f(pos, 0.0f)).scale(new Vector3f(size, 1.0f));
    }

    @Override
    public void draw(RenderQueue rq, Batch batch) {
        super.draw(rq, batch);
        rq.queue(this);
    }

    @Override
    public boolean pointWithin(Vector2f pos) {
        return Utils.rectContains(this.pos, size, pos);
    }
}
