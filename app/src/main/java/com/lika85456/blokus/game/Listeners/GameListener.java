package com.lika85456.blokus.game.Listeners;

import com.lika85456.blokus.game.Game.Game;
import com.lika85456.blokus.game.Game.Move;
import com.lika85456.blokus.game.Game.Player;

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
