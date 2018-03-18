package com.example.lika85456.blokusdeskgame;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by lika85456 on 18.03.2018.
 */

public class SquareGroup extends ViewGroup {

    public ArrayList<SquareView> list;
    private int width;
    private int height;
    private Context ctx;

    public SquareGroup(Context ctx, int width, int height) {
        super(ctx);
        this.ctx = ctx;
        list = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    public void add(SquareView squareView) {
        list.add(squareView);
        this.addView(squareView);
    }
}
