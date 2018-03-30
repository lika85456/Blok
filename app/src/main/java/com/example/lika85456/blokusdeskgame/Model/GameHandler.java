package com.example.lika85456.blokusdeskgame.Model;

import com.example.lika85456.blokusdeskgame.Game.Board;
import com.example.lika85456.blokusdeskgame.Game.Move;
import com.example.lika85456.blokusdeskgame.Game.Player;

/**
 * Created by lika85456 on 28.03.2018.
 */

public class GameHandler {

    public Board board;
    public Player currentPlayer;
    public Player[] players;
    private int index = 0;

    public GameHandler(Board board, Player[] players) {
        this.board = board;
        this.players = players;
        this.currentPlayer = players[index];
        //Notifies user to make a move
        this.currentPlayer.onMove(board);
    }

    //Called when player is moving
    public void PlayerIsMoving(Player player) {
        player.onMove(board);
    }

    //Called when player makes a move
    public void move(Player player, Move move) {
        if (!board.isValid(move.getRealPiece(), move.getX(), move.getY())) {
            //TODO HOW IT CAN BE INVALID???
            return;
        }

        board.addPiece(move.getRealPiece(), move.getX(), move.getY());

    }

    public Player getNextPlayer() {
        index++;
        if (index > 4) index = 0;
        this.currentPlayer = players[index];
        return currentPlayer;
    }

}
