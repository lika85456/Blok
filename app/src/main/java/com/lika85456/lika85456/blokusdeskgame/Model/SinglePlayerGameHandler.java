package com.lika85456.lika85456.blokusdeskgame.Model;

import com.lika85456.lika85456.blokusdeskgame.Game.Game;
import com.lika85456.lika85456.blokusdeskgame.Game.Move;
import com.lika85456.lika85456.blokusdeskgame.Game.Player;
import com.lika85456.lika85456.blokusdeskgame.Listeners.GameListener;

public class SinglePlayerGameHandler implements GameListener {
    public Game game;

    public SinglePlayerGameHandler(Game game) {
        this.game = game;
        //onMoving(game.getCurrentPlayer());
    }

    public boolean isGameEnd() {
        return game.getBoard().isOver(game.players);
    }


    public void move(Player player, Move move) {
        if (player == null) {
            game.play(player, move);
            onMove(player, move);
        }
        onMoving(game.getCurrentPlayer());
    }

    public void onMoveNotify(Player player) {
        //  if (player instanceof ComputerPlayer) {
        //      move(player, ((ComputerPlayer) (player)).getMove(game));
        //  }
    }


    @Override
    public void noMoves(Player player) {

    }

    @Override
    public void onMove(Player player, Move move) {

    }

    @Override
    public void onGameEnd(Game game) {

    }

    @Override
    public void onMoving(Player player) {

    }
}
