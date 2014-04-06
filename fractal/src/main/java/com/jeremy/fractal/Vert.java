package com.jeremy.fractal;

import android.graphics.Color;

import com.jeremy.fractal.Vec2;

/**
 * Created by JeremyIV on 4/2/14.
 */

// verts are points in the fractal. They have a position and color.
public class Vert{
    public Vec2 pos;
    public int col;

    public Vert() {
        pos = new Vec2();
        col = Color.WHITE;
    }

    public Vert(Vec2 v, int c) {
        pos = v;
        this.col = c;
    }

    public Vert(Vec2 v) {
        pos = v;
        col = Color.WHITE;
    }
}
