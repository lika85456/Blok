package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.lika85456.blokusdeskgame.R;

import java.util.ArrayList;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class GridView extends ZoomView {
    public int width = 0, height = 0;
    private Context ctx;
    private ArrayList<SquareView> grid;

    private RelativeLayout gridView;

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
        this.width = MeasureSpec.getSize(w);
        this.height = MeasureSpec.getSize(h);
        int pointSize = (int) ((float) Math.min(width, height) / 20.f);
        for (int i = 0; i < grid.size(); i++) {
            grid.get(i).measure(pointSize, pointSize);
        }
        this.setMeasuredDimension(width, height);
        this.invalidate();
    }

    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (b) {
            int pointSize = (int) ((float) Math.min(width, height) / 20.f);
            if (pointSize == 0) pointSize = 36;


            for (int x = 0; x < grid.size(); x++) {
                int left = (pointSize * grid.get(x).x);
                int top = (pointSize * grid.get(x).y);
                grid.get(x).layout(left, top, left + pointSize, top + pointSize);
            }
        }
    }

    public void add(SquareView toAdd) {
        if (gridView == null) {
            gridView = findViewById(R.id.insider_grid);
        }
        gridView.addView(toAdd);
        grid.add(toAdd);
    }

}
