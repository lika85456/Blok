package com.lika85456.blokus.game.Model;

import com.lika85456.blokus.game.Game.Game;
import com.lika85456.blokus.game.Game.Move;
import com.lika85456.blokus.game.Game.Player;
import com.lika85456.blokus.game.Listeners.GameListener;

public class SinglePlayerGameHandler implements GameListener {
    public Game game;

    protected SinglePlayerGameHandler(Game game) {
        this.game = game;
    }

    private boolean isGameEnd() {
        return game.getBoard().isOver(game.players);
    }


    public void move(Player player, Move move) {

        game.play(player, move);
        onMove(player, move);

        if (move == null) {
            noMoves(game.getCurrentPlayer());
            player.hasMoves = false;
            if (isGameEnd()) {
                onGameEnd(game);
                return;
            }
        }

        onMoving(game.getCurrentPlayer());
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
