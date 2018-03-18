package com.example.lika85456.blokusdeskgame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class GridView extends RelativeLayout implements Resizable {
    public float size = 1.f;
    public float pivotX, pivotY;
    public int width = 0, height = 0;
    private Context ctx;
    private ArrayList<SquareView> grid;

    public GridView(Context ctx) {
        super(ctx);
        this.ctx = ctx;
        grid = new ArrayList<>();

    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        grid = new ArrayList<>();
    }

    protected void onMeasure(int w, int h) {
        this.width = w;
        this.height = h;
        for (int i = 0; i < grid.size(); i++) {
            grid.get(i).measure((int) (30 * size), (int) (30 * size));
        }
        this.invalidate();
    }

    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        Log.d("onLayout", i + " " + i1 + " " + i2 + " " + i3);
        for (int x = 0; x < grid.size(); x++) {
            int left = (int) ((((float) (i2 - i)) / 20.f) * grid.get(x).x);
            int right = (int) ((((float) (i3 - i1)) / 20.f) * grid.get(x).y);
            grid.get(x).layout(left + (int) (pivotX * size), right + (int) (pivotY * size), (int) (left + (30 * size)) + (int) (pivotX * size), (int) (right + (30 * size)) + (int) (pivotY * size));
        }
    }

    public void add(SquareView toAdd) {
        grid.add(toAdd);
        this.addView(toAdd);
    }

    protected void dispatchDraw(Canvas canvas) {
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.scale(size, size, pivotX, pivotY);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public void scale(float scaleFactor, float pivotX, float pivotY) {
        this.size = scaleFactor * size;
        this.pivotX = pivotX;
        this.pivotY = pivotY;

        if (size < 10)
            size = 10.f;
        if (size > 1)
            size = 1f;
        this.layout(0, 0, width, height);
        this.invalidate();
    }

}
