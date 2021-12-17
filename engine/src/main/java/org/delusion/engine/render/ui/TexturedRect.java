package org.delusion.engine.render.ui;

import org.delusion.engine.math.AABB;
import org.delusion.engine.render.Color;
import org.delusion.engine.render.RenderQueue;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.buffer.ElementBuffer;
import org.delusion.engine.render.buffer.VertexArray;
import org.delusion.engine.render.buffer.VertexBuffer;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.shape.Shape;
import org.delusion.engine.render.texture.Texture2D;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.utils.Utils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;


/**
 * Base rectangle. technically would work, but use the subclasses
 */
public class TexturedRect extends Group implements Shape {
    protected Vector4f uvs;
    protected Texture2D tex;
    protected VertexBuffer vbo;
    protected static ElementBuffer ebo;
    protected VertexArray vao;

    protected Vector2f pos, size;
    protected Matrix4f model;
    protected boolean fill;
    protected ShaderProgram sh;

    protected Color tint = Color.WHITE;

    public static void init() {
        ebo = new ElementBuffer(List.of(0,1,2,3));
    }

    private AABB aabb;

    public Color getTint() {
        return tint;
    }

    public TexturedRect setTint(Color tint) {
        this.tint = tint;
        return this;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.setModel(model);
        renderer.useShader(sh);
        renderer.setTex(tex, 0);
        renderer.setUVs(uvs);
        renderer.setTint(tint);
        if (fill) {
            renderer.drawElements(Renderer.PrimitiveType.TriangleFan, vao, 4);
        } else {
            renderer.drawElements(Renderer.PrimitiveType.LineLoop, vao, 4);
        }
    }

    public TexturedRect(Vector2f pos, Vector2f size, boolean fill,  Texture2D tex, Vector4f uvs, ShaderProgram sh) {
        this(pos, size, fill, tex, uvs, Color.WHITE, sh);
    }

    public TexturedRect(Vector2f pos, Vector2f size, boolean fill,  Texture2D tex, Vector4f uvs, Color tint, ShaderProgram sh) {
        this.pos = pos;
        this.size = size;
        this.fill = fill;
        aabb = AABB.centerPos(pos, size);
        this.sh = sh;
        model = new Matrix4f().identity().translate(new Vector3f(pos, 0.0f)).scale(new Vector3f(size, 1.0f));
        vbo = new VertexBuffer(List.of(
                -0.5f,-0.5f, uvs.x, uvs.y,
                0.5f,-0.5f, uvs.z, uvs.y,
                0.5f, 0.5f, uvs.z, uvs.w,
                -0.5f, 0.5f, uvs.x, uvs.w));
        vao = new VertexArray().bindVertexBuffer(vbo, 2, 2).elementBuffer(ebo);
        this.tex = tex;
        this.uvs = uvs;

    }

    public void setUVs(Vector4f nuvs) {
        if (uvs.equals(nuvs)) return;
        uvs = nuvs;
        vbo.set(List.of(
                -0.5f,-0.5f, uvs.x, uvs.y,
                0.5f,-0.5f, uvs.z, uvs.y,
                0.5f, 0.5f, uvs.z, uvs.w,
                -0.5f, 0.5f, uvs.x, uvs.w));
        vbo.update();
    }

    @Override
    public void draw(RenderQueue rq, Batch batch) {
        rq.queue(this);
        super.draw(rq, batch);
    }

    @Override
    public boolean pointWithin(Vector2f pos) {
        if (!super.pointWithin(pos)) return false;
        if (aabb.containsPoint(pos)) {
//            System.out.printf("(%f, %f) within " + aabb + '\n', pos.x, pos.y);
            return true;
        }

//        System.out.printf("(%f, %f) not within " + aabb + '\n', pos.x, pos.y);
        return false;
    }
}
