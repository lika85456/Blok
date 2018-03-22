package com.example.lika85456.blokusdeskgame.Views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.example.lika85456.blokusdeskgame.R;

/**
 * Created by lika85456 on 21.03.2018.
 */

public class CircularSelectorView extends View {

    public int width, height;
    private float rotate;

    public CircularSelectorView(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.circular_selector_background);

    }

    public void onMeasure(int w, int h) {
        width = MeasureSpec.getSize(w);
        height = MeasureSpec.getSize(h);
        this.setMeasuredDimension(width, height);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        rotate += event.getOrientation();
        return true;
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        //TODO CLEANUP
        super.dispatchDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(0xCCC);
        canvas.drawArc(new RectF(0, 0, width, height), -30, 60, true, paint);
    }


}
