package com.jeremy.fractal;

import android.graphics.Color;

import com.jeremy.fractal.Mat2;

/**
 * Created by JeremyIV on 4/2/14.
 */
public class Transformation {
    public Vec2 origin;
    public Mat2 t_matrix;
    public int color;
    public float color_weight;// float between 0 and 1

    public Transformation() {
        origin = new Vec2();
        t_matrix = new Mat2();
        color = Color.WHITE;
        color_weight = 0;
    }
    public Transformation(Vec2 origin) {
        this.origin = origin;
        t_matrix = new Mat2();
        color = Color.WHITE;
        color_weight = 0;
    }
    public Transformation(Vec2 origin, Mat2 t_matrix) {
        this.origin = origin;
        this.t_matrix = t_matrix;
        color = Color.WHITE;
        color_weight = 0;
    }
    public Transformation(Vec2 origin, Mat2 t_matrix, int color, float color_weight) {
        this.origin = origin;
        this.t_matrix = t_matrix;
        this.color = color;
        this.color_weight = color_weight;
    }

    public Vert transform(Vert v) {
        Vert new_v = new Vert();
        // change position
        new_v.pos = t_matrix.times(v.pos.minus(origin)).plus(origin);
        new_v.col = Color.rgb(
                (int)((1-color_weight)*Color.red(v.col)   + color_weight*Color.red(color)   ),
                (int)((1-color_weight)*Color.green(v.col) + color_weight*Color.green(color) ),
                (int)((1-color_weight)*Color.blue(v.col)  + color_weight*Color.blue(color)  )
        );
        // return the new com.jeremy.fractal.Vert
        return new_v;
    }
}
