package org.delusion.game.inventory;

import org.delusion.engine.render.PackedTextureManager;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.texture.Texture2D;
import org.delusion.engine.render.texture.Tileset;
import org.joml.Vector2f;

public class Items {

    public final Item GEM1, WOOD_STICKS, WOOD_PLANKS, WOOD_LOGS;

    public Items(Renderer renderer) {
        GEM1 = new Item("gem1", 100);
        WOOD_STICKS = new Item("wood_sticks", 200);
        WOOD_PLANKS = new Item("wood_planks", 200);
        WOOD_LOGS = new Item("wood_logs", 50);
    }

    public static void registerTextures(PackedTextureManager textureManager, Texture2D.TexParams texParams) {
        textureManager.addTileset("item", new Tileset("/textures/item.png", texParams, new Vector2f(32, 32)));
        textureManager.addTexture("item", "gem1", 0);
        textureManager.addTexture("item", "wood_sticks", 1);
        textureManager.addTexture("item", "wood_logs", 2);
        textureManager.addTexture("item", "wood_planks", 3);
    }
}
