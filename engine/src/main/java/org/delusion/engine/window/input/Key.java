package org.delusion.engine.window.input;

import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public enum Key {
    Unknown(GLFW.GLFW_KEY_UNKNOWN),
    Space(GLFW.GLFW_KEY_SPACE),
    Apostrophe(GLFW.GLFW_KEY_APOSTROPHE),
    Comma(GLFW.GLFW_KEY_COMMA),
    Minus(GLFW.GLFW_KEY_MINUS),
    Period(GLFW.GLFW_KEY_PERIOD),
    Slash(GLFW.GLFW_KEY_SLASH),
    K0(GLFW.GLFW_KEY_0),
    K1(GLFW.GLFW_KEY_1),
    K2(GLFW.GLFW_KEY_2),
    K3(GLFW.GLFW_KEY_3),
    K4(GLFW.GLFW_KEY_4),
    K5(GLFW.GLFW_KEY_5),
    K6(GLFW.GLFW_KEY_6),
    K7(GLFW.GLFW_KEY_7),
    K8(GLFW.GLFW_KEY_8),
    K9(GLFW.GLFW_KEY_9),
    Semicolon(GLFW.GLFW_KEY_SEMICOLON),
    Equal(GLFW.GLFW_KEY_EQUAL),
    A(GLFW.GLFW_KEY_A),
    B(GLFW.GLFW_KEY_B),
    C(GLFW.GLFW_KEY_C),
    D(GLFW.GLFW_KEY_D),
    E(GLFW.GLFW_KEY_E),
    F(GLFW.GLFW_KEY_F),
    G(GLFW.GLFW_KEY_G),
    H(GLFW.GLFW_KEY_H),
    I(GLFW.GLFW_KEY_I),
    J(GLFW.GLFW_KEY_J),
    K(GLFW.GLFW_KEY_K),
    L(GLFW.GLFW_KEY_L),
    M(GLFW.GLFW_KEY_M),
    N(GLFW.GLFW_KEY_N),
    O(GLFW.GLFW_KEY_O),
    P(GLFW.GLFW_KEY_P),
    Q(GLFW.GLFW_KEY_Q),
    R(GLFW.GLFW_KEY_R),
    S(GLFW.GLFW_KEY_S),
    T(GLFW.GLFW_KEY_T),
    U(GLFW.GLFW_KEY_U),
    V(GLFW.GLFW_KEY_V),
    W(GLFW.GLFW_KEY_W),
    X(GLFW.GLFW_KEY_X),
    Y(GLFW.GLFW_KEY_Y),
    Z(GLFW.GLFW_KEY_Z),
    LeftBracket(GLFW.GLFW_KEY_LEFT_BRACKET),
    Backslash(GLFW.GLFW_KEY_BACKSLASH),
    RightBracket(GLFW.GLFW_KEY_RIGHT_BRACKET),
    GraveAccent(GLFW.GLFW_KEY_GRAVE_ACCENT),
    World1(GLFW.GLFW_KEY_WORLD_1),
    World2(GLFW.GLFW_KEY_WORLD_2),
    Escape(GLFW.GLFW_KEY_ESCAPE),
    Enter(GLFW.GLFW_KEY_ENTER),
    Tab(GLFW.GLFW_KEY_TAB),
    Backspace(GLFW.GLFW_KEY_BACKSPACE),
    Insert(GLFW.GLFW_KEY_INSERT),
    Delete(GLFW.GLFW_KEY_DELETE),
    Right(GLFW.GLFW_KEY_RIGHT),
    Left(GLFW.GLFW_KEY_LEFT),
    Down(GLFW.GLFW_KEY_DOWN),
    Up(GLFW.GLFW_KEY_UP),
    PageUp(GLFW.GLFW_KEY_PAGE_UP),
    PageDown(GLFW.GLFW_KEY_PAGE_DOWN),
    Home(GLFW.GLFW_KEY_HOME),
    End(GLFW.GLFW_KEY_END),
    CapsLock(GLFW.GLFW_KEY_CAPS_LOCK),
    ScrollLock(GLFW.GLFW_KEY_SCROLL_LOCK),
    NumLock(GLFW.GLFW_KEY_NUM_LOCK),
    PrintScreen(GLFW.GLFW_KEY_PRINT_SCREEN),
    Pause(GLFW.GLFW_KEY_PAUSE),
    F1(GLFW.GLFW_KEY_F1),
    F2(GLFW.GLFW_KEY_F2),
    F3(GLFW.GLFW_KEY_F3),
    F4(GLFW.GLFW_KEY_F4),
    F5(GLFW.GLFW_KEY_F5),
    F6(GLFW.GLFW_KEY_F6),
    F7(GLFW.GLFW_KEY_F7),
    F8(GLFW.GLFW_KEY_F8),
    F9(GLFW.GLFW_KEY_F9),
    F10(GLFW.GLFW_KEY_F10),
    F11(GLFW.GLFW_KEY_F11),
    F12(GLFW.GLFW_KEY_F12),
    F13(GLFW.GLFW_KEY_F13),
    F14(GLFW.GLFW_KEY_F14),
    F15(GLFW.GLFW_KEY_F15),
    F16(GLFW.GLFW_KEY_F16),
    F17(GLFW.GLFW_KEY_F17),
    F18(GLFW.GLFW_KEY_F18),
    F19(GLFW.GLFW_KEY_F19),
    F20(GLFW.GLFW_KEY_F20),
    F21(GLFW.GLFW_KEY_F21),
    F22(GLFW.GLFW_KEY_F22),
    F23(GLFW.GLFW_KEY_F23),
    F24(GLFW.GLFW_KEY_F24),
    F25(GLFW.GLFW_KEY_F25),
    KP0(GLFW.GLFW_KEY_KP_0),
    KP1(GLFW.GLFW_KEY_KP_1),
    KP2(GLFW.GLFW_KEY_KP_2),
    KP3(GLFW.GLFW_KEY_KP_3),
    KP4(GLFW.GLFW_KEY_KP_4),
    KP5(GLFW.GLFW_KEY_KP_5),
    KP6(GLFW.GLFW_KEY_KP_6),
    KP7(GLFW.GLFW_KEY_KP_7),
    KP8(GLFW.GLFW_KEY_KP_8),
    KP9(GLFW.GLFW_KEY_KP_9),
    KPDecimal(GLFW.GLFW_KEY_KP_DECIMAL),
    KPDivide(GLFW.GLFW_KEY_KP_DIVIDE),
    KPMultiply(GLFW.GLFW_KEY_KP_MULTIPLY),
    KPSubtract(GLFW.GLFW_KEY_KP_SUBTRACT),
    KPAdd(GLFW.GLFW_KEY_KP_ADD),
    KPEnter(GLFW.GLFW_KEY_KP_ENTER),
    KPEqual(GLFW.GLFW_KEY_KP_EQUAL),
    LeftShift(GLFW.GLFW_KEY_LEFT_SHIFT),
    LeftControl(GLFW.GLFW_KEY_LEFT_CONTROL),
    LeftAlt(GLFW.GLFW_KEY_LEFT_ALT),
    LeftSuper(GLFW.GLFW_KEY_LEFT_SUPER),
    RightShift(GLFW.GLFW_KEY_RIGHT_SHIFT),
    RightControl(GLFW.GLFW_KEY_RIGHT_CONTROL),
    RightAlt(GLFW.GLFW_KEY_RIGHT_ALT),
    RightSuper(GLFW.GLFW_KEY_RIGHT_SUPER),
    Menu(GLFW.GLFW_KEY_MENU);

    private static Map<Integer,Key> keyMap;

    private final int keynum;

    Key(int keynum) {
        this.keynum = keynum;
    }

    public int getID() {
        return keynum;
    }

    public static Key get(int id) {
        return keyMap.getOrDefault(id, Unknown);
    }

    public static void init() {
        keyMap =  new HashMap<>();
        for (Key k : values()) {
            keyMap.put(k.getID(), k);
        }
    }

}

