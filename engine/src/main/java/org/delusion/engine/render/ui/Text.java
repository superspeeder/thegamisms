package org.delusion.engine.render.ui;
import org.delusion.engine.render.Color;
import org.delusion.engine.render.RenderQueue;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.buffer.ElementBuffer;
import org.delusion.engine.render.buffer.VertexArray;
import org.delusion.engine.render.buffer.VertexBuffer;
import org.delusion.engine.render.shader.Shader;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.shape.Shape;
import org.delusion.engine.sprite.Batch;
import org.delusion.engine.utils.Utils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL45;
import org.lwjgl.stb.STBTruetype;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


public class Text extends Node implements Shape {
    private String text;
    private Font font;
    private ShaderProgram shader;
    private VertexArray vao;
    private VertexBuffer vbo;
    private ElementBuffer ebo;
    private Vector2f pos;
    private Color textColor;

    public Text(String text, Font font, Vector2f pos, ShaderProgram shader) {
        this(text, font,pos, shader, Color.WHITE);
    }

    public Text(String text, Font font, Vector2f pos, ShaderProgram shader, Color color) {
        this.text = text;
        this.font = font;
        this.pos = pos;
        this.shader = shader;
        vbo = new VertexBuffer(font.genQuadVertices(text));
        List<Integer> ebov = new ArrayList<>();
        for (int i = 0 ; i < text.length() ; i++) {
            ebov.addAll(Utils.offsetAll(i * 4, 0,1,2,0,3,1));
        }
        ebo = new ElementBuffer(ebov);
        vao = new VertexArray().bindVertexBuffer(vbo, 2,2).elementBuffer(ebo);
        textColor = color;
    }


    public void setText(String newtext) {
        vbo.set(font.genQuadVertices(newtext));
        List<Integer> ib = new ArrayList<>();
        for (int i = 0 ; i < newtext.length() ; i++) {
            ib.add(i * 4);
            ib.add(i * 4 + 2);
            ib.add(i * 4 + 3);
            ib.add(i * 4);
            ib.add(i * 4 + 1);
            ib.add(i * 4 + 2);
        }
        ebo.set(ib);
        vbo.update();
        text = newtext;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.useShader(shader);
        renderer.setTex(font.getTex());
        renderer.setModel(new Matrix4f().translate(new Vector3f(pos.x, pos.y, 0.0f)));
        renderer.setForegroundColor(textColor);
        renderer.drawElements(Renderer.PrimitiveType.Triangles, vao, 6 * text.length());
    }

    @Override
    public void draw(RenderQueue rq, Batch batch) {
        if (isEnabled()) rq.queue(this);
    }
}
