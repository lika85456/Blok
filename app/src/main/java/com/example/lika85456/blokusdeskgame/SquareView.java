package com.example.lika85456.blokusdeskgame;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
    public SquareView(Context ctx, int color) {
        super(ctx);
        this.ctx = ctx;
        this.color = color;
        this.setBackgroundResource(R.drawable.block);
        this.getBackground().setColorFilter(Utility.getColorFromInt(color), PorterDuff.Mode.MULTIPLY);

        this.setLayoutParams(new LinearLayout.LayoutParams(30, 30));
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
        Log.d("width", String.valueOf(this.getMeasuredWidth()));
        int tX = (int) ((double) this.getMeasuredWidth() * (((double) 20) / (double) x));
        int tY = (int) ((double) this.getMeasuredHeight() * (((double) 20) / (double) y));
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(30, 30);
        l.setMargins(tX, tY, 0, 0);
        this.setLayoutParams(l);
    }

}

