package com.example.lika85456.blokusdeskgame;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.RelativeLayout;

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
        setPos(x, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(r - l, b - t);
            params.setMargins(l, t, 0, 0);
            this.setLayoutParams(params);
        }
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

