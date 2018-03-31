package com.example.lika85456.blokusdeskgame.Game;

public class Game {
    private Board board;
    private Player[] players;

    public static int getNextPlayerId(int id) {
        id++;
        if (id > 4) id = 0;
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }
}
