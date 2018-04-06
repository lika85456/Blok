package com.lika85456.lika85456.blokusdeskgame.Listeners;

import com.lika85456.lika85456.blokusdeskgame.Game.Move;

/**
 * Created by lika85456 on 27.03.2018.
 */

public interface DatabaseListener {
    //Called when new game begins
    //void onGameLoad(GameHandler game);

    //Called when game is over
    //void onGameEnd(GameHandler game);

    //Called when oponent is supposed to make a move
    void onOponentTurn();

    //Called when oponent made a turn
    void onOponentMove(Move move);

}