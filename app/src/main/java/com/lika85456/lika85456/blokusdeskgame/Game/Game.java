package com.lika85456.lika85456.blokusdeskgame.Game;

public class Game {
    private Board board;
    public Player[] players;


    private int currentPlayerIndex;


    public Game(Player[] players) {
        this.players = players;
        currentPlayerIndex = 0;
    }

    public static int getNextPlayerId(int id) {
        id++;
        if (id > 3) id = 0;
        return id;
    }

    public void play(Player player, Move move) {
        if (move != null) {
            board.move(move);
            player.onMove(move);


        }
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.length)
            currentPlayerIndex = 0;

    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}
