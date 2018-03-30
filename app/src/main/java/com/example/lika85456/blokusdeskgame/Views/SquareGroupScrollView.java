package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.lika85456.blokusdeskgame.Game.Piece;

import java.util.ArrayList;

/**
 * Created by lika85456 on 24.03.2018.
 */

public class SquareGroupScrollView extends RelativeLayout {

    public OnClickListener onClickListener;
    private ArrayList<Piece> list;
    private boolean initialized = false;
    public SquareGroupScrollView(Context context) {
        super(context);
    }

    public SquareGroupScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareGroupScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        if (initialized == false) {
            for (int i = 0; i < this.list.size(); i++) {
                Piece piece = this.list.get(i);
                SquareGroup temp = new SquareGroup(this.getContext(), piece);
                temp.setOnClickListener(onClickListener);
                this.addView(temp);
                initialized = true;
            }
        }
    }

    public void onMeasure(int w,int h)
    {
        int width = MeasureSpec.getSize(w);
        int height = MeasureSpec.getSize(h);
        this.setMeasuredDimension(width, (int) (((float) width / 3.f - 15.f) * 7.f));
    }

    @Override
    protected void onLayout(boolean b, int i0, int i1, int i2, int i3) {
        //this.setMeasuredDimension(getWidth(), (int) (((float) getWidth() / 3.f - 15.f) * list.size() / 3));
        init();
        for (int i = 0; i < this.list.size(); i++) {
            SquareGroup temp = (SquareGroup) this.getChildAt(i);
            int width = (i2 - i0) / 3 - 15;
            int margin = (width + 15) * (i % 3) + 7;
            temp.layout(margin, i / 3 * width, margin + width, i / 3 * width + width);

            //this.getChildAt(i).layout(margin, ((int)(i / 3)) * width, margin + width, ((int)(i / 3)) * width + width);
        }
    }

    public void removeElementAtIndex(Piece piece) {
        int index = list.indexOf(piece);
        list.remove(index);
        removeViewAt(index);
        requestLayout();
    }

    public void add(Piece piece)
    {
        if (list == null)
            list = new ArrayList<>();
        list.add(piece);

    }
}
