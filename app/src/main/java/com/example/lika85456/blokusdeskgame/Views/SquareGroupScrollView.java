package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.example.lika85456.blokusdeskgame.Model.Piece;

import java.util.ArrayList;

/**
 * Created by lika85456 on 24.03.2018.
 */

public class SquareGroupScrollView extends ScrollView {

    private ArrayList<Piece> list;
    public SquareGroupScrollView(Context context) {
        super(context);
    }

    public SquareGroupScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareGroupScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onMeasure(int w,int h)
    {
        int width = MeasureSpec.getSize(w);
        int height = MeasureSpec.getSize(h);
        this.setMeasuredDimension(width,height);

        for(int i = 0;i<list.size();i++)
        {
            addView(new SquareGroup(this.getContext(),w/3-50,h/3-50,list.get(i)));
        }

    }

    @Override
    protected void onLayout(boolean b, int i0, int i1, int i2, int i3) {
        if(b)
        for (int i = 0; i < this.getChildCount(); i++) {
            int width = i2-i0;
            int height = width;
            int margin = width*(i%3)
            this.getChildAt(i).layout();
        }
    }

    public void add(Piece piece)
    {
        list.add(piece);
    }
}
