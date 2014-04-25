package com.jeremy.fractal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JeremyIV on 4/24/2014.
 */
public class NodesView extends View {
    private List<Node> nodes = new ArrayList<Node>();
    private int selectedIndex = -1;
    private float nodeRadius = 20;

    // drawing objects
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // refs to other views
    public FractalView fractalView;

    public NodesView(Context context) {
        super(context);
        init();
    }
    public NodesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public NodesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private int nodeIndexAtPoint(float x, float y) {
        // if touch is farther than this from any point, nothing is tapped
        float min_dist = nodeRadius*1.4f;
        int nodeIndex = -1;
        Vec2 t = new Vec2(x, y);
        for(int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            float dist = n.pos.minus(t).len();
            if (n.pos.minus(t).len() < min_dist) {
                min_dist = dist;
                nodeIndex = i;
            }
        }
        return nodeIndex;
    }

    private void init() {
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            // select a node
            setSelectedIndex( nodeIndexAtPoint(event.getX(), event.getY()) );
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            // if no node selected, do nothing
            if (selectedIndex <= -1 || selectedIndex >= nodes.size()) {return false;}
            // else, move the selected node
            nodes.get(selectedIndex).pos = new Vec2(event.getX(), event.getY());
            // redraw the view
            invalidate();
            requestLayout();
            // update the fractal
            fractalView.setFractal(getFractal());
        }
        return true;
    }

    private void drawNode(int index, Canvas canvas) {
        Node n = nodes.get(index);

        paint.setColor(Color.BLACK);
        canvas.drawCircle(n.pos.x, n.pos.y, nodeRadius *1.1f, paint);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(n.pos.x, n.pos.y, nodeRadius, paint);
        paint.setColor(n.color);
        canvas.drawCircle(n.pos.x, n.pos.y, nodeRadius * 0.9f, paint);

        // darken node as if indented
        if (index == selectedIndex) {
            Shader s = paint.getShader();
            RadialGradient gradient =
                    new RadialGradient(
                            n.pos.x, n.pos.y,
                            nodeRadius,
                            0xa0000000, 0x00000000,
                            Shader.TileMode.CLAMP);
            paint.setDither(true);
            paint.setShader(gradient);
            canvas.drawCircle( n.pos.x, n.pos.y, nodeRadius, paint);
            paint.setDither(false);
            paint.setShader(s);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0; i < nodes.size(); i++) {
            drawNode(i, canvas);
        }
    }

    public Fractal getFractal() {
        Fractal f = new Fractal();
        for (Node n : nodes)
            f.tforms.add(n.getTransformation());
        return f;
    }

    public float getNodeRadius() {return nodeRadius;}
    public void setNodeRadius(float radius) {
        nodeRadius = radius; invalidate(); requestLayout();}
    public int getSelectedIndex() {return selectedIndex;}
    public void setSelectedIndex(int selectedIndex) {
        if (selectedIndex < -1 || selectedIndex >= nodes.size()) {/* throw error */}

        this.selectedIndex = selectedIndex;
        invalidate();
        requestLayout();
    }
    public Node getSelectedNode() {
        return nodes.get(selectedIndex);
    }
    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
        invalidate();
        requestLayout();
    }
}
