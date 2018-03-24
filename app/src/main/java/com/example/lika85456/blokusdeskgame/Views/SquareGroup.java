package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.view.ViewGroup;

import com.example.lika85456.blokusdeskgame.Model.Piece;

import java.util.ArrayList;

/**
 * Created by lika85456 on 18.03.2018.
 */

public class SquareGroup extends ViewGroup {

    public ArrayList<SquareView> list;
    private int width;
    private int height;
    private Context ctx;
    private int squareSize;

    public SquareGroup(Context ctx, int width, int height) {
        super(ctx);
        this.ctx = ctx;
        list = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    public SquareGroup(Context ctx,int width,int height,Piece piece)
    {
        super(ctx);
        this.ctx = ctx;
        list = new ArrayList<>();
        for(int i = 0;i<piece.list.size();i++)
        {
            this.list.add(new SquareView(ctx,piece.color,piece.list.get(i).x,piece.list.get(i).y));
        }
        this.width = width;
        this.height = height;
    }

    private int getTotalX() {
        int toRet = 0;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).x > toRet) toRet = list.get(i).x;
        if (list.size() > 0)
            return toRet + 1;
        else
            return 0;
    }

    private int getTotalY() {
        int toRet = 0;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).y > toRet) toRet = list.get(i).y;
        if (list.size() > 0)
            return toRet + 1;
        else
            return 0;
    }

    public void onMeasure(int w, int h) {

        //int sizeX = getTotalX();
        //int sizeY = getTotalY();
        this.squareSize = Math.min(width, height) / 5;
        for (int i = 0; i < list.size(); i++)
            list.get(i).measure(Math.min(width, height) / 5, Math.min(width, height) / 5);

        this.setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean b, int i0, int i1, int i2, int i3) {
        for (int i = 0; i < list.size(); i++) {
            SquareView squareView = list.get(i);
            squareView.layout(squareView.x * squareSize, squareView.y * squareSize, (squareView.x + 1) * squareSize, (squareView.y + 1) * squareSize);
        }
    }

    /***
     *
     * @param list list of existing SquareViews
     * @param color SquareColor
     */
    public static void setSquareColor(ArrayList<SquareView> list,byte color)
    {
        for(int i = 0; i < list.size();i++)
        {
            list.get(i).setColor(color);
        }
    }

    public void add(SquareView squareView) {
        list.add(squareView);
        this.addView(squareView);
    }
}
