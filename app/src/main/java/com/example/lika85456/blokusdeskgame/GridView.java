package com.example.lika85456.blokusdeskgame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class GridView extends LinearLayout implements Resizable {
    public float size = 1.f;
    public float pivotX, pivotY;
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

        if (size < 0.1)
            size = 0.1f;
        if (size > 1)
            size = 1f;

        this.invalidate();
    }

}
