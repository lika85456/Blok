package com.example.lika85456.blokusdeskgame.Game;

public class Game {
    private Board board;
    private Player[] players;

    public Game(Player[] players) {
        this.players = players;
    }

    public static int getNextPlayerId(int id) {
        id++;
        if (id > 4) id = 0;
        return id;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }
}
