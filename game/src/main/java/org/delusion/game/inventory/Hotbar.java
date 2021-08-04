package org.delusion.game.inventory;

import org.delusion.engine.render.PackedTextureManager;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.ui.Group;
import org.joml.Vector2f;

public class Hotbar extends Group {
    private Slot[] slots = new Slot[10];

    private int currentSelection = 0;

    public Hotbar(ShaderProgram sh, ShaderProgram textSh, PackedTextureManager tm) {
        for (int i = -5 ; i < 5; i++) {
            slots[i + 5] = new Slot(new Vector2f(i * 72 + 36,492), new Vector2f(64, 64), sh, textSh, tm);
        }

        slots[0].select();
        addAll(slots);
    }

    public Slot getSlot(int i) {
        return slots[i];
    }

    public void setSelectedSlot(int i) {
        slots[currentSelection].deselect();
        currentSelection = i;
        slots[currentSelection].select();
    }

    public Slot getSelectedSlot() {
        return slots[currentSelection];
    }

    public boolean isModifiable() {
        return false;
    }

    public void setSelectedSlot(Slot slot) {
        for (int i = 0 ; i < 10 ; i++) {
            if (slots[i] == slot) {
                setSelectedSlot(i);
                break;
            }
        }
    }
}
