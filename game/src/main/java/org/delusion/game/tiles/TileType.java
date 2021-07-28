package org.delusion.game.tiles;

import org.delusion.game.utils.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum TileType {
    Air(-1, false, Direction.none(), Set.of(SimpleProperty.NoRender)),
    Dirt(0, true, Direction.all(), Set.of()),
    Grass(1, true, Direction.all(), Set.of()),
    Platform(2, true, Set.of(Direction.Down), Set.of(SimpleProperty.Platform)),
    Stone(3, true, Direction.all(), Set.of())
    ;


    private final int i;
    private boolean solid;
    private Set<Direction> solidDirections;
    private Set<SimpleProperty> simpleProperties;

    private static Map<Integer,TileType> types = new HashMap<>();

    TileType(int i, boolean solid, Set<Direction> solidDirections, Set<SimpleProperty> simpleProperties) {

        this.i = i;
        this.solid = solid;
        this.solidDirections = solidDirections;
        this.simpleProperties = simpleProperties;
    }

    public static void init() {
        for (TileType type : values()) {
            types.put(type.i, type);
        }
    }

    public static TileType get(int tile) {
        return types.getOrDefault(tile, TileType.Air);
    }

    public boolean solid() {
        return solid;
    }

    public boolean solidInDirection(Direction dir) {
        return solidDirections.contains(dir);
    }

    public int getID() {
        return i;
    }

    public boolean hasSimpleProperty(SimpleProperty property) {
        return simpleProperties.contains(property);
    }
}
