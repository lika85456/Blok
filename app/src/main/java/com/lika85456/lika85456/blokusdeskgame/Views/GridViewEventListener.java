package com.lika85456.lika85456.blokusdeskgame.Views;

import com.lika85456.lika85456.blokusdeskgame.Game.Piece;

/**
 * Created by lika85456 on 29.03.2018.
 */

public interface GridViewEventListener {
    void onSelectedPieceMove(int x, int y);

    void onSelectedPieceRotate(Piece piece);
}
