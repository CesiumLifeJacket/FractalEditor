package com.jeremy.fractal;

/**
 * Created by JeremyIV on 4/2/14.
 */
public class Mat2 {
    float x1, y1;
    float x2, y2;

    public Mat2() {
        x1 = 0; y1 = 0;
        x2 = 0; y2 = 0;
    }
    public Mat2(
        float x1, float y1,
        float x2, float y2) {

        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
    }
    public Mat2 times(float c) {
        return new Mat2(
            x1*c, y1*c,
            x2*c, y2*c
        );
    }
    public Vec2 times(Vec2 v) {
        return new Vec2(
            x1*v.x + y1*v.y,
            x2*v.x + y2*v.y
        );
    }
    public Mat2 times(Mat2 m) {
        return new Mat2(
            x1*m.x1 + y1*m.x2, x1*m.y1 + y1*m.y2,
            x2*m.x1 + y2*m.x2, x2*m.y1 + y2*m.y2
        );
    }
}
