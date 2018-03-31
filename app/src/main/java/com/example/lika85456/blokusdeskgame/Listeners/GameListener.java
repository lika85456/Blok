package com.example.lika85456.blokusdeskgame.Listeners;

import com.example.lika85456.blokusdeskgame.Game.Board;
import com.example.lika85456.blokusdeskgame.Game.Player;

/**
 * Created by lika85456 on 28.03.2018.
 */

public interface GameListener {
    //Called when player has no moves
    void noMoves(Player player);

    //Called when player makes move
    void onMove(Player player, Board board);

}
