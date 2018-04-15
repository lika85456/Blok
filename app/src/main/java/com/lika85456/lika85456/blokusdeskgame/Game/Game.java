package com.lika85456.lika85456.blokusdeskgame.Game;

public class Game {
    public Board board;
    public Player[] players;
    public int currentPlayerIndex = 0;


    public Game(Player[] players) {
        this.players = players;
    }

    public static int getNextPlayerId(int id) {
        id++;
        if (id > 3) id = 0;
        return id;
    }

    public void play(Player player, Move move) {
        if (move != null) {
            board.move(move);
            player.iDidMove(move);


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
}
