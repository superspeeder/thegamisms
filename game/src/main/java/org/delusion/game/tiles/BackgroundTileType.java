package org.delusion.game.tiles;

import org.delusion.game.utils.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum BackgroundTileType {
    Air(-1, false, Set.of(SimpleProperty.NoRender)),
    Dirt(0, true, Set.of());


    private final int i;
    private boolean opaque;
    private Set<SimpleProperty> simpleProperties;

    private static Map<Integer, BackgroundTileType> types = new HashMap<>();

    BackgroundTileType(int i, boolean opaque, Set<SimpleProperty> simpleProperties) {

        this.i = i;
        this.opaque = opaque;
        this.simpleProperties = simpleProperties;
    }

    public static void init() {
        for (BackgroundTileType type : values()) {
            types.put(type.i, type);
        }
    }

    public static BackgroundTileType get(int tile) {
        return types.getOrDefault(tile, BackgroundTileType.Air);
    }

    public boolean opaque() {
        return opaque;
    }

    public int getID() {
        return i;
    }

    public boolean hasSimpleProperty(SimpleProperty property) {
        return simpleProperties.contains(property);
    }
}
