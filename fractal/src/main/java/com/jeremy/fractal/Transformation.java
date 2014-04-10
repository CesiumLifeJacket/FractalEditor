package com.jeremy.fractal;

import android.graphics.Color;

public class Transformation {
    /** Origin of the transformation.
     * Matrix will transform verts with this position as the origin
     */
    public Vec2 origin;
    /** Transformation matrix.
     * {x, y} position vector of vert is multiplied by this matrix in transformation
     */
    public Mat2 t_matrix;
    /** Transformation color.
     * Transformed vert color will be adjusted towards this color.
     */
    public int color;
    /** Weight of the color adjustment.
     * 0 is no affect on vert color,
     * 1 is complete change to transformation color.
     */
    public float color_weight;

    /**
     * Default Transformation constructor.
     */
    public Transformation() {
        origin = new Vec2();
        t_matrix = new Mat2();
        color = Color.WHITE;
        color_weight = 0;
    }

    /**
     * Transformation constructor with specified origin.
     * <p>
     * Transformation constructor with specified origin.
     * Default transformation matrix is the identity matrix,
     * default color is white,
     * default color weight is zero.
     *
     * @param origin  transformation origin
     */
    public Transformation(Vec2 origin) {
        this.origin = origin;
        t_matrix = new Mat2();
        color = Color.WHITE;
        color_weight = 0;
    }

    /**
     * Transformation constructor with specified origin and transformation matrix.
     *
     * Transformation constructor with specified origin and transformation matrix.
     * Default color is white,
     * default color weight is zero.
     *
     * @param origin    transformation origin
     * @param t_matrix  transformation matrix
     */
    public Transformation(Vec2 origin, Mat2 t_matrix) {
        this.origin = origin;
        this.t_matrix = t_matrix;
        color = Color.WHITE;
        color_weight = 0;
    }

    /**
     * Transformation constructor with specified origin, transformation matrix, color, and color weight.
     * <p>
     * Transformation constructor with specified origin, transformation matrix, color, and color weight.
     *
     * @param origin        transformation origin
     * @param t_matrix      transformation matrix
     * @param color         transformation color
     * @param color_weight  transformation color weight
     */
    public Transformation(Vec2 origin, Mat2 t_matrix, int color, float color_weight) {
        this.origin = origin;
        this.t_matrix = t_matrix;
        this.color = color;
        this.color_weight = color_weight;
    }

    /**
     * Copy constructor for Transformation.
     *
     * @param t Transformation to copy
     */
    public Transformation(Transformation t) {
        this.origin = new Vec2(t.origin);
        this.t_matrix = new Mat2(t.t_matrix);
        this.color = t.color;
        this.color_weight = t.color_weight;
    }

    /**
     * Applies Transformation to a Vert.
     *
     * @param v  Vert to transform
     * @return  the transformed Vert
     */
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
