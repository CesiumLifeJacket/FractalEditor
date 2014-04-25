package com.jeremy.fractal;

import android.graphics.Color;

/**
 * Created by JeremyIV on 4/24/2014.
 */
public class Node {

    // component transform values
    public Vec2 pos;
    public int   color;
    public float color_weight;
    public float scaleX, scaleY;
    public float shearX, shearY;
    public float rotate;

    public Node() {
        color = Color.WHITE;
        color_weight = 0;
        pos = new Vec2(100 ,100);
        scaleX = 1;
        scaleY = 1;
        shearX = 0;
        shearY = 0;
        rotate = 0;
    }
    public Node(
            int color,
            float color_weight,
            float X,
            float Y,
            float scaleX,
            float scaleY,
            float shearX,
            float shearY,
            float rotate) {

        this.color = color;
        this.color_weight = color_weight;
        this.pos = new Vec2(X, Y);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.shearX = shearX;
        this.shearY = shearY;
        this.rotate = rotate;
    }

    public Transformation getTransformation() {

        Transformation tform = new Transformation();
        tform.color = color;
        tform.color_weight = color_weight;
        tform.origin = pos;

        // calculate the transformation matrix
        Mat2 scale_mat = new Mat2(scaleX, 0, 0, scaleY);
        float theta = rotate*(float)Math.PI/180.0f; // convert degrees to radians
        float cos = (float)Math.cos(theta);
        float sin = (float)Math.sin(theta);
        Mat2 rotate_mat = new Mat2(cos, -sin, sin, cos);
        Mat2 shear_mat = new Mat2(1, shearX, shearY, 1);
        tform.t_matrix = rotate_mat.times(scale_mat.times(shear_mat));

        return tform;
    }

}
