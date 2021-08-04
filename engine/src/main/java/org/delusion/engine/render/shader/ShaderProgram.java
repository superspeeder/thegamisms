package org.delusion.engine.render.shader;

import org.delusion.engine.render.Color;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.lwjgl.opengl.GL46.*;

public class ShaderProgram {
    private int handle;
    private Map<String,Integer> uniformLocations = new HashMap<>();

    public ShaderProgram(Shader... shaders) {
        handle = glCreateProgram();
        for (Shader sh : shaders) {
            glAttachShader(handle, sh.getHandle());
        }

        glLinkProgram(handle);
        if (glGetProgrami(handle, GL_LINK_STATUS) != GL_TRUE) {
            System.out.println(glGetProgramInfoLog(handle));
        }

        System.out.println(handle + ":");
        int uc = glGetProgrami(handle, GL_ACTIVE_UNIFORMS);
        System.out.println("Active Uniforms: ");
        for (int i = 0 ; i < uc ; i++) {
            System.out.println(i + ": " + glGetActiveUniformName(handle, i));
            uniformLocations.put(glGetActiveUniformName(handle, i), i);
        }


    }

    public void use() {
        glUseProgram(handle);
    }

    private int getUniformLocation(String name) {
        return uniformLocations.getOrDefault(name, -1);
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
        int uniformLocation = getUniformLocation(name);
        glProgramUniformMatrix4fv(handle, uniformLocation, false, fb);
        return this;
    }

    public void uniform2f(String name, Vector2f vec) {
        uniform2f(name, vec.x,vec.y);
    }

    public void uniformColor(String name, Color col) {
        if (col != null)
            uniform4f(name, col.r(), col.g(), col.b(), col.a());
    }

    public void uniform4f(String name, Vector4f vec) {
        if (vec != null)
            uniform4f(name, vec.x, vec.y, vec.z, vec.w);
    }
}
