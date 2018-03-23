package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by lika85456 on 23.03.2018.
 */

public class DrawingGridView extends RelativeLayout {
    private int pointSize;

    public DrawingGridView(Context context) {
        super(context);
    }

    public DrawingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onMeasure(int w, int h) {

        int width = MeasureSpec.getSize(w);
        int height = MeasureSpec.getSize(h);
        pointSize = (int) ((float) Math.min(width, height) / 20.f);
        this.setMeasuredDimension(width, height);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(0xCC999999);
        for (int i = 1; i < 19; i++) {
            canvas.drawLine(0, i * pointSize, this.getMeasuredWidth(), i * pointSize, paint);
            canvas.drawLine(i * pointSize, 0, i * pointSize, this.getMeasuredHeight(), paint);
        }
        this.invalidate();
    }
}
