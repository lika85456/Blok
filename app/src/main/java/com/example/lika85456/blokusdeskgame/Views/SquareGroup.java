package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.ViewGroup;

import com.example.lika85456.blokusdeskgame.Game.Piece;

import java.util.ArrayList;

/**
 * Created by lika85456 on 18.03.2018.
 */

public class SquareGroup extends ViewGroup {

    private ArrayList<SquareView> list;
    private Piece piece;
    private float mSize = 1.f;
    private int squareSize;
    public SquareGroup(Context ctx) {
        super(ctx);
        this.invalidate();
    }

    public SquareGroup(SquareGroup squareGroup) {
        super(squareGroup.getContext());
        list = (ArrayList<SquareView>) squareGroup.list.clone();
        this.squareSize = squareGroup.squareSize;
        this.piece = new Piece(squareGroup.piece);
    }

    public SquareGroup(Context ctx, Piece piece) {
        super(ctx);

        fromPiece(piece);
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void fromPiece(Piece piece) {
        this.list = new ArrayList<>();
        this.piece = piece;
        this.removeAllViews();

        for (int i = 0; i < piece.list.size(); i++) {
            SquareView temp = new SquareView(getContext(), piece.color, piece.list.get(i).x, piece.list.get(i).y);
            this.list.add(temp);
            this.addView(this.list.get(i));
            temp.layout(temp.x * squareSize, temp.y * squareSize, (temp.x + 1) * squareSize, (temp.y + 1) * squareSize);
        }
        this.requestLayout();
    }

    /***
     *
     * @param list list of existing SquareViews
     * @param color SquareColor
     */
    public static void setSquareColor(ArrayList<SquareView> list, byte color) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setColor(color);
        }
    }

    public void onMeasure(int w, int h) {

        //int sizeX = getTotalX();
        //int sizeY = getTotalY();
        int width = MeasureSpec.getSize(w);
        int height = MeasureSpec.getSize(h);
        this.squareSize = Math.min(width, height) / 5;
        for (int i = 0; i < list.size(); i++)
            list.get(i).measure(Math.min(width, height) / 5, Math.min(width, height) / 5);

        this.setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean b, int i0, int i1, int i2, int i3) {

            this.squareSize = Math.min(getWidth(), getHeight()) / 5;
            for (int i = 0; i < list.size(); i++) {
                SquareView squareView = list.get(i);
                squareView.layout(squareView.x * squareSize, squareView.y * squareSize, (squareView.x + 1) * squareSize, (squareView.y + 1) * squareSize);
            }


    }

    public void setSize(float fl) {
        this.mSize = fl;
        setWillNotDraw(false);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSize > 1.f) {
            canvas.save();
            canvas.scale(mSize, mSize, canvas.getWidth() / 2, canvas.getHeight() / 2);
            canvas.restore();
        } else {
            setWillNotDraw(true);
        }

    }

    public void add(SquareView squareView) {
        list.add(squareView);
        this.addView(squareView);
    }

    public void rotate() {
        this.piece.rotateBy90();
        this.invalidate();
    }
}
