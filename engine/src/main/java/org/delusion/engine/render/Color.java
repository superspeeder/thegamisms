package org.delusion.engine.render;

import org.delusion.engine.utils.Utils;

public class Color {

    public static final Color WHITE = new Color(1,1,1);
    public static final Color BLACK = new Color(0,0,0);
    public static final Color RED = new Color(1,0,0);
    public static final Color GREEN = new Color(0,1,0);
    public static final Color BLUE = new Color(0,0,1);
    public static final Color YELLOW = new Color(1,1,0);
    public static final Color MAGENTA = new Color(1,0,1);
    public static final Color CYAN = new Color(0,1,1);

    private final float r;
    private final float g;
    private final float b;
    private final float a;

    public Color(float r, float g, float b) {
        this(r,g,b,1.0f);
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int rgba8888) {
        r = (float)((rgba8888 >> 24) & 0xFF) / 255.0f;
        g = (float)((rgba8888 >> 16) & 0xFF) / 255.0f;
        b = (float)((rgba8888 >> 8) & 0xFF) / 255.0f;
        a = (float)(rgba8888 & 0xFF) / 255.0f;
    }

    public float r() {
        return r;
    }


    public float g() {
        return g;
    }

    public float b() {
        return b;
    }

    public float a() {
        return a;
    }

    public static Color fromHSV(float h, float s, float v) {
        if (s == 0.f) {
            return new Color(v,v,v);
        }
        int i = (int) (h * 6.f);
        float f = (h * 6.f) - i;
        float p = v * (1.f - s);
        float q = v * (1.f - s*f);
        float t = v * (1.f - s*(1.f-f));
        i = i % 6;
        return switch (i) {
            case 0 -> new Color(v, t, p);
            case 1 -> new Color(q, v, p);
            case 2 -> new Color(p, v, t);
            case 3 -> new Color(p, q, v);
            case 4 -> new Color(t, p, v);
            case 5 -> new Color(v, p, q);
            default -> new Color(0, 0, 0);
        };
    }

    public static Color lerp(Color a, Color b, float t) {
        return new Color(
                (a.r + (b.r - a.r) * t),
                (a.g + (b.g - a.g) * t),
                (a.b + (b.b - a.b) * t),
                (a.a + (b.a - a.a) * t)
        );
    }

    public Color add(Color other) {
        return new Color(
                Utils.clamp(r + other.r, 0.0f, 1.0f),
                Utils.clamp(g + other.g, 0.0f, 1.0f),
                Utils.clamp(b + other.b, 0.0f, 1.0f),
                Utils.clamp(a + other.a, 0.0f, 1.0f)
        );
    }

    public Color sub(Color other) {
        return new Color(
                Utils.clamp(r - other.r, 0.0f, 1.0f),
                Utils.clamp(g - other.g, 0.0f, 1.0f),
                Utils.clamp(b - other.b, 0.0f, 1.0f),
                Utils.clamp(a - other.a, 0.0f, 1.0f)
        );
    }
}
