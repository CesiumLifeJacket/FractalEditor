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
 * Created by JeremyIV on 4/2/14.
 */
// TODO: complete this class
public class FractalView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder sh;
    private Context ctx = null;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Thread drawThread;

    // TODO: make sure this is thread-safe to write from main and read in draw
    private Fractal fractal;

    public FractalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sh = getHolder();
        ctx = context;
        sh.addCallback(this);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    public Fractal getFractal() {return fractal;}

    public void setFractal(Fractal f) {
        fractal = f;

        // redraw fractal
        drawThread.start(); // TODO: find out what happens when start() is called before a thread finishes.
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // Make a nifty leaf fractal
        fractal = new Fractal();
        Mat2 scale_half = new Mat2(0.5f, 0, 0, 0.5f);
        fractal.tforms.add(new Transformation(new Vec2(325,  165), scale_half, Color.RED, 0.3f));
        fractal.tforms.add(new Transformation(new Vec2(50, 700), scale_half, Color.GREEN, 0.3f));
        fractal.tforms.add(new Transformation(new Vec2(600, 700), scale_half, Color.BLUE, 0.3f));

        // initialize fractal drawing thread
        drawThread = new Thread(new FractalDrawer());

        // draw fractal in new thread
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

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

    // function to draw the fractal to the screen instead of onDraw
    void doDraw(Canvas canvas) {
        // black background
        canvas.drawColor(Color.BLACK);

        // get the verts compising the fractal
        Vert[] verts = fractal.point_fractal(7);

        // draw them to the canvas
        for (int i=0; i < verts.length; i++) {
            paint.setColor(verts[i].col);
            canvas.drawPoint(verts[i].pos.x, verts[i].pos.y, paint);
        }

        paint.setColor(Color.WHITE);
    }

    private class FractalDrawer implements Runnable {
        @Override
        public void run() {
            Canvas canvas = sh.lockCanvas();
            if (canvas != null) {
                doDraw(canvas);
                sh.unlockCanvasAndPost(canvas);
            }
        }
    }

}
