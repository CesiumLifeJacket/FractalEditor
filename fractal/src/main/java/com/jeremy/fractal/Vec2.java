package com.jeremy.fractal;

/**
 * Created by JeremyIV on 4/2/14.
 */
public class Vec2 {
    public float x, y;

    public Vec2() {
        x = 0;
        y = 0;
    }
    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vec2(Vec2 v) {
        x = v.x;
        y = v.y;
    }

    public Vec2 plus(Vec2 v) {
        return new Vec2(x+v.x, y+v.y);
    }
    public Vec2 minus(Vec2 v) {
        return new Vec2(x-v.x, y-v.y);
    }
    public Vec2 times(float c) {
        return new Vec2(x*c, y*c);
    }
    public float dot(Vec2 v) {
        return x*v.x + y*v.y;
    }
    public float len() {
        return (float)Math.sqrt(x*x + y*y);
    }
    public Vec2 unit() {
        return this.times(1.0f/len());
    }
}
