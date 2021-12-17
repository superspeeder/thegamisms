package org.delusion.game.inventory;

import org.delusion.engine.render.Color;
import org.delusion.engine.render.Renderer;
import org.delusion.engine.render.shader.ShaderProgram;
import org.delusion.engine.render.texture.Texture2D;
import org.delusion.engine.render.texture.Tileset;
import org.delusion.engine.render.ui.Node;
import org.delusion.engine.render.ui.Rect;
import org.delusion.engine.render.PackedTextureManager;
import org.delusion.engine.render.ui.TexturedRect;
import org.delusion.game.Main;
import org.delusion.game.utils.DirtyableVar;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.List;

public class Slot extends TexturedRect {

    public enum Type {
        Normal, Selected, Trash
    };

    private PackedTextureManager texman;
    protected DirtyableVar<Type> type;
    protected final Type origintype;
    private Stack contents;
    private Stack.Renderable renderableStack;

    public static void registerTextures(PackedTextureManager textureManager, Texture2D.TexParams texParams) {
        textureManager.addTileset("slot", new Tileset("/textures/slot.png", texParams, new Vector2f(64, 64)));
        textureManager.addTexture("slot", "normal", 0);
        textureManager.addTexture("slot", "selected", 1);
        textureManager.addTexture("slot", "trash", 2);
    }

    public Slot(Vector2f pos, Vector2f size, ShaderProgram sh,  ShaderProgram textSh, PackedTextureManager packedTextureManager) {
        this(pos,size,sh, textSh, packedTextureManager,Type.Normal);
    }

    public Slot(Vector2f pos, Vector2f size, ShaderProgram sh,  ShaderProgram textSh, PackedTextureManager packedTextureManager, Type type) {
        super(pos, size, true, packedTextureManager.getTileset("slot").getTexture(), packedTextureManager.getTextureUVs("slot", type.name().toLowerCase()), new Color(1, 1, 1, 0.75f), sh);
        texman = packedTextureManager;
        this.type = DirtyableVar.create(type, t -> setUVs(texman.getTextureUVs("slot", t.name().toLowerCase())));
        origintype = type;
        contents = null;
        renderableStack = new Stack.Renderable(pos, new Vector2f(size).mul(0.75f), sh, textSh);
        add(renderableStack);
        setTint(new Color(1.0f,1.0f,1.0f,0.7f));
    }


    public Stack getContents() {
        return contents;
    }


    /**
     * Try to merge
     *
     * @param other
     * @return
     */
    public Stack merge(Stack other) {
        if (this.contents == null || contents.isEmpty()) {
            setContents(other);
            return null;
        }

        Item i = getContents().getItem();
        int max = i.getMaxStackSize();

        int total = getContents().getCount() + other.getCount();

        if (total <= max) { // if other fits into the stack
            contents.setCount(total);
            return null;
        }

        contents.setCount(max);
        int end = total - max;
        return i.makeStack(end);
    }

    /**
     * Used to transfer contents of a stack from the cursor to this slot.
     *
     *
     * @param other
     * @return
     */
    public Stack onClicked(Stack other) {
        if (other == null || other.isEmpty()) { // Cursor is empty, take stack
            Stack out = getContents();
            setContents(null);
            return out;
        }

        if (getContents() == null) {
            setContents(other);
            return null;
        }

        if (!getContents().getItem().equals(other.getItem()) || getContents().isEmpty()) { // Stack needs to be replaced
            Stack out = getContents();
            setContents(other);
            return out;
        }

        // since the items are equal, the stacks are likely mergeable

        Item i = getContents().getItem();
        int max = i.getMaxStackSize();
        if (contents.getCount() == max) { // in this scenario, take the stack and replace
            Stack out = getContents();
            setContents(other);
            return out;
        }

        int total = getContents().getCount() + other.getCount();
        if (total <= max) {
            contents.setCount(total);
            renderableStack.update(contents);
            return null;
        }

        contents.setCount(max);
        renderableStack.update(contents);
        int end = total - max;
        return i.makeStack(end);
    }

    public void setContents(Stack stack) {
        contents = stack;
        renderableStack.update(contents);
    }

//    public void select() {
//        type = Type.Selected;
//        setUVs(texman.getTextureUVs("slot", type.name().toLowerCase()));
//    }
//
//    public void deselect() {
//        type = origintype;
//        setUVs(texman.getTextureUVs("slot", type.name().toLowerCase()));
//    }

    public Type getType() {
        return type.get();
    }


    protected Vector4f getCurrentUVs() {
        return uvs;
    }

    @Override
    public void onClick(Vector2f pos, int button, int mods) {
        super.onClick(pos, button, mods);
        if (getParent() instanceof Hotbar) {
            Hotbar h = (Hotbar) getParent();
            if (h.isModifiable()) {
                Main.getCurrent().getPlayer().setCursor(onClicked(Main.getCurrent().getPlayer().getCursor()));
            }
        } else {
            Main.getCurrent().getPlayer().setCursor(onClicked(Main.getCurrent().getPlayer().getCursor()));
        }
    }
}
