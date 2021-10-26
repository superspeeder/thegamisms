package org.delusion.game;

import org.delusion.engine.assets.AssetLibrary;
import org.delusion.engine.math.AABB;
import org.delusion.engine.math.Rect2i;
import org.delusion.engine.render.ui.Group;
import org.delusion.engine.render.ui.Node;
import org.delusion.engine.sprite.QuadSprite;
import org.delusion.game.inventory.Hotbar;
import org.delusion.game.tiles.SimpleProperty;
import org.delusion.game.tiles.TileData;
import org.delusion.game.utils.Direction;
import org.delusion.game.world.tilemap.Chunk;
import org.delusion.engine.utils.Utils;
import org.delusion.engine.window.input.Key;
import org.delusion.game.world.World;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerSprite extends QuadSprite {
    private static final float MOVE_ACCEL_X = 10.6f;
    private static final float JUMP_STRENGTH = 607.0f;
    private static final float MOVE_MOD = 50.0f;
    private static final float MAX_VEL_X = 16.0f;
    private static final float MAX_VEL_Y = 1500.0f;
    private Hotbar hotbar;
    private World world;

    private Vector2f velocity, acceleration;
    private boolean onGround = false;
    private boolean crouching = false;
    private Set<TileData> tilesOnTopOf = new HashSet<>();
    private Group hud = new Group();


    public PlayerSprite(Vector2f position, World world) {
        super(position, new Vector2f(16.0f, 16.0f));
        this.world = world;
        velocity = new Vector2f();
        acceleration = new Vector2f();
        hotbar = new Hotbar();
        hud.add(hotbar);
    }

    public void onKeyPress(Key key) {
        switch (key) {
            case Left, A -> acceleration.x -= MOVE_ACCEL_X;
            case Right, D -> acceleration.x += MOVE_ACCEL_X;
            case Up, W, Space -> {
                if (onGround && velocity.y < JUMP_STRENGTH)
                    velocity.y += JUMP_STRENGTH;
            }
            case Down, S -> crouching = true;
        }
    }


    public void onKeyRelease(Key key) {
        switch (key) {
            case Left, A -> acceleration.x += MOVE_ACCEL_X;
            case Right, D -> acceleration.x -= MOVE_ACCEL_X;
            case Down, S -> crouching = false;
        }
    }

    public void update(float delta) {
        updateScale();
        moveX(delta);
        moveY(delta);
        tilesOnTopOf.clear();
        if (onGround) {
            Rect2i tilebb = getBoundingBoxTile();
            tilesOnTopOf.addAll(world.getTiles(tilebb.getX(), tilebb.getY() - 1, tilebb.getX2(), tilebb.getY() - 1));
        }
    }

    private void updateScale() {
        if (crouching) {
            scale.set(16, 16);
        } else {
            scale.set(16, 32);
        }
    }

    private static final Vector2f cscale = new Vector2f(Chunk.PIXELS_PER_TILE);
    private void moveX(float delta) {
        velocity.x = Utils.clamp(velocity.x + acceleration.x * delta + world.getGravity().x * delta, -MAX_VEL_X, MAX_VEL_X);
        move(velocity.x * delta * MOVE_MOD, 0);

        if (acceleration.x == 0) {
            if (onGround)
                velocity.x -= (velocity.x) * 2 * delta;
            else
                velocity.x -= ((velocity.x) / 2.f) * delta;

            if (onGround) {
                if (Math.abs(velocity.x) < 5.0f) velocity.x = 0;
            } else {
                if (Math.abs(velocity.x) < 1.0f) velocity.x = 0;
            }
        }

        List<TileData> collisionTiles = getCollisions();

        if (velocity.x > 0) {
            // Moving right

            for (TileData td : collisionTiles) {
                if (getBoundingBox().overlaps(new AABB(new Vector2f(td.pos), cscale)) && td.ty.solidInDirection(Direction.Right)) {
                    position.x = td.pos.x - scale.x;
                    velocity.x = 0;
                }
            }

        } else {
            // Moving left
            for (TileData td : collisionTiles) {
                if (getBoundingBox().overlaps(new AABB(new Vector2f(td.pos), cscale)) && td.ty.solidInDirection(Direction.Left)) {
                    position.x = td.pos.x + Chunk.PIXELS_PER_TILE;
                    velocity.x = 0;
                }
            }
        }
    }

    public AABB getBoundingBox() {
        return new AABB(position, scale);
    }

    private List<TileData> getCollisions() {
        List<TileData> collisions = new ArrayList<>();
        Rect2i boundingBox = getBoundingBoxTile();

        for (int x = boundingBox.getX() - 1; x < boundingBox.getX2() + 1; x += 1) {
            for (int y = boundingBox.getY() - 1; y < boundingBox.getY2() + 1; y += 1) {
                if (world.isSolid(x, y)) {
                    collisions.add(new TileData(new Vector2i(x * Chunk.PIXELS_PER_TILE,y * Chunk.PIXELS_PER_TILE), world.getTile(x, y)));
                }
            }
        }
        return collisions;
    }

    public Rect2i getBoundingBoxTile() {
        return Rect2i.fromCorners(
                Math.floorDiv((int)position.x,Chunk.PIXELS_PER_TILE),
                Math.floorDiv((int)position.y,Chunk.PIXELS_PER_TILE),
                (int) Math.ceil((position.x + scale.x) / Chunk.PIXELS_PER_TILE),
                (int) Math.ceil((position.y + scale.y) / Chunk.PIXELS_PER_TILE));
    }

    private void moveY(float delta) {
        onGround = false;
        velocity.y += acceleration.y * delta + world.getGravity().y * delta;
        move(0, Utils.clamp(velocity.y * delta, -5.0f, 15.0f) );

        List<TileData> collisionTiles = getCollisions();

        if (velocity.y > 0) {
            // Moving up
            for (TileData td : collisionTiles) {
                if (getBoundingBox().overlaps(new AABB(new Vector2f(td.pos), cscale)) && td.ty.solidInDirection(Direction.Up)) {
                    position.y = td.pos.y - scale.y;
                    velocity.y = 0;
                }
            }
        } else {
            // Moving Down
            for (TileData td : collisionTiles) {
                if (getBoundingBox().overlaps(new AABB(new Vector2f(td.pos), cscale)) && td.ty.solidInDirection(Direction.Down)) {
                    if (td.ty.hasSimpleProperty(SimpleProperty.Platform) && crouching) {
                        continue;
                    }
                    else if (td.ty.hasSimpleProperty(SimpleProperty.Platform) && position.y + 4 < td.pos.y + Chunk.PIXELS_PER_TILE) {
                        continue;
                    }
                    position.y = td.pos.y + Chunk.PIXELS_PER_TILE;
                    velocity.y = 0;
                    onGround = true;
                }
            }
        }
    }

    public Group getHUD() {
        return hud;
    }

    public Hotbar getHotbar() {
        return hotbar;
    }
}
