package com.example.lika85456.blokusdeskgame.Listeners;

import com.example.lika85456.blokusdeskgame.Game.Move;
import com.example.lika85456.blokusdeskgame.Game.Player;

/**
 * Created by lika85456 on 28.03.2018.
 */

public interface GameHandlerListener {
    //Called when player has no moves
    void noMoves(Player player);

    //Called when player makes move
    void move(Player player, Move move);

}
