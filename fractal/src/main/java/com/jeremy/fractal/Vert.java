package com.jeremy.fractal;

import android.graphics.Color;

/**
 * A colored point. Used to draw the fractal.
 */
public class Vert{
    public Vec2 pos;
    public int col;

    /**
     * Default constructor.
     *
     * Sets pos to pos.
     * Sets color to white.
     */
    public Vert() {
        pos = new Vec2();
        col = Color.WHITE;
    }

    /**
     * Constructor with specified position.
     *
     * Sets color to white.
     *
     * @param v Position Vec2
     */
    public Vert(Vec2 v) {
        pos = v;
        col = Color.WHITE;
    }

    /**
     * Constructor with specified position and color.
     *
     * @param v  Position Vec2
     * @param c  Color of the Vert
     */
    public Vert(Vec2 v, int c) {
        pos = v;
        this.col = c;
    }
}
