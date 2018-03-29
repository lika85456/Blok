package com.example.lika85456.blokusdeskgame.Listeners;

import com.example.lika85456.blokusdeskgame.Game.Move;

/**
 * Created by lika85456 on 27.03.2018.
 */

public interface DatabaseListener {
    //Called when new game begins
    void onGameLoad(Game game);

    //Called when game is over
    void onGameEnd(Game game);

    //Called when oponent is supposed to make a move
    void onOponentTurn();

    //Called when oponent made a turn
    void onOponentMove(Move move);

}
