package com.jeremy.fractal;

/**
 * Created by JeremyIV on 4/2/14.
 */
public class Vec3 {
    public float x, y, z;
    public Vec3() {
        x = 0;
        y = 0;
        z = 0;
    }
    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vec3 plus(Vec3 v) {
        return new Vec3(x+v.x, y+v.y, z+v.z);
    }
    public Vec3 minus(Vec3 v) {
        return new Vec3(x-v.x, y-v.y, z-v.z);
    }
    public Vec3 times(float c) {
        return new Vec3(x*c, y*c, z*c);
    }
}
