package org.delusion.game.inventory;

import org.delusion.engine.render.PackedTextureManager;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.ui.Group;
import org.delusion.game.Main;
import org.joml.Vector2f;

import java.util.HashMap;

public class Hotbar extends Group {
    private SelectableSlot[] slots = new SelectableSlot[10];
    private HashMap<SelectableSlot, Integer> slotRev = new HashMap<>();

    private int currentSelection = 0;
    private SelectableSlot selectedSlot = null;

    public Hotbar(/*ShaderProgram sh, ShaderProgram textSh, PackedTextureManager tm*/) {
        ShaderProgram sh = Main.getCurrent().getAssetLibrary().getShader("ui.textured");
        ShaderProgram textSh = Main.getCurrent().getAssetLibrary().getShader("ui.text");
        PackedTextureManager tm = Main.getCurrent().getTexManager();
        for (int i = -5 ; i < 5; i++) {
            slots[i + 5] = new SelectableSlot(new Vector2f(i * 72 + 36,492), new Vector2f(64, 64), sh, textSh, tm, this::onSelect);
            slotRev.put(slots[i + 5], i + 5);
        }

        slots[0].select();
        addAll(slots);
    }

    private void onSelect(SelectableSlot slot) {
        if (selectedSlot != null && selectedSlot != slot) {
            selectedSlot.deselect();
        }
        selectedSlot = slot;
        currentSelection = slotRev.get(slot);
    }

    public Slot getSlot(int i) {
        return slots[i];
    }

    public void setSelectedSlot(int i) {
        if (currentSelection == i) return;
        slots[i].select();
    }

    public SelectableSlot getSelectedSlot() {
        return selectedSlot;
    }

    public boolean isModifiable() {
        return Main.getCurrent().getPlayer().isInventoryOpen();
    }

    public void setSelectedSlot(SelectableSlot slot) {
        slot.select();
    }

    public void select(int i) {
        setSelectedSlot(i);
    }
}
