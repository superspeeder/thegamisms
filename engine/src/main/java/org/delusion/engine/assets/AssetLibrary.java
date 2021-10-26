package org.delusion.engine.assets;

import org.delusion.engine.render.shader.ShaderProgram;

import java.util.concurrent.ConcurrentHashMap;

public class AssetLibrary {
    private ConcurrentHashMap<String, ShaderProgram> shaders = new ConcurrentHashMap<>();

    public ShaderProgram getShader(String s) {
        return shaders.get(s);
    }

    public void putShader(String s, ShaderProgram prog) {
        shaders.put(s, prog);
    }
}
