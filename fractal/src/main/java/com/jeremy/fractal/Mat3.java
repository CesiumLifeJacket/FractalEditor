package com.jeremy.fractal;

/**
 * Created by JeremyIV on 4/2/14.
 */
public class Mat3 {
    public float x1, y1, z1;
    public float x2, y2, z2;
    public float x3, y3, z3;

    public Mat3() {
        x1 = 0; y1 = 0; z1 = 0;
        x2 = 0; y2 = 0; z2 = 0;
        x3 = 0; y3 = 0; z3 = 0;
    }
    public Mat3(
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3) {
        this.x1 = x1; this.y1 = y1; this.z1 = z1;
        this.x2 = x2; this.y2 = y2; this.z2 = z2;
        this.x3 = x3; this.y3 = y3; this.z3 = z3;
    }
    public Mat3 times(float c) {
        return new Mat3(
            x1*c, y1*c, z1*c,
            x2*c, y2*c, z2*c,
            x3*c, y3*c, z3*c
        );
    }
    public Vec3 times(Vec3 v) {
        return new Vec3(
            x1*v.x + y1*v.y + z1*v.z,
            x2*v.x + y2*v.y + z2*v.z,
            x3*v.x + y3*v.y + z3*v.z
        );
    }
    public Mat3 times(Mat3 m) {
        return new Mat3(
                x1*m.x1 + y1*m.x2 + z1*m.x3,
                x1*m.y1 + y1*m.y2 + y1*m.y3,
                x1*m.z1 + y1*m.z2 + z1*m.z3,

                x2*m.x1 + y2*m.x2 + z2*m.x3,
                x2*m.y1 + y2*m.y2 + y2*m.y3,
                x2*m.z1 + y2*m.z2 + z2*m.z3,

                x3*m.x1 + y3*m.x2 + z3*m.x3,
                x3*m.y1 + y3*m.y2 + y3*m.y3,
                x3*m.z1 + y3*m.z2 + z3*m.z3
        );
    }

    // IMPLEMENT
    /*
    public String toString() {
        return "";
    }
    */
}
