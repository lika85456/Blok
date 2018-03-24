package com.example.lika85456.blokusdeskgame.Views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.lika85456.blokusdeskgame.R;

/**
 * Created by lika85456 on 21.03.2018.
 */

public class CircularSelectorView extends RelativeLayout {

    public int width, height;
    public CircularSelectorElement[] list = new CircularSelectorElement[4];
    public int index = 0;
    private double rotate;
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
        rotate = Math.atan2(event.getX() - Math.min(width, height) / 2.f, -event.getY() + Math.min(width, height) / 2.f);
        if (rotate < 0) {
            rotate += 2 * Math.PI;
        }

        return true;
    }

    protected void onLayout(boolean b, int i1, int i2, int i3, int i4) {
        int width = i3 - i1;
        int height = i4 - i2;
        int size = Math.min(width, height);

        int elementSize = size / 4;
        for (int i = 0; i < 4; i++) {
            CircularSelectorElement element = list[i];
            int x = (int) (Math.sin(Math.toRadians(element.rotate + this.rotate)) * (size - elementSize) / 2.f + (size - elementSize) / 2.f);
            int y = (int) (Math.cos(Math.toRadians(element.rotate + this.rotate)) * (size - elementSize) / 2.f + (size - elementSize) / 2.f);

            x -= elementSize / 5 * element.piece.mass().x;
            y -= elementSize / 5 * element.piece.mass().y;

            element.layout(x, y, x + elementSize, y + elementSize);
        }
    }

    public void addElement(CircularSelectorElement element) {
        element.rotate = 90 * index;
        this.addView(element);
        if (index > 3) return;
        list[index] = element;
        index++;
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        //TODO CLEANUP
        super.dispatchDraw(canvas);
        int size = Math.min(width, height);
        Paint paint = new Paint();
        paint.setColor(0xFFCCCCCC);
        canvas.drawArc(new RectF(0, 0, size, size), -30, 60, true, paint);
        paint.setColor(0xFF00FF00);
        canvas.drawArc(new RectF(size / 5 - 5, size / 5 - 5, size / 5 * 4 + 5, size / 5 * 4 + 5), 0, 360, true, paint);
        paint.setColor(0x0);
        canvas.drawArc(new RectF(size / 5, size / 5, size / 5 * 4, size / 5 * 4), 0, 360, true, paint);
    }


}
