package org.delusion.engine.render;

import org.delusion.engine.render.texture.Tileset;
import org.joml.Vector4f;

import java.util.HashMap;
import java.util.Map;

public class PackedTextureManager {
    private final Map<String, Map<String, Integer>> names = new HashMap<>();
    private final Map<String, Tileset> tilesets = new HashMap<>();

    public PackedTextureManager() {
    }

    public void addTexture(String tileset, String name, int i) {
        if (names.containsKey(tileset)) {
            names.get(tileset).put(name, i);
        }
    }

    public void addTileset(String name, Tileset ts) {
        tilesets.put(name, ts);
        names.put(name, new HashMap<>());
    }

    public int getTextureID(String tileset, String name) {
        return names.get(tileset).get(name);
    }

    public Vector4f getTextureUVs(String tileset, String name) {
        return tilesets.get(tileset).tileUVs(getTextureID(tileset, name));
    }

    public Tileset getTileset(String name) {
        return tilesets.get(name);
    }
}
