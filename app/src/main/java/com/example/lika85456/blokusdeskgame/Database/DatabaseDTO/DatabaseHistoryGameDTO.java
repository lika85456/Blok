package com.example.lika85456.blokusdeskgame.Database.DatabaseDTO;

/**
 * Created by lika85456 on 27.03.2018.
 */

public class DatabaseHistoryGameDTO {
    public int id;
    public DatabasePlayerDTO[] players;
    public DatabasePlayerDTO winner;

    public DatabaseHistoryGameDTO() {

    }

    public int getId() {
        return id;
    }

    public DatabasePlayerDTO[] getPlayers() {
        return players;
    }

    public DatabasePlayerDTO getWinner() {
        return winner;
    }

    /*//TODO REPAIR
    public DatabaseBoardDTO getBoard() {
        return board;
    }
    */
}