package org.delusion.engine.render.shader;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class ShaderProgram {
    private int handle;

    public ShaderProgram(Shader... shaders) {
        handle = glCreateProgram();
        for (Shader sh : shaders) {
            glAttachShader(handle, sh.getHandle());
        }

        glLinkProgram(handle);
        if (glGetProgrami(handle, GL_LINK_STATUS) != GL_TRUE) {
            System.out.println(glGetProgramInfoLog(handle));
        }
    }

    public void use() {
        glUseProgram(handle);
    }

    private int getUniformLocation(String name) {
        return glGetUniformLocation(handle, name);
    }

    public ShaderProgram uniform1f(String name, float x) {
        glProgramUniform1f(handle, getUniformLocation(name), x);
        return this;
    }

    public ShaderProgram uniform2f(String name, float x, float y) {
        glProgramUniform2f(handle, getUniformLocation(name), x, y);
        return this;
    }

    public ShaderProgram uniform3f(String name, float x, float y, float z) {
        glProgramUniform3f(handle, getUniformLocation(name), x, y, z);
        return this;
    }

    public ShaderProgram uniform4f(String name, float x, float y, float z, float w) {
        glProgramUniform4f(handle, getUniformLocation(name), x, y, z, w);
        return this;
    }

    public ShaderProgram uniform1d(String name, double x) {
        glProgramUniform1d(handle, getUniformLocation(name), x);
        return this;
    }

    public ShaderProgram uniform2d(String name, double x, double y) {
        glProgramUniform2d(handle, getUniformLocation(name), x, y);
        return this;
    }

    public ShaderProgram uniform3d(String name, double x, double y, double z) {
        glProgramUniform3d(handle, getUniformLocation(name), x, y, z);
        return this;
    }

    public ShaderProgram uniform4d(String name, double x, double y, double z, double w) {
        glProgramUniform4d(handle, getUniformLocation(name), x, y, z, w);
        return this;
    }

    public ShaderProgram uniform1i(String name, int x) {
        glProgramUniform1i(handle, getUniformLocation(name), x);
        return this;
    }

    public ShaderProgram uniform2i(String name, int x, int y) {
        glProgramUniform2i(handle, getUniformLocation(name), x, y);
        return this;
    }

    public ShaderProgram uniform3i(String name, int x, int y, int z) {
        glProgramUniform3i(handle, getUniformLocation(name), x, y, z);
        return this;
    }

    public ShaderProgram uniform4i(String name, int x, int y, int z, int w) {
        glProgramUniform4i(handle, getUniformLocation(name), x, y, z, w);
        return this;
    }

    public ShaderProgram uniformMat4(String name, Matrix4f mat) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        mat.get(fb);
        glProgramUniformMatrix4fv(handle, getUniformLocation(name), false, fb);
        return this;
    }
}
