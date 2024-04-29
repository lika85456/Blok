package com.lika85456.blokus.game.Model;

import com.lika85456.blokus.game.Game.AI;
import com.lika85456.blokus.game.Game.Game;
import com.lika85456.blokus.game.Game.Move;
import com.lika85456.blokus.game.Game.Player;
import com.lika85456.blokus.game.Listeners.GameListener;

/**
 * Created by lika85456 on 28.03.2018.
 */

public class GameHandler implements GameListener {
    public Game game;
    private AI ai = new AI(500);

    public GameHandler(Game game) {
        this.game = game;
        onMoving(game.getCurrentPlayer());
    }

    private boolean isGameEnd() {
        return game.getBoard().isOver(game.players);
    }


    public void move(Player player, Move move) {
        game.play(player, move);

        if (!ai.hasPossibleMove(game.getBoard(), game.players[Game.getNextPlayerId(player.color)])) {
            game.setCurrentPlayerIndex(Game.getNextPlayerId(game.getCurrentPlayerIndex()));
            noMoves(game.getCurrentPlayer());
            if (isGameEnd()) {
                onGameEnd(game);
            }
        } else
            onMove(player, move);

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
