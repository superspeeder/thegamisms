package org.delusion.game;

import org.delusion.engine.math.Rect2i;
import org.delusion.engine.render.mesh.Mesh;
import org.delusion.engine.sprite.QuadSprite;
import org.delusion.engine.sprite.Sprite;
import org.delusion.engine.tilemap.Chunk;
import org.delusion.engine.utils.Utils;
import org.delusion.engine.window.input.Key;
import org.delusion.game.world.World;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class PlayerSprite extends QuadSprite {
    private static final float MOVE_ACCEL_X = 0.6f;
    private static final float JUMP_STRENGTH = 7.0f;
    private static float MAX_VEL_X = 5.0f;
    private static float MAX_VEL_Y = 15.0f;
    private World world;

    private Vector2f velocity, acceleration;
    private boolean onGround = false;


    public PlayerSprite(Vector2f position, World world) {
        super(position, new Vector2f(16.0f, 16.0f));
        this.world = world;
        velocity = new Vector2f();
        acceleration = new Vector2f();
    }

    public void onKeyPress(Key key) {
        switch (key) {
            case Left, A -> acceleration.x -= MOVE_ACCEL_X;
            case Right, D -> acceleration.x += MOVE_ACCEL_X;
            case Up, W -> {
                if (onGround && velocity.y < JUMP_STRENGTH)
                    velocity.y += JUMP_STRENGTH;
            }
            case Space -> System.out.println(velocity);
        }
    }

    public void onKeyRelease(Key key) {
        switch (key) {
            case Left, A -> acceleration.x += MOVE_ACCEL_X;
            case Right, D -> acceleration.x -= MOVE_ACCEL_X;
        }
    }

    public void update() {
        moveX();
        moveY();
    }

    private void moveX() {
        velocity.x = Utils.clamp(velocity.x + acceleration.x + world.getGravity().x, -MAX_VEL_X, MAX_VEL_X);
        move(velocity.x, 0);

        if (acceleration.x == 0) {
            velocity.x -= (velocity.x);
            if (Math.abs(velocity.x) < 1.0f) velocity.x = 0;
        }

        List<Vector2i> collisionTiles = getCollisions();

        if (velocity.x > 0) {
            // Moving right

            for (Vector2i pos : collisionTiles) {
                if (pos.x <= position.x + scale.x) {
                    position.x = pos.x - scale.x;
                    velocity.x = 0;
                }
            }

        } else {
            // Moving left
            for (Vector2i pos : collisionTiles) {
                if (pos.x + Chunk.PIXELS_PER_TILE >= position.x) {
                    position.x = pos.x + Chunk.PIXELS_PER_TILE;
                    velocity.x = 0;
                }
            }
        }
    }

    private List<Vector2i> getCollisions() {
        List<Vector2i> collisions = new ArrayList<>();
        Rect2i boundingBox = getBoundingBox();

        for (int x = boundingBox.getX() ; x < boundingBox.getX2() ; x += Chunk.PIXELS_PER_TILE) {
            for (int y = boundingBox.getY() ; y < boundingBox.getY2() ; y += Chunk.PIXELS_PER_TILE) {
                if (world.isSolid(x / Chunk.PIXELS_PER_TILE,y / Chunk.PIXELS_PER_TILE)) {
                    collisions.add(new Vector2i(x,y));
                }
            }
        }
        return collisions;
    }

    public Rect2i getBoundingBox() {
        return Rect2i.fromCorners(
                Math.floorDiv((int)position.x,Chunk.PIXELS_PER_TILE)*Chunk.PIXELS_PER_TILE,
                Math.floorDiv((int)position.y,Chunk.PIXELS_PER_TILE)*Chunk.PIXELS_PER_TILE,
                (int) Math.ceil((position.x + scale.x) / Chunk.PIXELS_PER_TILE)*Chunk.PIXELS_PER_TILE,
                (int) Math.ceil((position.y + scale.y) / Chunk.PIXELS_PER_TILE)*Chunk.PIXELS_PER_TILE);
    }

    private void moveY() {
        onGround = false;
        velocity.y += acceleration.y + world.getGravity().y;
        move(0, Utils.clamp(velocity.y, -5.0f, 15.0f));

        List<Vector2i> collisionTiles = getCollisions();

        if (velocity.y > 0) {
            // Moving up

            for (Vector2i pos : collisionTiles) {
                if (pos.y <= position.y + scale.y) {
                    position.y = pos.y - scale.y;
                    velocity.y = 0;
                }
            }

        } else {
            // Moving Down
            for (Vector2i pos : collisionTiles) {
                if (pos.y + Chunk.PIXELS_PER_TILE >= position.y) {
                    position.y = pos.y + Chunk.PIXELS_PER_TILE;
                    velocity.y = 0;
                    onGround = true;
                }
            }
        }
    }
}
