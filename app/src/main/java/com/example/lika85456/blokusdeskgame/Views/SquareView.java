package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.lika85456.blokusdeskgame.Model.SquareColor;
import com.example.lika85456.blokusdeskgame.R;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class SquareView extends View {

    public int x = 0, y = 0;
    public byte color;
    private Context ctx;

    /***
     *
     * @param ctx
     * @param color from 0 to 3
     */
    public SquareView(Context ctx, byte color, int x, int y) {
        super(ctx);
        this.ctx = ctx;
        this.color = color;
        this.setBackgroundResource(R.drawable.block);
        this.getBackground().setColorFilter(SquareColor.getColorFromCode(color), PorterDuff.Mode.MULTIPLY);
        this.getBackground().mutate();
        setPos(x, y);
    }

    public void setColor(byte color)
    {
        this.setBackgroundResource(R.drawable.block);
        this.getBackground().setColorFilter(SquareColor.getColorFromCode(color), PorterDuff.Mode.MULTIPLY);
        this.getBackground().mutate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            setMargins(this, l, t, r, b);
        }
    }


    public void setPos(Point point)
    {
        setPos(point.x,point.y);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}

