package com.lika85456.blokus.game.Views;


import com.lika85456.blokus.game.Game.Piece;

/**
 * Created by lika85456 on 29.03.2018.
 */

public interface GridViewEventListener {
    void onSelectedPieceMove(int x, int y);

    void onSelectedPieceRotate(Piece piece);
}
