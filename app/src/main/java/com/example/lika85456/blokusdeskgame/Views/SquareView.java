package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.Utility;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class SquareView extends View {

    public int x = 0, y = 0;
    public int color;
    private Context ctx;

    /***
     *
     * @param ctx
     * @param color from 0 to 3
     */
    public SquareView(Context ctx, int color, int x, int y) {
        super(ctx);
        this.ctx = ctx;
        this.color = color;
        this.setBackgroundResource(R.drawable.block);
        this.getBackground().setColorFilter(Utility.getColorFromInt(color), PorterDuff.Mode.MULTIPLY);
        this.getBackground().mutate();
        setPos(x, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            setMargins(this, l, t, r, b);
            Log.d("POSITION", "X:" + l + " Y:" + r + " WIDTH:" + (r - l) + " HEIGHT:" + (b - t));
        }
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

