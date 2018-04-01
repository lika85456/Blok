package com.example.lika85456.blokusdeskgame.Model;

import com.example.lika85456.blokusdeskgame.Game.ComputerPlayer;
import com.example.lika85456.blokusdeskgame.Game.Game;
import com.example.lika85456.blokusdeskgame.Game.Move;
import com.example.lika85456.blokusdeskgame.Game.Player;
import com.example.lika85456.blokusdeskgame.Listeners.GameListener;

import java.util.ArrayList;

/**
 * Created by lika85456 on 28.03.2018.
 */

public class GameHandler implements GameListener {
    public Game game;
    public Player currentPlayer;
    private int index = 0;
    private ArrayList<Move> moves;

    public GameHandler(Game game) {
        this.game = game;
        this.currentPlayer = game.getPlayers()[index];
        moves = new ArrayList<>();
    }

    public boolean isGameEnd() {
        for (int i = 0; i < game.getPlayers().length; i++) {
            if (game.getPlayers()[i].movesAvailable == true) return true;
        }
        return false;
    }

    public void askForMove() {

        ComputerPlayer temp = new ComputerPlayer(currentPlayer);
        if (temp.getMove(game) == null) {
            noMoves(currentPlayer);
            currentPlayer.movesAvailable = false;
        }
        if (isGameEnd()) {
            onGameEnd(game);
            return;
        }
        this.onMoveNotify(currentPlayer);
    }


    public void move(Player player, Move move) {
        if (move != null || player == null) {

            player.moveAdd(move);
            game.getBoard().addPiece(move.getPiece(), move.getX(), move.getY());
            moves.add(move);
            onMove(player, move);
        }
        nextPlayer();
        askForMove();
    }

    public void onMoveNotify(Player player) {
        if (player instanceof ComputerPlayer) {
            move(player, ((ComputerPlayer) (player)).getMove(game));
        }
    }

    public void nextPlayer() {
        this.currentPlayer = game.getPlayers()[Game.getNextPlayerId(index)];
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
}
