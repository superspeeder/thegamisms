package org.delusion.game.inventory;

import org.delusion.engine.render.PackedTextureManager;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.ui.Group;
import org.delusion.game.Main;
import org.joml.Vector2f;


public class InventoryView extends Group {

    private final int height;
    private final int width;
    private Slot[] slots;

    public InventoryView(Vector2f pos, Vector2f slotScale, float padding, int rows, int cols) {
        height = rows;
        width = cols;
        slots = new Slot[rows * cols];

        ShaderProgram sh = Main.getCurrent().getAssetLibrary().getShader("ui.textured");
        ShaderProgram textSh = Main.getCurrent().getAssetLibrary().getShader("ui.text");
        PackedTextureManager tm = Main.getCurrent().getTexManager();

        for (int x = 0 ; x < cols ; x++) {
            for (int y = 0 ; y < rows ; y++) {
                slots[y * cols + x] = new Slot(new Vector2f(pos.x + (slotScale.x + padding) * x,pos.y + (slotScale.y + padding) * y ), slotScale, sh, textSh, tm);
            }
        }

        addAll(slots);
    }

    public Slot getSlot(int i) {
        return slots[i];
    }

    public int calcSlotIdx(int x, int y) {
        return y * width + x;
    }

    public Slot getSlot(int x, int y) {
        return getSlot(calcSlotIdx(x, y));
    }
}
