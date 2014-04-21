package com.jeremy.fractal;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * Each ViewNode presents a user interface to edit one of the fractal's Transformations.
 * They are drawn as small circles of the transformation's color at the transformation's origin
 * They hold transformation information associated with the transformation matrix
 */
public class NodeView extends View {
    /// actual attributes ///
    private int color;
    private float color_weight;
    private boolean use_matrix;
    // component transform values
    private float scale_X, scale_Y;
    private float shearX, shearY;
    private float rotate;
    // matrix values
    private float x1, y1;
    private float x2, y2;

    // drawing attributes
    private Paint paint;

    public NodeView(Context context) {
        super(context);
        init(null, 0);
    }

    public NodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public NodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.NodeView, defStyle, 0);

        // actual attributes
        color = a.getColor(R.styleable.NodeView_color, Color.WHITE);
        color_weight = a.getFloat(R.styleable.NodeView_colorWeight, 1.0f);
        use_matrix = (a.getInt(R.styleable.NodeView_defType, 1) > 0);

        // component values
        scale_X = a.getFloat(R.styleable.NodeView_scaleX, 1);
        scale_Y = a.getFloat(R.styleable.NodeView_scaleY, 1);
        shearX = a.getFloat(R.styleable.NodeView_shearX, 0);
        shearY = a.getFloat(R.styleable.NodeView_shearY, 0);
        rotate = a.getFloat(R.styleable.NodeView_rotate, 0);

        // matrix values
        x1 = a.getFloat(R.styleable.NodeView_x1, 1);
        y1 = a.getFloat(R.styleable.NodeView_y1, 0);
        x2 = a.getFloat(R.styleable.NodeView_x2, 0);
        y2 = a.getFloat(R.styleable.NodeView_y2, 1);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        paint.setColor(Color.GRAY);
        canvas.drawCircle(
                contentWidth/2.0f,
                contentHeight/2.0f,
                Math.min(contentHeight/2.0f, contentWidth/2.0f),
                paint);
        paint.setColor(color);
        canvas.drawCircle(
                contentWidth/2.0f,
                contentHeight/2.0f,
                Math.min(contentHeight/2.3f, contentWidth/2.3f),
                paint);
    }

    // returns the transformation associated with this node
    public Transformation getTransformation() {
        Transformation tform = new Transformation();
        tform.color = color;
        tform.color_weight = color_weight;
        ViewGroup.MarginLayoutParams vlp = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
        tform.origin = new Vec2(vlp.leftMargin+15, vlp.topMargin+15);
        tform.t_matrix = new Mat2();

        // calculate the transformation matrix
        if (use_matrix) {
            tform.t_matrix = new Mat2(x1, y1, x2, y2);
        }
        else {
            Mat2 scale_mat = new Mat2(scale_X, 0, 0, scale_Y);
            float cos = (float)Math.cos(rotate);
            float sin = (float)Math.sin(rotate);
            Mat2 rotate_mat = new Mat2(cos, -sin, sin, cos);
            Mat2 shear_mat = new Mat2(1, shearX, shearY, 1);
            tform.t_matrix = rotate_mat.times(scale_mat).times(shear_mat);
        }

        return tform;
    }

    // hideous block of value getters and setters
    public int getColor() {return color; }
    public void setColor(int color) {this.color = color; paint.setColor(color); invalidate(); }
    public float getColor_weight() {return color_weight; }
    public void setColor_weight(float color_weight) {this.color_weight = color_weight;invalidate(); }
    public boolean isUse_matrix() {return use_matrix; }
    public void setUse_matrix(boolean use_matrix) {this.use_matrix = use_matrix; invalidate(); requestLayout(); }
    public float getScale_X() {return scale_X; }
    public void setScale_X(float scale_X) {this.scale_X = scale_X; invalidate(); requestLayout(); }
    public float getScale_Y() {return scale_Y; }
    public void setScale_Y(float scale_Y) {this.scale_Y = scale_Y; invalidate(); requestLayout(); }
    public float getShearX() {return shearX; }
    public void setShearX(float shearX) {this.shearX = shearX; invalidate(); requestLayout(); }
    public float getShearY() {return shearY; }
    public void setShearY(float shearY) {this.shearY = shearY; invalidate(); requestLayout(); }
    public float getRotate() {return rotate; }
    public void setRotate(float rotate) {this.rotate = rotate; invalidate(); requestLayout(); }
    public float getX1() { return x1; }
    public void setX1(float x1) {this.x1 = x1; invalidate(); requestLayout(); }
    public float getY1() { return y1; }
    public void setY1(float y1) { this.y1 = y1; invalidate(); requestLayout(); }
    public float getX2() { return x2; }
    public void setX2(float x2) { this.x2 = x2; invalidate(); requestLayout(); }
    public float getY2() { return y2; }
    public void setY2(float y2) {this.y2 = y2; invalidate(); requestLayout(); }
}
