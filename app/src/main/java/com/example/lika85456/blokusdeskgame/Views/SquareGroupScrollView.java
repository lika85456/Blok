package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lika85456.blokusdeskgame.Activity.SingleplayerActivity;
import com.example.lika85456.blokusdeskgame.Model.Piece;

import java.util.ArrayList;

/**
 * Created by lika85456 on 24.03.2018.
 */

public class SquareGroupScrollView extends RelativeLayout {

    public SingleplayerActivity activity;
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
            SquareGroup temp = new SquareGroup(this.getContext(), width / 3 - 50, height / 3 - 50, list.get(i));
            temp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onSquareGroupTouch((SquareGroup) view);
                }
            });
            addView(temp);
        }

    }

    @Override
    protected void onLayout(boolean b, int i0, int i1, int i2, int i3) {
        if(b)
        for (int i = 0; i < this.getChildCount(); i++) {
            int width = (i2 - i0) / 3 - 50;
            int margin = (width + 50) * (i % 3);
            this.getChildAt(i).layout(margin, (i / 3) * width, margin + width, (i / 3) * width + width);
        }
    }

    public void add(Piece piece)
    {
        if (list == null)
            list = new ArrayList<>();
        list.add(piece);
    }
}
