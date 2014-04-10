package com.jeremy.fractal;

/**
 * Implementation of a 2x2 matrix
 */
public class Mat2 {
    public float x1, y1;
    public float x2, y2;

    /**
     * Default Mat2 constructor.
     */
    public Mat2() {
        x1 = 1; y1 = 0;
        x2 = 0; y2 = 1;
    }

    /**
     * Mat2 constructor with specified elements.
     *
     * @param x1  element in 1st row 1st col
     * @param y1  element in 1st row 2nd col
     * @param x2  element in 2nd row 2nd col
     * @param y2  element in 2nd row 2nd col
     */
    public Mat2(
        float x1, float y1,
        float x2, float y2) {

        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
    }

    /**
     * Mat2 copy constructor.
     *
     * @param m  Mat2 to copy
     */
    public Mat2(Mat2 m) {
        x1 = m.x1; y1 = m.y1;
        x2 = m.x2; y2 = m.y2;
    }

    /**
     * Multiplies matrix by a scalar
     *
     * @param c  scalar to multiply by
     * @return  resulting matrix
     */
    public Mat2 times(float c) {
        return new Mat2(
            x1*c, y1*c,
            x2*c, y2*c
        );
    }

    /**
     * Multiplies a vector by this matrix
     *
     * @param v  Vec2 to multiply
     * @return  the resulting Vec2
     */
    public Vec2 times(Vec2 v) {
        return new Vec2(
            x1*v.x + y1*v.y,
            x2*v.x + y2*v.y
        );
    }

    /**
     * Multiply another matrix by this matrix.
     *
     * Multiply another matrix by this matrix. Matrix passed as argument is on the right side
     * of the multiplication; i.e. A*B is expressed as A.times(B)
     *
     * @param m  Mat2 to multiply by
     * @return  the produced Mat2
     */
    public Mat2 times(Mat2 m) {
        return new Mat2(
            x1*m.x1 + y1*m.x2, x1*m.y1 + y1*m.y2,
            x2*m.x1 + y2*m.x2, x2*m.y1 + y2*m.y2
        );
    }
}
