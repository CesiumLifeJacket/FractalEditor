package com.jeremy.fractal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * View which displays fractal drawn in a separate thread.
 * Uses standard android graphics toolkit.
 * We'll eventually want to replace this with an OpenGL drawing window.
 */
public class FractalView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder sh;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Thread drawThread = new Thread(new FractalDrawer());

    // TODO: make sure this is thread-safe to write from main and read in draw
    private Fractal fractal = new Fractal();

    /**
     * Fractal constructor.
     *
     * @param context  context
     * @param attrs  attrs
     */
    public FractalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sh = getHolder();
        sh.addCallback(this);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    /**
     * Returns the fractal used to draw in this FractalView
     *
     * @return  Fractal
     */
    public Fractal getFractal() {return fractal;}

    /**
     * Thread-safely sets fractal and redraws it.
     *
     * @param f  new Fractal.
     */
    public void setFractal(Fractal f) {
        // interrupt the drawThread's current process and wait for it to finish
        drawThread.interrupt();
        boolean retry = true;
        while(retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (Exception e) {
                Log.v("Error ending thread", e.getMessage());
            }
        }
        // update the fractal
        fractal = f;
        // draw the changed fractal
        drawThread.start();
    }

    /**
     * initializes relevant objects to draw
     *
     * @param surfaceHolder surfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        sh = surfaceHolder;
        // interrupt the drawThread's current process and wait for it to finish
        drawThread.interrupt();
        boolean retry = true;
        while(retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (Exception e) {
                Log.v("Error ending thread", e.getMessage());
            }
        }
        // draw the fractal
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Thread-safely deletes the fractal and ends the drawThread.
     *
     * @param surfaceHolder surfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while(retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (Exception e) {
                Log.v("Error ending thread", e.getMessage());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    /**
     * Draws the fractal to the specified Canvas.
     *
     * @param canvas  Canvas to draw to
     */
    private void doDraw(Canvas canvas) {
        // black background
        canvas.drawColor(Color.BLACK);

//        // get the verts compising the fractal
//        Vert[] verts = fractal_copy.point_fractal(7);
//
//        // draw them to the canvas
//        for (int i=0; i < verts.length; i++) {
//            paint.setColor(verts[i].col);
//            canvas.drawPoint(verts[i].pos.x, verts[i].pos.y, paint);
//        }
        // draw the fractal to the canvas
        // total number of points to be drawn (n_transforms^level)
        int level = 7;
        int n_points = 1;
        for(int i=0; i < level; i++) {
            n_points *= fractal.tforms.size();
        }
        // for each point to be drawn
        for (int i = 0; i < n_points; i++) {
            int r = i;
            Vert t_vert = new Vert(fractal.tforms.get(0).origin);

            // transform the vertex level times
            for (int j = 0; j < level; j++) {
                Transformation tform = fractal.tforms.get((r%(fractal.tforms.size())) );
                t_vert = tform.transform(t_vert);
                r /= fractal.tforms.size();
            }

            // draw the transformed vertex to the canvas
            paint.setColor(t_vert.col);
            canvas.drawPoint(t_vert.pos.x, t_vert.pos.y, paint);
        }

        paint.setColor(Color.WHITE);
    }

    /**
     * Used to draw the fractal in a separate thread.
     */
    private class FractalDrawer implements Runnable {
        @Override
        public void run() {
            Canvas canvas = sh.lockCanvas();
            if (canvas != null) {
                // black background
                canvas.drawColor(Color.BLACK);

                // draw the fractal to the canvas
                // total number of points to be drawn (n_transforms^level)
                int level = 7;
                int n_points = 1;
                for(int i=0; i < level; i++) {
                    n_points *= fractal.tforms.size();
                }
                // for each point to be drawn
                for (int i = 0; i < n_points && !Thread.interrupted(); i++) {
                    int r = i;
                    Vert t_vert = new Vert(fractal.tforms.get(0).origin);

                    // transform the vertex level times
                    for (int j = 0; j < level; j++) {
                        Transformation tform = fractal.tforms.get((r%(fractal.tforms.size())) );
                        t_vert = tform.transform(t_vert);
                        r /= fractal.tforms.size();
                    }

                    // draw the transformed vertex to the canvas
                    paint.setColor(t_vert.col);
                    canvas.drawPoint(t_vert.pos.x, t_vert.pos.y, paint);
                }

                paint.setColor(Color.WHITE);

                sh.unlockCanvasAndPost(canvas);
            }
        }
    }
}
