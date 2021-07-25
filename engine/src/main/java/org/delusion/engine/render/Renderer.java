package org.delusion.engine.render;

import org.delusion.engine.App;
import org.delusion.engine.render.buffer.VertexArray;
import org.delusion.engine.render.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {

    private Matrix4f model = new Matrix4f().identity();
    private Color foregroundColor;
    private App app;
    private Color backgroundColor;
    private ShaderProgram currentProgram;
    private Matrix4f viewProjection = new Matrix4f().identity();

    public void useShader(ShaderProgram prog) {
        currentProgram = prog;
        prog.use();
    }

    public void setModel(Matrix4f model) {

        this.model = model;
    }

    public Matrix4f getModel() {
        return model;
    }

    public void setForegroundColor(Color color) {
        foregroundColor = color;
    }

    public void setViewProjection(Matrix4f combined) {
        viewProjection = combined;
    }

    public enum PrimitiveType {
        Triangles(GL_TRIANGLES),
        TriangleFan(GL_TRIANGLE_FAN),
        TriangleStrip(GL_TRIANGLE_STRIP),
        TriangleStripAdjacency(GL_TRIANGLE_STRIP_ADJACENCY),
        Point(GL_POINT),
        Line(GL_LINE),
        LineLoop(GL_LINE_LOOP),
        LineStrip(GL_LINE_STRIP),
        LineStripAdjacency(GL_LINE_STRIP_ADJACENCY),
        ;

        private final int val;

        PrimitiveType(int val) {

            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }



    public Renderer(App app) {
        this.app = app;
        this.backgroundColor = Color.WHITE;
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
        glClearColor(backgroundColor.r(),backgroundColor.g(),backgroundColor.b(),backgroundColor.a());
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void clearScreen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void drawArray(PrimitiveType type, VertexArray array, int first, int count) {
        preRender();
        array.bind();
        glDrawArrays(type.getVal(), first, count);
    }

    public void drawArray(PrimitiveType type, VertexArray array, int count) {
        preRender();
        drawArray(type, array,0,count);
    }

    public void drawElements(PrimitiveType type, VertexArray array, int count) {
        preRender();
        array.bind();
        glDrawElements(type.getVal(), count, GL_UNSIGNED_INT, NULL);
    }

    private void preRender() {
        currentProgram.uniformColor("uColor", foregroundColor);
        currentProgram.uniformMat4("uModel", model);
        currentProgram.uniformMat4("uViewProjection", viewProjection);
    }

    public void drawElements(PrimitiveType type, VertexArray array, int count, long offset) {
        preRender();
        array.bind();
        glDrawElements(type.getVal(), count, GL_UNSIGNED_INT, offset * 4L);
    }

}
