package org.delusion.engine.sprite;

import org.delusion.engine.render.mesh.Mesh;
import org.joml.Vector2f;

public class SpriteWithPhysics extends Sprite {
    public SpriteWithPhysics(Mesh mesh) {
        super(mesh);
    }

    public SpriteWithPhysics(Mesh mesh, Vector2f position) {
        super(mesh, position);
    }

    public SpriteWithPhysics(Mesh mesh, Vector2f position, Vector2f scale) {
        super(mesh, position, scale);
    }

    public SpriteWithPhysics(Mesh mesh, Vector2f position, Vector2f scale, float rotation) {
        super(mesh, position, scale, rotation);
    }

    public SpriteWithPhysics(Mesh mesh, Vector2f position, float rotation) {
        super(mesh, position, rotation);
    }
}
