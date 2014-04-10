package com.jeremy.fractal;

/**
 * implementation of a 2 dimensional vector
 */
public class Vec2 {
    public float x, y;

    /**
     * Default constructor.
     *
     * Sets components to {0, 0}
     */
    public Vec2() {
        x = 0;
        y = 0;
    }

    /**
     * Vec2 constructor.
     *
     * @param x  x component (1st element) of vector
     * @param y  y component (2nd element) of vector
     */
    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor
     * @param v  Vec2 to copy
     */
    public Vec2(Vec2 v) {
        x = v.x;
        y = v.y;
    }

    /**
     * Sum with another Vec2.
     *
     * @param v  Vec2 to add
     * @return  Sum of the Vec2's
     */
    public Vec2 plus(Vec2 v) {
        return new Vec2(x+v.x, y+v.y);
    }

    /**
     * This vec2 minus another vec2.
     *
     * @param v  Vec2 to subtract
     * @return  this vec2 minus another vec2
     */
    public Vec2 minus(Vec2 v) {
        return new Vec2(x-v.x, y-v.y);
    }

    /**
     * Product with a scalar.
     *
     * @param c  scalar multiplier
     * @return  product of this Vec2 and a scalar
     */
    public Vec2 times(float c) {
        return new Vec2(x*c, y*c);
    }

    /**
     * Dot product with another Vec2.
     *
     * @param v  Vec2 to take dot product with
     * @return  dot product of the Vec2's
     */
    public float dot(Vec2 v) {
        return x*v.x + y*v.y;
    }

    /**
     * Magnitude (length) of the Vec2.
     *
     * @return Magnitude (length) of the Vec2
     */
    public float len() {
        return (float)Math.sqrt(x*x + y*y);
    }

    /**
     * Unit vector (vector of length 1) in the same direction
     *
     * @return Unit vector in the same direction
     */
    public Vec2 unit() {
        return this.times(1.0f/len());
    }
}
