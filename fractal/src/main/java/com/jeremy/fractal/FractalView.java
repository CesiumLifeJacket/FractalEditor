package com.jeremy.fractal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.logging.Handler;


/**
 * Created by JeremyIV on 4/2/14.
 */
// TODO: complete this class
public class FractalView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder sh;
    private Context ctx = null;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private FractalThread fthread;

    public Fractal fractal;

    public FractalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sh = getHolder();
        ctx = context;
        sh.addCallback(this);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // make fractal a sierpinski gasket
        fractal = new Fractal();
        Mat2 scale_half = new Mat2(0.5f, 0, 0, 0.7f);
        Mat2 right_trans  = new Mat2(0.4f, -0.2f, 0.2f, 0.4f);
        Mat2 left_trans   = new Mat2(0.4f, 0.2f, -0.2f, 0.4f);
        Mat2 bottom_trans = new Mat2(0.25f, 0, 0, -0.3f);
        fractal.tforms.add(new Transformation(new Vec2(300,  165), scale_half, Color.RED, 0.3f));
        fractal.tforms.add(new Transformation(new Vec2(100, 700), left_trans, Color.YELLOW, 0.3f));
        fractal.tforms.add(new Transformation(new Vec2(500, 700), right_trans, Color.CYAN, 0.3f));
        fractal.tforms.add(new Transformation(new Vec2(300, 665), bottom_trans, Color.GREEN, 0.3f));

        // paint the fractal to the screen
        /*
        Canvas canvas = sh.lockCanvas();
        onDraw(canvas);
        sh.unlockCanvasAndPost(canvas);
        */

        // start up the draw thread
        fthread = new FractalThread();
        fthread.setRunning(true);
        fthread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        fthread.setRunning(false);
        boolean retry = true;
        while(retry) {
            try {
                fthread.join();
                retry = false;
            } catch (Exception e) {
                Log.v("Error ending thread", e.getMessage());
            }
        }
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

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO: ditch this function when the threading is working
        /*
        // draw the fractal to the canvas
        canvas.drawColor(Color.BLACK);


        Vert[] verts = fractal.point_fractal(7);
        for (int i=0; i < verts.length; i++) {
            paint.setColor(verts[i].col);
            canvas.drawPoint(verts[i].pos.x, verts[i].pos.y, paint);
        }

        //fractal.draw_point_fractal(4, canvas); // should make more efficient
        paint.setColor(Color.WHITE);
        */
    }

    private class FractalThread extends Thread {
        boolean running;
        Canvas canvas;
        public FractalThread() {
            running = false;
        }
        void setRunning(boolean r) {
            running = r;
        }

        @Override
        public void run() {
            super.run();
            while(running) {
                canvas = sh.lockCanvas();
                if (canvas != null) {
                    doDraw(canvas);
                    sh.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
