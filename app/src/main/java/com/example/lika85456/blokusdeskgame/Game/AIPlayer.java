package com.example.lika85456.blokusdeskgame.Game;

import com.example.lika85456.blokusdeskgame.Utilities.SquareColor;

/**
 * Created by lika85456 on 26.03.2018.
 */

public class AIPlayer extends Player {

    //Make aiplayer from color (might be more than 5 so validate it please)
    public AIPlayer(byte color) {
        this.color = SquareColor.validateColor(color);
    }

    @Override
    public Move move(Board board) {
        return null;
    }

    @Override
    public void onMove(Board board) {

    }
}
