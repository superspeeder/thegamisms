package org.delusion.engine.sprite;

import org.delusion.engine.render.mesh.Mesh;
import org.joml.Vector2f;

import java.io.IOException;

public class QuadSprite extends Sprite {
    private static Mesh quadMesh;

    public static void initMesh() throws IOException {
        quadMesh = new Mesh("/meshes/quad.json");
    }

    public QuadSprite() {
        super(quadMesh);
    }

    public QuadSprite(Vector2f position) {
        super(quadMesh, position);
    }

    public QuadSprite(Vector2f position, Vector2f scale) {
        super(quadMesh, position, scale);
    }

    public QuadSprite(Vector2f position, Vector2f scale, float rotation) {
        super(quadMesh, position, scale, rotation);
    }

    public QuadSprite(Vector2f position, float rotation) {
        super(quadMesh, position, rotation);
    }
}
