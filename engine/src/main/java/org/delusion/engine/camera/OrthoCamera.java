package org.delusion.engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class OrthoCamera extends Camera {
    public OrthoCamera(float left, float right, float bottom, float top, Vector2f position) {
        super(new Matrix4f().ortho2D(left, right, bottom, top), position);
    }

    public OrthoCamera(float left, float right, float bottom, float top) {
        super(new Matrix4f().ortho2D(left, right, bottom, top));
    }
}
