package com.example.lika85456.blokusdeskgame.Model;

import com.example.lika85456.blokusdeskgame.Game.Board;
import com.example.lika85456.blokusdeskgame.Game.Game;
import com.example.lika85456.blokusdeskgame.Game.Move;
import com.example.lika85456.blokusdeskgame.Game.Player;
import com.example.lika85456.blokusdeskgame.Listeners.GameListener;

/**
 * Created by lika85456 on 28.03.2018.
 */

public class GameHandler implements GameListener {
    public Game game;
    public Player currentPlayer;
    private int index = 0;

    public GameHandler(Game game) {
        this.game = game;
        this.currentPlayer = game.getPlayers()[index];
    }

    public void askForMove() {
        this.onMove(currentPlayer, game.getBoard());
    }


    public void move(Player player, Move move) {

        game.getBoard().addPiece(move.getPiece(), move.getX(), move.getY());
        nextPlayer();
        askForMove();
    }


    public void nextPlayer() {
        this.currentPlayer = game.getPlayers()[Game.getNextPlayerId(index)];
    }

    @Override
    public void noMoves(Player player) {

    }

    @Override
    public void onMove(Player player, Board board) {

    }
}
