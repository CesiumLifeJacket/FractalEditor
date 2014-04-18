package com.jeremy.fractal;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fractal defined by a set of transformations.
 * Contains methods to draw the fractal or retrieve a set of points defining it.
 */
public class Fractal {
    // transformations that define the fractal
    ArrayList<Transformation> tforms;

    public Fractal() {
        tforms = new ArrayList<Transformation>();
    }
    public Fractal(Fractal f) {
        tforms = new ArrayList<Transformation>();
        for(int i=0; i < f.tforms.size(); i++)
            tforms.add(new Transformation(f.tforms.get(i)));
    }

    /**
     * Determines a triangulation of the convex hull encompassing the transformation origins.
     * @return  an array of Verts defining a clockwise wind-order triangulation of the convex hull.
     */
    private Vert[] convexHull() {
        Vec2[] vecs = new Vec2[tforms.size()];
        for (int i = 0; i < tforms.size(); i++) {
            vecs[i] = tforms.get(i).origin;
        }
        // triangle or fewer has all points on hull
        if (vecs.length <= 3) {
            Vert[] verts = new Vert[vecs.length];
            for (int i = 0; i < vecs.length; i++)
                verts[i] = new Vert(vecs[i]); // TODO: account for color
            return verts;
        }
        List<Vec2> points = new ArrayList<Vec2>(Arrays.asList(vecs));

        List<Vec2> hull = new ArrayList<Vec2>(); // list of points on the hull, in order

        // get highest point
        int pointOnHull_i = 0;
        for (int i = 0; i < points.size(); i++)
            if (points.get(i).y > points.get(pointOnHull_i).y)
                pointOnHull_i = i;

        Vec2 pointOnHull = points.get(pointOnHull_i);
        points.remove(pointOnHull_i);

        // find points on the hull
        Vec2 firstPtOnHull = pointOnHull;
        Vec2 lastUnitVec = new Vec2(0, 1);

        // get all the points on the hull
        while (true) {
            // find the next point on the hull
            int newHullPt_i = 0; // index of the next hull point
            float max_dot = -2; // related to angle to next dot
            for (int i = 0; i < points.size(); i++) {
                float new_dot = lastUnitVec.dot( (points.get(i).minus(pointOnHull)).unit());
                if( new_dot > max_dot) {
                    newHullPt_i = i;
                    max_dot = new_dot;
                }
            }
            lastUnitVec = (points.get(newHullPt_i).minus(pointOnHull)).unit();

            // if the newly found point is the first point, we are done
            if(points.get(newHullPt_i) == firstPtOnHull) break;

            // else, add this new point to the list of hull points and continue
            pointOnHull = points.get(newHullPt_i);
            hull.add(pointOnHull);
            points.remove(newHullPt_i);
        }
        Vert[] hull_verts = new Vert[hull.size()];
        for (int i = 0; i < hull.size(); i++) hull_verts[i] = new Vert(hull.get(i));

        Vert[] hull_triangles = new Vert[(hull_verts.length-2)*3];

        for (int i = 0; i < hull_verts.length-2; i++) {
            hull_triangles[i*3] = hull_verts[0];
            hull_triangles[i*3+1] = hull_verts[i];
            hull_triangles[i*3+2] = hull_verts[i+1];
        }

        return hull_triangles;
    }

    /**
     * Generates and returns fractal verts using supplied Verts as the base.
     *
     * @param verts  base vertices to recursively transform into a fractal
     * @param n_levels  number of levels of recursion in the fractal
     * @return  array of Verts in the fractal
     */
    private Vert[] recurse_verts(Vert[] verts, int n_levels) {
        Vert[] rec_verts = verts;

        // for n levels of recursion
        while(n_levels-->0) {

            // allocate memory for the next level of recursion
            Vert[] n_rec_verts = new Vert[rec_verts.length*tforms.size()];

            // for each transformation
            for (int i = 0; i < tforms.size(); i++)

                // add a duplicate of the previous level Verts transformed by this transformation
                for (int j = 0; j < rec_verts.length; j++)
                    n_rec_verts[i*rec_verts.length+j] = tforms.get(i).transform(rec_verts[j]);

            rec_verts = n_rec_verts;
        }
        return rec_verts;
    }

    /**
     * Returns an array of Verts on the fractal.
     *
     * @param n_levels  levels of recursion to draw the fractal for
     * @return  an array of Verts on the fractal
     */
    public Vert[] point_fractal(int n_levels) {
        if (tforms.size() == 0) return new Vert[0];
        Vert[] verts = {new Vert(tforms.get(0).origin)};
        return recurse_verts(verts, n_levels);
    }

    /**
     * Draws the fractal to the specified canvas.
     * An iterative method for drawing the fractal with no large memory requirements.
     * Currently highly inefficient, so unused.
     *
     * @param level  levels of recursion to draw the fractal for
     * @param canvas  canvas to draw the fractal to
     */
    public void draw_point_fractal(int level, Canvas canvas) {
        Paint paint = new Paint();
        // total number of points to be drawn (n_transforms^level)
        int n_points = 1;
        for(int i=0; i < level; i++) {
            n_points *= tforms.size();
        }
        // for each point to be drawn
        for (int i = 0; i < n_points; i++) {
            int r = i;
            Vert t_vert = new Vert(tforms.get(0).origin);

            // transform the vertex level times
            for (int j = 0; j < level; j++) {
                Transformation tform = tforms.get((r%(tforms.size())) );
                t_vert = tform.transform(t_vert);
                r /= tforms.size();
            }

            // draw the transformed vertex to the canvas
            paint.setColor(t_vert.col);
            canvas.drawPoint(t_vert.pos.x, t_vert.pos.y, paint);
        }
    }

    /**
     * Returns an array of Verts defining tris in clockwise wind-order
     *
     * @param n_levels  levels of recursion to draw the fractal for
     * @return  An array of Verts defining tris in clockwise wind-order
     */
    public Vert[] tri_fractal(int n_levels) {
        // TODO: generate triangulization of convex hull around transformation origins
        Vert[] verts = convexHull();
        return recurse_verts(verts, n_levels);
    }
}
