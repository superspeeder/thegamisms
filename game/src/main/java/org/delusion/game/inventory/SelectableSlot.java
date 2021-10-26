package org.delusion.game.inventory;

import org.delusion.engine.render.PackedTextureManager;
import org.delusion.engine.render.shader.ShaderProgram;
import org.joml.Vector2f;

import java.util.function.Consumer;

public class SelectableSlot extends Slot {
    private final Consumer<SelectableSlot> onSelect;

    public SelectableSlot(Vector2f pos, Vector2f size, ShaderProgram sh, ShaderProgram textSh, PackedTextureManager packedTextureManager, Consumer<SelectableSlot> onSelect) {
        this(pos, size, sh, textSh, packedTextureManager, Type.Normal, onSelect, false);
    }

    public SelectableSlot(Vector2f pos, Vector2f size, ShaderProgram sh,  ShaderProgram textSh, PackedTextureManager packedTextureManager, Type type, Consumer<SelectableSlot> onSelect) {
        this(pos, size, sh, textSh, packedTextureManager, type, onSelect, false);
    }

    public SelectableSlot(Vector2f pos, Vector2f size, ShaderProgram sh, ShaderProgram textSh, PackedTextureManager packedTextureManager, Consumer<SelectableSlot> onSelect, boolean selected) {
        this(pos, size, sh, textSh, packedTextureManager, Type.Normal, onSelect, selected);
    }

    public SelectableSlot(Vector2f pos, Vector2f size, ShaderProgram sh,  ShaderProgram textSh, PackedTextureManager packedTextureManager, Type type, Consumer<SelectableSlot> onSelect, boolean selected) {
        super(pos, size, sh, textSh, packedTextureManager, type);
        this.onSelect = onSelect;
    }


    public void select() {
        type.set(Type.Selected);
        onSelect.accept(this);
    }

    @Override
    public void onClick(Vector2f pos, int button, int mods) {
        super.onClick(pos, button, mods);
        select();
    }

    public void deselect() {
        type.set(origintype);

    }
}
