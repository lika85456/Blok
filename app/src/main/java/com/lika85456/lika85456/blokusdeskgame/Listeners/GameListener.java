package com.lika85456.lika85456.blokusdeskgame.Listeners;

import com.lika85456.lika85456.blokusdeskgame.Game.Game;
import com.lika85456.lika85456.blokusdeskgame.Game.Move;
import com.lika85456.lika85456.blokusdeskgame.Game.Player;

/**
 * Created by lika85456 on 28.03.2018.
 */

public interface GameListener {
    //Called when player has no moves
    void noMoves(Player player);

    void onMove(Player player, Move move);

    void onGameEnd(Game game);

    void onMoving(Player player);

}
