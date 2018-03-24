package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;

import com.example.lika85456.blokusdeskgame.Model.Piece;

/**
 * Created by lika85456 on 21.03.2018.
 */

public class CircularSelectorElement extends SquareGroup {
    public float rotate = 0.f;

    public CircularSelectorElement(Context ctx, int width, int height, Piece piece) {
        super(ctx, width, height, piece);
    }
}
