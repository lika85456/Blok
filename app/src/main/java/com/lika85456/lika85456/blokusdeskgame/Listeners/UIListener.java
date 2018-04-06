package com.lika85456.lika85456.blokusdeskgame.Listeners;

import com.lika85456.lika85456.blokusdeskgame.Game.Piece;

/**
 * Created by lika85456 on 27.03.2018.
 */

public interface UIListener {
    void onPieceSelected(Piece piece);

    boolean isValid(Piece piece, int x, int y);

    void onMoveConfirm(int x, int y);

}
