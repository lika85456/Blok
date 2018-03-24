package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.lika85456.blokusdeskgame.Utilities.Initialization.OnOnInitializedListener;
import com.example.lika85456.blokusdeskgame.Model.Piece;
import com.example.lika85456.blokusdeskgame.Model.SquareColor;
import com.example.lika85456.blokusdeskgame.R;

import java.util.ArrayList;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class GridView extends ZoomView {
    public int width = 0, height = 0;
    private Context ctx;
    private ArrayList<SquareView> grid;
    private boolean initialized = false;

    private OnOnInitializedListener onInitializedListener = new OnOnInitializedListener();

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

    private void fill(){
        for (int x = 0; x < 20; x++)
            for (int y = 0; y < 20; y++) {
                add(new SquareView(ctx, SquareColor.BLANK, x, y));

            }
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
        if(initialized==false)
        {
            initialized=true;
            fill();
            onInitializedListener.onInit();
        }
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

    /***
     * Sets OnOnInitializedListener
     * @param onInitializedListener
     */
    public void setOnInitializedListener(OnOnInitializedListener onInitializedListener)
    {
        this.onInitializedListener = onInitializedListener;
    }

    /***
     * Sets the grid from board array
     * @param array
     */
    public void fromBoard(byte[][] array)
    {
        for(int x = 0;x<20;x++)
            for(int y = 0;y<20;y++)
            {
                setColor(x,y,array[x][y]);
            }
    }

    public void addPiece(Piece piece,int x,int y)
    {
        for(int i = 0;i<piece.list.size();i++)
        {
            Point temp = piece.list.get(i);
            get(temp.x+x,temp.y+y).setColor(piece.color);
        }
    }

    /***
     *
     * @param x
     * @param y
     * @param color (byte)
     */
    public void setColor(int x,int y,byte color)
    {
        get(x,y).setColor(color);
    }

    public SquareView get(int x,int y)
    {
        return grid.get(x*20+y);
    }

    private void add(SquareView toAdd) {
        if (gridView == null) {
            gridView = findViewById(R.id.insider_grid);
        }
        gridView.addView(toAdd);
        grid.add(toAdd);
    }

}

